package com.chave.pojo;

import lombok.*;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Data
@ToString
@Component
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String salt;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        // 注册时生成随机 salt
        SecureRandom random = new SecureRandom();
        StringBuilder saltBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            saltBuilder.append((char) (random.nextInt(26) + 'a'));
        }
        this.salt = saltBuilder.toString();
    }
}
