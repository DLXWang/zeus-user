package com.xinxu.user.filter;

import java.lang.annotation.*;

/**
 * @author wangxinxing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtCheckIgnore {

    String resource() default "/";

    boolean enable() default true;
}
