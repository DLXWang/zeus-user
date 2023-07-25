package com.xinxu.user.exception;


import com.xinxu.user.util.MapMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * valid 异常 处理
     */
    @ExceptionHandler(ExpireException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MapMessage handleException(ExpireException exception) {
        return MapMessage.errorMessage("token 超时").setErrorCode("555");
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MapMessage lastHandleException(BadCredentialsException exception) {
        return MapMessage.errorMessage(exception.getMessage());
    }

}
