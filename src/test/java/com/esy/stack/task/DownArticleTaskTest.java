package com.esy.stack.task;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * DownArticleTask Tester.
 *
 * @author <guanjie>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@ActiveProfiles(value = "dev")
@Log4j
public class DownArticleTaskTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Autowired
    DownArticleTask downArticleTask;

    /**
     * Method: download()
     */
    @Test
    public void testDownload() throws Exception {
        downArticleTask.download();
        TimeUnit.SECONDS.sleep(20);
    }

    /**
     * Method: run()
     */
    @Test
    public void testRun() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getCharSet(Article article)
     */
    @Test
    public void testGetCharSet() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = DownArticleTask.getClass().getMethod("getCharSet", Article.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: listWaitingArticles()
     */
    @Test
    public void testListWaitingArticles() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = DownArticleTask.getClass().getMethod("listWaitingArticles"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
