package com.huike.common.aspect;

import com.huike.common.annotation.AutoFill;
import com.huike.common.constant.AutoFillConstant;
import com.huike.common.enums.OperationType;
import com.huike.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Description TODO
 * @Author daqiang
 * @Date 2023-12-06 9:45
 */
@Component
@Slf4j
@Aspect
public class AutoFillAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.huike.*.mapper.*.*(..))&& @annotation(com.huike.common.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        //获取当前拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        OperationType operationType = annotation.value();//获得数据库操作类型

        //获取到当前被拦截的方法的参数--实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        //准备要赋值的数据
        String username = SecurityUtils.getUsername();
        Date date = new Date();

        //根据不同的操作类型，为对应的属性通过反射来进行赋值(因为不管更行还是创建都要设置这两个属性)
        try {
            Method setUpdateBy = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_BY, String.class);
            Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, Date.class);

            //通过反射为对象属性赋值
            setUpdateTime.invoke(entity, date);
            setUpdateBy.invoke(entity, username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //如果是更新操作就将创建人和创建时间补上
        if (operationType == OperationType.INSERT) {
            try {
                Method setCreateTime = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME, Date.class);
                Method setCreateBy = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_BY, String.class);

                //通过反射为对象属性赋值
                setCreateBy.invoke(entity, username);
                setCreateTime.invoke(entity, date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
