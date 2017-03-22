package com.esy.stack.download.impl;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * QQAnalysis Tester.
 *
 * @author <guanjie>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Log4j
public class QQAnalysisTest {

    @Autowired
    private QQAnalysis qqAnalysis;
    @Autowired
    private W163Analysis w163Analysis;
    @Autowired
    private ChinatimesAnalysis chinatimesProcess;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getWebSiteId()
     */
    @Test
    public void testGetWebSiteId() throws Exception {
        log.warn(qqAnalysis.getWebSiteId());
//        qqAnalysis.analys();
        chinatimesProcess.analys();
    }

    /**
     * Method: parseArticles(String content, WebSiteColumn aWebSiteColumn)
     */
    @Test
    public void testParseArticles() throws Exception {
    }

    /**
     * Method: parseColumnUrl(WebSiteColumn aWebSiteColumn)
     */
    @Test
    public void testParseColumnUrl() throws Exception {

    }


} 
