package com.br.aspect;

import com.google.common.collect.ImmutableList;
import com.br.annotation.Limit;
import com.br.exception.BadRequestException;
import com.br.utils.RequestHolder;
import com.br.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author /
 */
@Aspect
@Component
public class LimitAspect {

    private final RedisTemplate<Object,Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);

    public LimitAspect(RedisTemplate<Object,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(com.br.annotation.Limit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        Limit limit = signatureMethod.getAnnotation(Limit.class);
        com.br.aspect.LimitType limitType = limit.limitType();
        String key = limit.key();
        if (StringUtils.isEmpty(key)) {
            if (limitType == com.br.aspect.LimitType.IP) {
                key = StringUtils.getIp(request);
            } else {
                key = signatureMethod.getName();
            }
        }

        ImmutableList<Object> keys = ImmutableList.of(StringUtils.join(limit.prefix(), "_", key, "_", request.getRequestURI().replaceAll("/","_")));

        String luaScript = buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = redisTemplate.execute(redisScript, keys, limit.count(), limit.period());
        if (null != count && count.intValue() <= limit.count()) {
            logger.info("第{}次访问key为 {}，描述为 [{}] 的接口", count, keys, limit.name());
            return joinPoint.proceed();
        } else {
            throw new BadRequestException("访问次数受限制");
        }
    }

    /**
     * 作用：在超时时间内，访问次数不能大于限制值
     * 限流脚本逻辑：先获取值，如果有值且小于限制值，则返回值；如果无值，则自增1设置值，并针对首次设置的设置超时时间
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }
}
