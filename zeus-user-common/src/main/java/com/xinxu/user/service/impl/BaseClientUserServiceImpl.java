package com.xinxu.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xinxu.user.constant.RedisTopic;
import com.xinxu.user.entity.BaseClientUser;
import com.xinxu.user.mapper.BaseUserClientMapper;
import com.xinxu.user.service.IBaseUserClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseClientUserServiceImpl extends ServiceImpl<BaseUserClientMapper, BaseClientUser> implements IBaseUserClientService {
    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    private final Cache<Long, ConcurrentHashMap<Long, BaseClientUser>> userClientCache = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofDays(10))
            .maximumSize(1_000_000)
            .build();

    @PostConstruct
    public void hotStart() {
        reactiveStringRedisTemplate.listenToChannel(RedisTopic.BASE_USER_CLIENT_UPDATE).subscribe(message -> {
            try {
                long key = Long.parseLong(message.getMessage().trim());
                refresh(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void refresh(Long key) {
        BaseClientUser baseClientUser = getById(key);
        if (Objects.isNull(baseClientUser)) {
            return;
        }
        ConcurrentHashMap<Long, BaseClientUser> ifPresent = userClientCache.getIfPresent(baseClientUser.getUserId());
        if (CollectionUtils.isEmpty(ifPresent)) {
            return;
        }

        if (baseClientUser.getIsDel()) {
            ifPresent.remove(baseClientUser.getId());
            if (ifPresent.isEmpty()) {
                userClientCache.invalidate(baseClientUser.getUserId());
            }
        } else {
            ifPresent.put(baseClientUser.getId(), baseClientUser);
        }
    }

    @Override
    public void refreshAll() {
        // 不需要，对于那种初始化load 全部的需要
    }

    @Override
    public boolean saveWithCache(BaseClientUser baseClientUser) {
        boolean success = save(baseClientUser);
        if (success){
            send(baseClientUser.getId());
        }
        return success;
    }

    @Override
    public List<BaseClientUser> queryByUserId(Long userId) {
        ConcurrentHashMap<Long, BaseClientUser> ifPresent = userClientCache.getIfPresent(userId);
        if (ifPresent != null) {
            return new ArrayList<>(ifPresent.values());
        }
        List<BaseClientUser> list = list(new LambdaQueryWrapper<BaseClientUser>().eq(BaseClientUser::getUserId, userId)
                .eq(BaseClientUser::getIsDel, false));
        userClientCache.put(userId, new ConcurrentHashMap<>(list.stream().collect(Collectors.toMap(BaseClientUser::getId, Function.identity()))));
        return list;
    }

    @Override
    public boolean updateBaseClientUser(BaseClientUser baseClientUser) {
        boolean success = updateById(baseClientUser);
        if (success){
            send(baseClientUser.getId());
        }
        return success;
    }

    @Override
    public boolean removeBaseClientUser(Long id) {
        boolean success = removeById(id);
        if (success){
            send(id);
        }
        return success;
    }

    @Override
    public void send(Long key) {
        reactiveStringRedisTemplate.convertAndSend(RedisTopic.BASE_USER_CLIENT_UPDATE,key.toString()).subscribe();
    }
}
