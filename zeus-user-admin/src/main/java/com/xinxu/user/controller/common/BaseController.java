package com.xinxu.user.controller.common;



import com.xinxu.user.constant.SignatureHeader;
import com.xinxu.user.model.AccountKeyInfoVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Resource
    private HttpServletRequest httpServletRequest;

    public AccountKeyInfoVO getAccountInfo(){
        System.out.println(httpServletRequest.hashCode());
        return  (AccountKeyInfoVO) httpServletRequest.getAttribute(SignatureHeader.AUTH_USER);
    }
}
