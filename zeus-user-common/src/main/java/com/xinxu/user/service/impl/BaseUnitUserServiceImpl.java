package com.xinxu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinxu.user.constant.RedisTopic;
import com.xinxu.user.entity.BaseUnit;
import com.xinxu.user.entity.BaseUnitUser;
import com.xinxu.user.mapper.BaseUnitUserMapper;
import com.xinxu.user.service.IBaseUnitUserService;
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
public class BaseUnitUserServiceImpl extends ServiceImpl<BaseUnitUserMapper, BaseUnitUser> implements IBaseUnitUserService {
    private final ReactiveStringRedisTemplate reactiveRedisTemplate;
    private Map<Long, BaseUnitUser> baseUtilUserMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void hotStart() {
        reactiveRedisTemplate.listenToChannel(RedisTopic.BASE_UTIL_USER_UPDATE).subscribe(message -> {
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
        BaseUnitUser byId = getById(key);
        if (Objects.nonNull(byId) && !byId.getIsDel()) {
            baseUtilUserMap.put(key, byId);
        } else {
            baseUtilUserMap.remove(key);
        }

    }

    @Override
    public void refreshAll() {
        baseUtilUserMap.clear();
        list().forEach(it -> {
            if (Objects.nonNull(it) && !it.getIsDel()) {
                baseUtilUserMap.put(it.getId(), it);
            }
        });

    }

    @Override
    public void send(Long key) {
        if (Objects.nonNull(key)) {
            reactiveRedisTemplate.convertAndSend(RedisTopic.BASE_UTIL_USER_UPDATE, key.toString()).subscribe();
        }

    }


    @Override
    public boolean saveOne(BaseUnitUser baseUnitUser) {
        boolean save = save(baseUnitUser);
        send(baseUnitUser.getId());
        return save;
    }

    @Override
    public List<BaseUnitUser> dumpAll() {
        return new ArrayList<>(baseUtilUserMap.values());
    }
}
