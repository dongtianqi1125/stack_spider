package com.esy.stack.task;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * IntefaceProcessTask Tester.
 *
 * @author <guanjie>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Log4j
public class IntefaceProcessTaskTest {
    @Autowired
    IntefaceProcessTask intefaceProcessTask;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: work()
     */
    @Test
    public void testWork() throws Exception {
        intefaceProcessTask.work();
        TimeUnit.SECONDS.sleep(20);
    }


} 
