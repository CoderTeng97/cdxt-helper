package com.cdxt.common.annotation;

import java.lang.annotation.*;

/**
 * @Author: TengGang
 * @Date: 2019/7/7 13:41
 * @Description: 不封装返回
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerResponseIgnore {
}
