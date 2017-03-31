package com.esy.stack.download;

import com.esy.stack.dao.impl.ArticleMapper;
import com.esy.stack.dao.impl.WebSiteColumnMapper;
import com.esy.stack.dao.impl.WebSiteMainMapper;
import com.esy.stack.entity.Article;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.entity.WebSiteMain;
import com.esy.stack.util.Constants;
import com.esy.stack.util.HttpClientUtil;
import com.esy.stack.util.StatusEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 网页分析基础类
 *
 * @author guanjie
 */
@Slf4j
public abstract class BaseAnalysis implements Analystor {


    /**
     * 分析网站栏目接口，解析文章并存储
     * <ol>
     * <li>获取站点所有待处理栏目</li>
     * <li>改变栏目状态为处理中</li>
     * <li>爬虫爬取网页，解析文章，入库</li>
     * </ol>
     */
    @Override
    public void analys() {
        List<WebSiteColumn> columns = getWebSiteColumnDOs();
        if (CollectionUtils.isEmpty(columns))
            return;
        for (WebSiteColumn column : columns)
            updateColumnStatus(column.getId(), StatusEnum.PROCESSING);
        for (WebSiteColumn aWebSiteColumn : columns) {
            try {
                handleEachColumn(aWebSiteColumn);
            } catch (RuntimeException e) {
                log.error("网站接口解析异常:", e);
            }
            updateColumnStatus(aWebSiteColumn.getId(), StatusEnum.SUCCESS);
        }
    }

    /**
     * 改变栏目状态为处理中
     *
     * @param aWebSiteColumn
     */
    private void handleEachColumn(WebSiteColumn aWebSiteColumn) {
        for (String colunUrl : parseColumnUrl(aWebSiteColumn)) {
            String content = getIntefaceContent(colunUrl);
            if (StringUtils.isEmpty(content)) {
                continue;
            }
            List<Article> articles = parseArticles(content, aWebSiteColumn);
            for (Article each : articles) {
                try {
                    each.setStatus(StatusEnum.WAIT.getValue());
                    articleMapper.insert(each);
                } catch (Exception e) {
                    log.error("article插入异常" + e.getMessage());
                }
            }
        }

    }


    /**
     * 获取站点id
     * <p>网站实体处理对象</p>
     *
     * @return int
     */
    protected abstract int getWebSiteId();

    /**
     * 解析文章内容，并返回稿件对象
     *
     * @param content
     * @return
     */
    protected abstract List<Article> parseArticles(String content, WebSiteColumn aWebSiteColumn);

    /**
     * 拼凑栏目url
     *
     * @return
     */
    protected abstract List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn);


    /**
     * 获取站点栏目对象
     *
     * @return
     */
    private List<WebSiteColumn> getWebSiteColumnDOs() {
        WebSiteColumn record = new WebSiteColumn();
        record.setWebSiteId(getWebSiteMainDO().getId());
        record.setStatus(StatusEnum.WAIT.getValue());
        return webSiteColumnMapper.select(record);
    }

    /**
     * 改变栏目状态
     *
     * @param aWebSiteColumn
     * @param status
     */
    private void updateColumnStatus(Integer columnId, StatusEnum status) {
        WebSiteColumn aWebSiteColumn = new WebSiteColumn();
        aWebSiteColumn.setId(columnId);
        aWebSiteColumn.setStatus(status.getValue());
        webSiteColumnMapper.updateStatus(aWebSiteColumn);
    }

    /**
     * 获取接口提供的网页内容
     * <p>爬虫方法，模拟人的行为，睡眠1秒钟</p>
     *
     * @param url
     * @return null 如果接口异常
     */
    private String getIntefaceContent(String url) {
        String content = null;
        try {
            content = HttpClientUtil.downloadToString(url, getWebSiteMainDO().getWebChar());
            TimeUnit.SECONDS.sleep(1);
        } catch (IOException e) {
            log.error("url内容提取异常", e);
        } catch (InterruptedException e) {
            log.error("url内容提取异常", e);
        }
        if (getWebSiteMainDO().getWebChar().equals(Constants.BASE_CHARSET))
            return content;
        try {
            return contentEncode(content);
        } catch (UnsupportedEncodingException e) {
            log.error(content, e);
            return null;
        }
    }

    /**
     * 内容进行编码（utf-8）
     *
     * @param content
     * @return
     * @throws UnsupportedEncodingException
     */
    private String contentEncode(String content) throws UnsupportedEncodingException {
        Assert.notNull(content);
        ByteBuffer buf = Charset.forName(Constants.BASE_CHARSET).encode(content);
        return new String(buf.array(), Constants.BASE_CHARSET);
    }

    /**
     * 获取站点对象
     *
     * @return
     */
    private WebSiteMain getWebSiteMainDO() {
        if (webSiteMain == null) {
            synchronized (this) {
                if (webSiteMain == null)
                    webSiteMain = webSiteMainMapper.selectByPrimaryKey(getWebSiteId());
            }
        }
        return webSiteMain;
    }

    private WebSiteMain webSiteMain;

    @Getter
    @Autowired
    private WebSiteColumnMapper webSiteColumnMapper;

    @Getter
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private WebSiteMainMapper webSiteMainMapper;
}
