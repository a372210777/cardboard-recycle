package com.br.common.utils;

import com.br.modules.security.service.dto.JwtUserDto;
import com.br.utils.SecurityUtils;
import org.springframework.util.Assert;

public class U {

    /**
     * 获取当前登录用户
     * @return
     */
    public static JwtUserDto getCurrentUser () {
        return (JwtUserDto)SecurityUtils.getCurrentUser();
    }


}
