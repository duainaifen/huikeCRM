package com.huike.common.annotation;

import com.huike.common.enums.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-06 9:08
 */

/**
 * 自定义注解，用于自动填充公共字段
 */
@Target(ElementType.METHOD)//元注解标记该注解只能标记方法
@Retention(RetentionPolicy.RUNTIME)//元注解标记该注解作用范围
public @interface AutoFill {
    //数据库操作类型：UPDATE INSERT
    OperationType value();
}
