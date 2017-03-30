package com.esy.stack.dao.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by guanjie on 2017/3/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-dao.xml")
@ActiveProfiles("prod")
public class ArticleMapperTest {
    @Autowired
    ArticleMapper articleMapper;

    @Test
    public void testSelect() {
        PageHelper.startPage(0, 10);
        System.out.println(JSON.toJSON(articleMapper.select(null)));
    }

    @Test
    public void testSelectCharSetByArticleId() {
        System.out.println(articleMapper.selectCharSetByArticleId(2272));;
        System.out.println(path);
    }
    @Test
    public void testSelectLastOne() {
        System.out.println(articleMapper.selectLastOne());
    }

    @Value(value = "#{serviceConfig['website.html.downloadpath']}")
    private String path;
}
