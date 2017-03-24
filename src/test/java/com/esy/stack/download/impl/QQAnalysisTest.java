package com.esy.stack.download.impl;

import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * QQAnalysis Tester.
 *
 * @author <guanjie>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Log4j
@ActiveProfiles("dev")
public class QQAnalysisTest {

//    @Autowired
//    private QQAnalysis qqAnalysis;
    //    @Autowired
    @Resource(name = "w163Analysis")
    private W163Analysis w163Analysis;

//    @Autowired
//    private ChinatimesAnalysis chinatimesProcess;
//    @Autowired
//    private THSAnalysis thsAnalysis;
//    @Autowired
//    private SinaAnalysis sinaAnalysis;
//    @Autowired
//    private JRTTAnalysis jrttAnalysis;
//    @Autowired
//    private PaperAnalysis paperAnalysis;

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
//        qqAnalysis.analys();
//        chinatimesProcess.analys();
//        thsAnalysis.analys();
//        sinaAnalysis.analys();
//        jrttAnalysis.analys();
//        paperAnalysis.analys();
        w163Analysis.analys();
        ;
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
