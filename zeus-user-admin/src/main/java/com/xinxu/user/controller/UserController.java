package com.xinxu.user.controller;

import com.xinxu.user.controller.common.BaseController;
import com.xinxu.user.filter.JwtCheckIgnore;
import com.xinxu.user.model.BaseUserDTO;
import com.xinxu.user.model.UserLoginDTO;
import com.xinxu.user.service.IUserManageService;
import com.xinxu.user.util.MapMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@RestController
@Api(tags = "用户")
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController extends BaseController {
    private final IUserManageService iUserManageService;

    @ApiOperation(value = "用户注册")
    @JwtCheckIgnore
    @PostMapping(value = "/register")
    public MapMessage register(@RequestBody @Valid BaseUserDTO baseUserDTO) {
        iUserManageService.register(baseUserDTO);
        return MapMessage.successMessage();
    }

    @ApiOperation(value = "用户登录并返回token")
    @PostMapping(value = "/login")
    @JwtCheckIgnore
    public MapMessage login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        Pair<String, String> token = iUserManageService.login(userLoginDTO);
        return MapMessage.successMessage().of("access", token.getLeft(), "refresh", token.getRight());
    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    @JwtCheckIgnore
    public MapMessage refreshToken(@RequestParam("refreshToken") @Valid @NotBlank String refreshToken) {
        Pair<String, String> token = iUserManageService.refresh(refreshToken);
        return MapMessage.successMessage().of("access", token.getLeft(), "refresh", token.getRight());
    }

    @ApiOperation("校验和获取用户信息")
    @GetMapping("/account")
    @JwtCheckIgnore
    public MapMessage getAccountDetail(@RequestParam("token") @Valid @NotBlank String token) {
        return MapMessage.successMessage().of("content", iUserManageService.authAndAppend(token));
    }

    @ApiOperation("测试")
    @GetMapping("/test")
    public MapMessage test() {
        return MapMessage.successMessage().of("content", getAccountInfo());
    }

}
