package com.esy.stack.dao.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dao.xml")
@ActiveProfiles("dev")
public class WebSiteColumnMapperTest {
    @Autowired
    private WebSiteColumnMapper webSiteColumnMapper;

    @Test
    public void testSelect() {
        System.out.println(webSiteColumnMapper);
    }

    @Test
    public void testCheckIfAllHandler() {
        System.out.println(webSiteColumnMapper.checkIfAllHandler());
    }

    public void testUpdateStatusById() {

    }

}
