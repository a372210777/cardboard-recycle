package com.br.config;

import com.br.utils.SecurityUtils;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
 * @description  : 设置审计，即自动填充实体类上@CreatedBy/@LastModifiedBy等注解
 * @author  : Dong ZhaoYang
 * @date : 2019/10/28
 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

    /**
     * 返回操作员标志信息
     *
     * @return /
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            // 这里应根据实际业务情况获取具体信息
            return Optional.of(SecurityUtils.getCurrentUsername());
        }catch (Exception ignored){}
        // 用户定时任务，或者无Token调用的情况
        return Optional.of("System");
    }


    /**
     * 由于在异步方法中使用repository保存对象，获取不到用户用户信息，需增加如下配置项，即可在Authentication获取用户的信息
     * SecurityContextHolder的主要功能是将当前执行的进程和SecurityContext关联起来。
       SecurityContextHolder.MODE_INHERITABLETHREADLOCAL ：
            用于线程有父子关系的情景中，子线程集成父线程的SecurityContextHolder；全局共用SecurityContextHolder
     */
    //@Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments(new String[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
        return methodInvokingFactoryBean;
    }
}
