package com.br.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @website https://el-admin.vip
 * @description
 * @date 2020-05-18
 **/
@Data
@Component
public class RsaProperties {

    public static String privateKey;
    public static String publicKey;


    @Value("${rsa.private_key}")
    public void setPrivateKey(String privateKey) {
        RsaProperties.privateKey = privateKey;
    }

    @Value("${rsa.public_key}")
    public void setPublicKey(String publicKey) {
        RsaProperties.publicKey = publicKey;
    }
}