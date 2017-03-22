package com.esy.stack.download.impl;

import com.esy.stack.download.BaseAnalysis;
import com.esy.stack.entity.ArticleWithBLOBs;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.Constants;
import com.esy.stack.util.StringManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 华夏时报
 * Created by guanjie on 2017/3/21.
 */
@Component
public class ChinatimesAnalysis extends BaseAnalysis {

    private static final StringManager stringManager = StringManager.getStringManageByFileName(Constants.HANDLER_PROP_PATH);

    private static final String CHINATIMES_WEBSITE_DOMAIN_NAME = stringManager.getValue("chinatimes_website_domain_name");

    @Override
    protected int getWebSiteId() {
        return stringManager.getIntValue("chinatimes_web_site_id");
    }

    @Override
    protected List<ArticleWithBLOBs> parseArticles(String content, WebSiteColumn aWebSiteColumn) {
        if (StringUtils.isEmpty(content))
            return Collections.emptyList();
        List<ArticleWithBLOBs> result = new ArrayList<>();
        Document doc = Jsoup.parse(content);
        Elements atagList = doc.select(".list_litpic a");
        for (Element each : atagList) {
            Elements h2tags = each.select("h2");
            if(h2tags == null || h2tags.isEmpty())
                continue;
            ArticleWithBLOBs record = new ArticleWithBLOBs();
            record.setTitle(h2tags.get(0).text());
            record.setUrl(CHINATIMES_WEBSITE_DOMAIN_NAME + each.attr("href"));
            record.setCreateTime(new Date());
            record.setColumnId(aWebSiteColumn.getId());
            result.add(record);
        }
        Elements toplist = doc.select(".list_top a");
        if(toplist != null && !toplist.isEmpty()) {
            ArticleWithBLOBs top = createTop(toplist.get(0));
            if(top != null)
                top.setColumnId(aWebSiteColumn.getId());
                result.add(top);
        }
        return result;
    }

    private ArticleWithBLOBs createTop(Element top) {
        Elements h2tags = top.select("h1");
        if(h2tags == null || h2tags.isEmpty())
            return null;
        ArticleWithBLOBs record = new ArticleWithBLOBs();
        record.setTitle(h2tags.text());
        record.setUrl(CHINATIMES_WEBSITE_DOMAIN_NAME + top.attr("href"));
        record.setCreateTime(new Date());
        return record;
    }


    @Override
    protected List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn) {
        return Arrays.asList(aWebSiteColumn.getColumnUrl());
    }
}
