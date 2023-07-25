package com.xinxu.user.service.impl;

import com.xinxu.user.entity.BaseUser;
import com.xinxu.user.exception.BadCredentialsException;
import com.xinxu.user.exception.ExpireException;
import com.xinxu.user.model.AccountKeyInfoVO;
import com.xinxu.user.model.BaseUserDTO;
import com.xinxu.user.model.UserLoginDTO;
import com.xinxu.user.service.IBaseUserService;
import com.xinxu.user.service.IUserManageService;
import com.xinxu.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.Strings;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserManageServiceImpl implements IUserManageService {
    private final IBaseUserService iBaseUserService;
    private final JwtUtil jwtUtil;

    @Override
    public Boolean register(BaseUserDTO baseUserDTO) {
        BaseUser build = BaseUser.builder().isDel(false).build();
        BeanUtils.copyProperties(baseUserDTO, build);
        String encodePassword = BCrypt.hashpw(baseUserDTO.getPassword(), BCrypt.gensalt());
        build.setPassword(encodePassword);
        return iBaseUserService.saveWithCache(build);
    }

    @Override
    public Pair<String, String> login(UserLoginDTO userLoginDTO) {
        BaseUser baseUser = iBaseUserService.queryByUserName(userLoginDTO.getUsername());
        if (Objects.isNull(baseUser)) {
            throw new IllegalArgumentException("用户不存在");
        }
        if (!BCrypt.checkpw(userLoginDTO.getPassword(), baseUser.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        String access = jwtUtil.generateToken(userLoginDTO.getUsername(), false);
        String refresh = jwtUtil.generateToken(userLoginDTO.getUsername(), true);

        return Pair.of(access, refresh);
    }

    @Override
    public Pair<String, String> refresh(String refresh) {
        if (Strings.isBlank(refresh)) {
            throw new BadCredentialsException("refresh 为空");
        }
        String access = jwtUtil.refreshToken(refresh, false);
        String refreshToken = jwtUtil.refreshToken(refresh, true);
        if (access == null || refreshToken == null) {
            throw new BadCredentialsException("refresh 拒绝");
        }
        return Pair.of(access, refreshToken);
    }

    @Override
    public AccountKeyInfoVO authAndAppend(String token) {
        String userNameFromToken = jwtUtil.getUserNameFromToken(token);
        if (Objects.isNull(userNameFromToken)) {
            throw new BadCredentialsException("token非法或者过期");
        }
        BaseUser baseUser = iBaseUserService.queryByUserName(userNameFromToken);
        if (Objects.isNull(baseUser)) {
            throw new BadCredentialsException("没有可用的用户");
        }
        return AccountKeyInfoVO.builder()
                .userId(baseUser.getId())
                .username(baseUser.getUsername())
                .userCode(baseUser.getUserCode())
                .unitId(baseUser.getUnitId())
                .build();

    }
}
