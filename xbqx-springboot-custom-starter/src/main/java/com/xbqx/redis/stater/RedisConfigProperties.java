package com.xbqx.redis.stater;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mr.Gao
 * @date 2023/1/11 18:33
 * @apiNote:
 */
@ConditionalOnProperty(prefix = "redission", name = "enabled", havingValue = "true", matchIfMissing = false)
@ConfigurationProperties(prefix = "jack.redis")
public class RedisConfigProperties {


    private String username;
    private String password;

    private String host;

    private int port;

    private Integer timeout;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
