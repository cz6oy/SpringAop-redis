package cn.czboy.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;

@Configuration
@Aspect
public class RedisCache {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(cn.czboy.annotation.AddCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuilder sb = new StringBuilder();
        //获取类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        System.out.println("类名："+className);

        //获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        System.out.println("方法名："+methodName);
        sb.append(methodName);

        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg:args){
            System.out.println("参数："+arg);
            sb.append(arg);
        }
        String str = sb.toString();
        System.out.println("SpringBuilder："+sb);
        Boolean aBoolean = redisTemplate.hasKey(className);
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object  result = null;
        if(aBoolean){
            result = hashOperations.get(className,str);
        }else{
            result = proceedingJoinPoint.proceed();
            HashMap<Object, Object> map = new HashMap<>();
            map.put(str,result);
            hashOperations.putAll(className,map);
        }
        return result;
    }
}