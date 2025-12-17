package com.vozni.springbootjwt.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
@Setter
@Getter
public class JwtProperties {
    String secret;
    int accessTokenDuration;
    int refreshTokenDuration;

}
