package com.wenyu.oauth.aspect;

import com.wenyu.Enum.StatusEnum;
import com.wenyu.model.BaseLongEntity;
import com.wenyu.oauth.model.Sequence;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.management.Query;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by zhaowy on 15/5/19.
 */
@Component
@Aspect
public class ModelAspect {
    @Pointcut("execution(* com.wenyu.oauth.dao.*.add*(..)) ||execution(* com.wenyu.oauth.dao.*.insert*(..))")
    public void pointCutAdd() {
    }

    @Pointcut("execution(* com.wenyu.oauth.dao.*.update*(..))")
    public void pointCutUpdate() {
    }

    @Pointcut("execution(* com.wenyu.oauth.dao.*.delete*(..)) || execution(* com.wenyu.oauth.dao.*.remove*(..))")
    public void pointCutDelete() {
    }

    @Pointcut("execution(* com.wenyu.oauth.dao.*.get*(..)) || execution(* com.wenyu.oauth.*.find*(..))||execution(* com.wenyu.oauth.dao.*.select*(..))")
    public void pointCutSelect() {
    }

    @Around("pointCutAdd()")
    public Object Aroundadd(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            for (Object object : args) {
                if (object instanceof BaseLongEntity) {
                    ((BaseLongEntity) object).setCreateTime(new Date());
                    ((BaseLongEntity) object).setStatus(StatusEnum.ENABLE);
                    //反射调用生成id方法
                    Method method = pjp.getTarget().getClass().getMethod("getIntSqlId");
                    Sequence sequence = (Sequence) method.invoke(pjp.getTarget());
                    ((BaseLongEntity) object).setId(sequence.getSeq());

                }
//                else {
//                    Method method = object.getClass().getDeclaredMethod("set_id", ObjectId.class);
//                    method.invoke(object, new ObjectId());
//                }

            }
        }
        Object o = pjp.proceed();
        return o;
    }

    @Around("pointCutUpdate()")
    public Object Aroundupdate(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            for (Object object : args) {

            }
        }
        Object o = pjp.proceed();
        return o;
    }

    @Around("pointCutDelete()")
    public Object Arounddelete(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            for (Object object : args) {
                if (object instanceof BaseLongEntity) {
                    ((BaseLongEntity) object).setModifyTime(new Date());
                    ((BaseLongEntity) object).setStatus(StatusEnum.DISABLE);

                }
            }
        }
        Object o = pjp.proceed();
        return o;
    }

    //	@Around("pointCutSelect()")
    public Object Aroundselect(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            for (Object object : args) {
                if (object instanceof Query) {

                }
            }
        }
        Object o = pjp.proceed();
        return o;
    }
}
