package com.esy.stack;

import org.junit.Test;
import org.springframework.core.env.StandardEnvironment;

/**
 * Created by guanjie on 2017/4/3.
 */
public class AllSpringTest {
    @Test
    public void test001() {
        StandardEnvironment environment = new StandardEnvironment();
        environment.validateRequiredProperties();
    }
}
