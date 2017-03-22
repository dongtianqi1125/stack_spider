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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guanjie on 2017/3/22.
 * 新浪
 */
@Component
public class SinaAnalysis extends BaseAnalysis {

    private static final StringManager stringManager = StringManager.getStringManageByFileName(Constants.HANDLER_PROP_PATH);

    @Override
    protected int getWebSiteId() {
        return stringManager.getIntValue("sina_website_id");
    }

    @Override
    protected List<ArticleWithBLOBs> parseArticles(String content, WebSiteColumn aWebSiteColumn) {
        List<ArticleWithBLOBs> result = new ArrayList<ArticleWithBLOBs>();
        Document doc = Jsoup.parse(content);
        System.out.println("doc :" + doc);
        Elements atagList = doc.select("#Main .listBlk .list_009 a[href ^= http://finance.sina.com.cn/stock]");
        for (Element each : atagList) {
            ArticleWithBLOBs record = new ArticleWithBLOBs();
            record.setTitle(each.text());
            record.setUrl(each.attr("href"));
            record.setCreateTime(new Date());
            record.setColumnId(aWebSiteColumn.getId());
            result.add(record);
        }
        return result;
    }

    @Override
    protected List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn) {
        List<String> result = new ArrayList<String>();
        for (int i = 1; i <= aWebSiteColumn.getTotalPages(); i++)
            result.add(MessageFormat.format(aWebSiteColumn.getColumnUrl(), i));
        return result;
    }
}
