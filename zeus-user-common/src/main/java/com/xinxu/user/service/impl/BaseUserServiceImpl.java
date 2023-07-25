package com.xinxu.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinxu.user.constant.RedisTopic;
import com.xinxu.user.entity.BaseUser;
import com.xinxu.user.mapper.BaseUserMapper;
import com.xinxu.user.service.IBaseUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {
    private final ReactiveStringRedisTemplate reactiveRedisTemplate;
    private Map<Long, BaseUser> baseUserMap = new ConcurrentHashMap<>();
    private Map<String, BaseUser> nameUserMap = new ConcurrentHashMap<>();


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
        refreshAll();

    }

    @Override
    public void refresh(Long key) {
        BaseUser byId = getById(key);
        if (byId.getIsDel()) {
            baseUserMap.remove(key);
            nameUserMap.remove(byId.getUsername());
            return;
        }
        baseUserMap.put(key, byId);
    }

    @Override
    public void refreshAll() {
        baseUserMap.clear();
        nameUserMap.clear();
        list().forEach(it -> {
            if (Objects.nonNull(it) && !it.getIsDel()) {
                baseUserMap.put(it.getId(), it);
                nameUserMap.put(it.getUsername(), it);
            }
        });
    }

    @Override
    public void send(Long key) {
        if (Objects.nonNull(key)) {
            reactiveRedisTemplate.convertAndSend(RedisTopic.BASE_USER_UPDATE, key.toString()).subscribe();
        }
    }

    @Override
    public boolean saveWithCache(BaseUser baseUser) {
        boolean save = save(baseUser);
        send(baseUser.getId());
        return save;
    }

    @Override
    public BaseUser queryByUserName(String userName) {
        return nameUserMap.get(userName);
    }

    @Override
    public List<BaseUser> dumpAll() {
        return new ArrayList<>(baseUserMap.values());
    }

}
