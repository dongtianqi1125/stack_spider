package com.esy.stack.download.impl;

import com.alibaba.fastjson.JSON;
import com.esy.stack.download.BaseAnalysis;
import com.esy.stack.entity.Article;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.Constants;
import com.esy.stack.util.StringManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by guanjie on 2017/3/22.
 * 今日头条
 */
@Component
public class JRTTAnalysis extends BaseAnalysis {

    private static final StringManager stringManager = StringManager.getStringManageByFileName(Constants.HANDLER_PROP_PATH);

    private static final String JRTT_WEBSITE_DOMAIN_NAME = stringManager.getValue("jrtt_website_domain_name");

    @Override
    protected int getWebSiteId() {
        return stringManager.getIntValue("jrtoutiao_website_id");
    }

    @Override
    protected List<Article> parseArticles(String content, WebSiteColumn aWebSiteColumn) {
        List<Article> result = new ArrayList<>();
        JRTTJsonDTO jrttJsonDTO = JSON.parseObject(content, JRTTJsonDTO.class);
        List<JRTTDTO> dtolist = jrttJsonDTO.getData();
        for (JRTTDTO jrttdto : dtolist) {
            Article Article = createBy(jrttdto);
            Article.setColumnId(aWebSiteColumn.getId());
            result.add(Article);
        }
        return result;
    }

    private Article createBy(JRTTDTO record) {
        Article article = new Article();
        article.setCreateTime(new Date());
        article.setTag(record.getTag());
        article.setTitle(record.getTitle());
        article.setUrl(JRTT_WEBSITE_DOMAIN_NAME + record.getSource_url());
        Long pushtime = record.getBehot_time();
        article.setPublishTime(pushtime == null ? new Date() : new Date(pushtime * 1000));
        return article;
    }

    @Override
    protected List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn) {
        return Arrays.asList(aWebSiteColumn.getColumnUrl());
    }

    @Setter
    @Getter
    static class JRTTDTO {
        private String chinese_tag;
        private String tag_url;
        private String title;
        private String tag;
        private Long behot_time;
        private String source_url;
        private String source;
        private String article_genre;
    }

    @Setter
    @Getter
    static class JRTTJsonDTO{
        private List<JRTTDTO> data;
    }
}
