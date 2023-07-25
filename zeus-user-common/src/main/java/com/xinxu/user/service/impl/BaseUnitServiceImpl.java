package com.xinxu.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinxu.user.constant.RedisTopic;
import com.xinxu.user.entity.BaseUnit;
import com.xinxu.user.mapper.BaseUnitMapper;
import com.xinxu.user.service.IBaseUnitService;
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
public class BaseUnitServiceImpl extends ServiceImpl<BaseUnitMapper, BaseUnit> implements IBaseUnitService {
    private final ReactiveStringRedisTemplate reactiveRedisTemplate;

    private Map<Long, BaseUnit> baseUtilMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void hotStart() {
        reactiveRedisTemplate.listenToChannel(RedisTopic.BASE_UTIL_UPDATE).subscribe(message -> {
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
        BaseUnit byId = getById(key);
        if (Objects.nonNull(byId) && byId.getIsDel()) {
            baseUtilMap.remove(key);
            return;
        }
        baseUtilMap.put(key, byId);

    }

    @Override
    public void refreshAll() {
        baseUtilMap.clear();
        list().forEach(it -> {
            if (Objects.nonNull(it) && !it.getIsDel()) {
                baseUtilMap.put(it.getId(), it);
            }
        });
    }

    @Override
    public void send(Long key) {
        if (Objects.nonNull(key)) {
            reactiveRedisTemplate.convertAndSend(RedisTopic.BASE_UTIL_UPDATE, key.toString()).subscribe();
        }

    }

    @Override
    public boolean saveOne(BaseUnit baseUnit) {
        boolean save = save(baseUnit);
        send(baseUnit.getId());
        return save;
    }

    @Override
    public List<BaseUnit> dumpAll() {
        return new ArrayList<>(baseUtilMap.values());
    }
}
