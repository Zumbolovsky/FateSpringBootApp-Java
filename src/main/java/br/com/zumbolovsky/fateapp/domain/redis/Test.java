package br.com.zumbolovsky.fateapp.domain.redis;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;

@RedisHash("Test")
public class Test implements Serializable {
    @Serial
    private static final long serialVersionUID = -2254515702608287255L;

    private Integer id;
    private String name;

    public Test(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Test() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
