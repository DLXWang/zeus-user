package com.xinxu.user.service;

import com.xinxu.user.model.AccountKeyInfoVO;
import com.xinxu.user.model.BaseUserDTO;
import com.xinxu.user.model.UserLoginDTO;
import org.apache.commons.lang3.tuple.Pair;

public interface IUserManageService {
    Boolean register(BaseUserDTO baseUserDTO);

    Pair<String, String> login(UserLoginDTO userLoginDTO);

    Pair<String, String> refresh(String refresh);

    AccountKeyInfoVO authAndAppend(String token);
}
