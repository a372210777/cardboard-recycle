package com.br.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Linrl
 * @Date 2021/12/15 10:29
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

        SYS_SUPER_ADMIN("超级管理员"),
        CUSTOMER_SERVICE("客服");

        private final String name;


}
