package com.xinxu.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xinxu.user.constant.RedisTopic;
import com.xinxu.user.entity.BaseUser;
import com.xinxu.user.mapper.BaseUserMapper;
import com.xinxu.user.service.IBaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {
    private final ReactiveStringRedisTemplate reactiveRedisTemplate;

    private final Cache<String, BaseUser> userNameCache = CacheBuilder.newBuilder()
            .expireAfterAccess(Duration.ofDays(10))
            .maximumSize(1_000_000)
            .build();

    @PostConstruct
    public void hotStart() {
        reactiveRedisTemplate.listenToChannel(RedisTopic.BASE_USER_UPDATE).subscribe(message -> {
                    try {
                        long key = Long.parseLong(message.getMessage().trim());
                        refresh(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void refresh(Long key) {
        BaseUser byId = getById(key);
        // 不建议物理删除，所以这里一定不为空，否则会出问题。真正需要物理删除的话，请再维护一个id-model的cache
        if (Objects.isNull(byId)) {
            throw new UnsupportedOperationException("数据库 和 缓存 不一致，存在物理删除");
        }
        BaseUser ifPresent = userNameCache.getIfPresent(byId.getUsername());
        if (Objects.isNull(ifPresent)) {
            return;
        }
        if (byId.getIsDel()) {
            userNameCache.invalidate(ifPresent.getUsername());
        }
        userNameCache.put(byId.getUsername(), byId);
    }

    @Override
    public void refreshAll() {
        // 不用初始化加载所有 【client 和 unit 就可用加载所有的方式】
        // 1、数据量不是固定量级
        // 2、不需要查询所有
    }

    @Override
    public void send(Long key) {
        if (Objects.nonNull(key)) {
            reactiveRedisTemplate.convertAndSend(RedisTopic.BASE_USER_UPDATE, key.toString()).subscribe();
        }
    }

    @Override
    public boolean saveWithCache(BaseUser baseUser) {
        boolean success = save(baseUser);
        if (success) {
            send(baseUser.getId());
        }
        return success;
    }

    @Override
    public BaseUser queryByUserName(String userName) {
        BaseUser ifPresent = userNameCache.getIfPresent(userName);
        if (ifPresent != null) {
            return ifPresent;
        }
        List<BaseUser> list = list(new LambdaQueryWrapper<BaseUser>().eq(BaseUser::getUsername, userName)
                .eq(BaseUser::getIsDel, false));
        if (CollectionUtils.isEmpty(list)) return null;
        BaseUser baseUser = list.get(0);
        userNameCache.put(userName,baseUser);
        return baseUser;
    }

    @Override
    public boolean updateUser(BaseUser baseUser) {
        boolean success = updateById(baseUser);
        if (success){
            send(baseUser.getId());
        }
        return success;
    }

}
