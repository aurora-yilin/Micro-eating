package com.lyl;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * Test
 *
 * @author lyl
 * @date 2020/12/8 16:06
 * @since 1.0.0
 **/
@SpringBootTest
public class Test {
    @org.junit.jupiter.api.Test
    public void TestUUID(){
        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }
}
