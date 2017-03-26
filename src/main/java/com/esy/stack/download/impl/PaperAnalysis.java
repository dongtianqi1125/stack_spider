package com.esy.stack.download.impl;

import com.esy.stack.download.BaseAnalysis;
import com.esy.stack.entity.Article;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.Constants;
import com.esy.stack.util.StringManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by guanjie on 2017/3/22.
 * 澎湃新闻
 */
@Component
public class PaperAnalysis extends BaseAnalysis {

    private static final StringManager stringManager = StringManager.getStringManageByFileName(Constants.HANDLER_PROP_PATH);
    private static final String PAPER_WEBSITE_DOMAIN_NAME = stringManager.getValue("pengpai_website_domain_name");

    @Override
    protected int getWebSiteId() {
        return stringManager.getIntValue("pengpai_website_id");
    }

    @Override
    protected List<Article> parseArticles(String content, WebSiteColumn aWebSiteColumn) {
        List<Article> result = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Elements atagList = doc.select(".t_news .news_tit02 a");
        for (Element each : atagList) {
            Article record = new Article();
            record.setTitle(each.text());
            record.setUrl(PAPER_WEBSITE_DOMAIN_NAME + each.attr("href"));
            record.setCreateTime(new Date());
            record.setColumnId(aWebSiteColumn.getId());
            result.add(record);
        }
        return result;
    }

    @Override
    protected List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn) {
        return Arrays.asList(aWebSiteColumn.getColumnUrl());
    }
}
