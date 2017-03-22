package com.esy.stack.download.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.esy.stack.download.BaseAnalysis;
import com.esy.stack.entity.ArticleWithBLOBs;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.Constants;
import com.esy.stack.util.StringManager;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 网易
 * Created by guanjie on 2017/3/21.
 */
@Component
public class W163Analysis extends BaseAnalysis {

    private static final StringManager stringManager = StringManager.getStringManageByFileName(Constants.HANDLER_PROP_PATH);

    @Override
    protected int getWebSiteId() {
        return stringManager.getIntValue("chinatimes_web_site_id");
    }

    /**
     * 网页接口回掉函数名
     */
    private static String dataCallback = stringManager.getValue("data_callback");

    @Override
    protected List<ArticleWithBLOBs> parseArticles(String content, WebSiteColumn aWebSiteColumn) {
        if(StringUtils.isEmpty(content))
            return Collections.emptyList();
        int start = dataCallback.length() + 1;
        String newContent = content.trim();
        int end = newContent.length() - 1;
        List<W163DO> records = JSON.parseArray(newContent.substring(start, end), W163DO.class);
        List<ArticleWithBLOBs> result = new ArrayList<ArticleWithBLOBs>();
        for (W163DO w163DO : records) {
            ArticleWithBLOBs articleWithBLOBs = createBy(w163DO);
            articleWithBLOBs.setColumnId(aWebSiteColumn.getId());
            result.add(articleWithBLOBs);
        }
        return result;
    }

    private ArticleWithBLOBs createBy(W163DO record) {
        ArticleWithBLOBs articleRecord = new ArticleWithBLOBs();
        articleRecord.setCreateTime(new Date());
        articleRecord.setPublishTime(record.getTime());
        articleRecord.setUrl(record.getDocurl());
        articleRecord.setTitle(record.getTitle());
        return articleRecord;
    }


    @Override
    protected List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn) {
        return Arrays.asList(aWebSiteColumn.getColumnUrl());
    }

    /**
     * 网页财经rpc调用返回领域对象
     * @author guanjie
     *
     */
    @Data
    private static class W163DO{
        private String title;
        private String docurl;
        @JSONField(format="MM/dd/yyyyHH:mm:ss")
        private Date time;
        private String newstype;
        private String imgurl;
        private String label;
        List<KeyWord> keywords;
    }

    @Data
    private static class KeyWord {
        private String keyname;
        private String akey_link;
    }
}
