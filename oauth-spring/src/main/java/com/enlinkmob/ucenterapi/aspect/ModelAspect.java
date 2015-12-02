package com.enlinkmob.ucenterapi.aspect;

import com.enlinkmob.ucenterapi.Enum.StatusEnum;
import com.enlinkmob.ucenterapi.model.BaseLongEntity;
import com.enlinkmob.ucenterapi.model.Sequence;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by zhaowy on 15/5/19.
 */
@Component
@Aspect
public class ModelAspect {
    @Pointcut("execution(* com.enlinkmob.ucenterapi.dao.*.add*(..)) ||execution(* com.enlinkmob.ucenterapi.dao.*.insert*(..))")
    public void pointCutAdd() {
    }

    @Pointcut("execution(* com.enlinkmob.ucenterapi.dao.*.update*(..))")
    public void pointCutUpdate() {
    }

    @Pointcut("execution(* com.enlinkmob.ucenterapi.dao.*.delete*(..)) || execution(* com.enlinkmob.ucenterapi.dao.*.remove*(..))")
    public void pointCutDelete() {
    }

    @Pointcut("execution(* com.enlinkmob.ucenterapi.dao.*.get*(..)) || execution(* com.enlinkmob.ucenterapi.dao.*.find*(..))||execution(* com.enlinkmob.ucenterapi.dao.*.select*(..))")
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
                if (object instanceof Update) {
                    ((Update) object).set("modifyTime", new Date());

                }

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
                    ((Query) object).addCriteria(Criteria.where("status").is(1));

                }
            }
        }
        Object o = pjp.proceed();
        return o;
    }
}
