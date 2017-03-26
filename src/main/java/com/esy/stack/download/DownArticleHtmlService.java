package com.esy.stack.download;

import com.esy.stack.dao.impl.ArticleDetailMapper;
import com.esy.stack.dao.impl.ArticleMapper;
import com.esy.stack.entity.Article;
import com.esy.stack.entity.ArticleDetail;
import com.esy.stack.entity.ArticleDetailWithBLOBs;
import com.esy.stack.util.HttpClientUtil;
import com.esy.stack.util.StatusEnum;
import com.github.pagehelper.PageHelper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by guanjie on 2017/3/26.
 */
@Log4j
@Component
public class DownArticleHtmlService {

    @Value(value = "#{serviceConfig['website.html.downloadpath']}")
    private String path;

    @Autowired
    private ArticleDetailMapper articleDetailMapper;

    public int countWaitArticles() {
        Article record = new Article();
        record.setStatus(StatusEnum.WAIT.getValue());
        return articleMapper.countWaitArticle(record);
    }
    @Transactional
    public void download(int start, int count) {
        List<Article> articles = listWaitingArticles(start, count);
        for (Article each : articles) {
            File parentDir = getAndCreateDir(each.getColumnId().toString());
            String charSet = getCharSet(each);
            try {
                String content =  HttpClientUtil.downloadToString(each.getUrl(), charSet);
                File htmlFile = new File(parentDir, System.currentTimeMillis() + ".html");
                FileCopyUtils.copy(content.getBytes(charSet), htmlFile);
                each.setStatus(StatusEnum.SUCCESS.getValue());
                Integer times = each.getTimes();
                each.setTimes(times == null ? 0 : times + 1);
                articleMapper.updateByPrimaryKey(each);
                saveArticleDetail(htmlFile.getAbsolutePath());
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    private void saveArticleDetail(String path) {
        ArticleDetailWithBLOBs record = new ArticleDetailWithBLOBs();
        record.setHtmlPath(path);
        articleDetailMapper.insert(record);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

    public static void main(String[] args) {
        System.out.println();
    }

    private File getAndCreateDir(String columnId) {
        File dir = new File(path + "/" + columnId);
        if(!dir.exists())
            dir.mkdirs();
        return dir;
    }

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取待处理的article对象
     *
     * @return
     */
    private List<Article> listWaitingArticles(int start, int count) {
        PageHelper.startPage(start, count);
        Article article = new Article();
        article.setStatus(StatusEnum.WAIT.getValue());
        return articleMapper.select(article);
    }

    /**
     * 通过给定的article对象获取article的编码
     *
     * @param article
     * @return
     */
    private String getCharSet(Article article) {
        String charset = WEB_CHAR_CACHE.get(article.getColumnId());
        if (charset == null) {
            synchronized (this) {
                charset = articleMapper.selectCharSetByArticleId(article.getId());
                WEB_CHAR_CACHE.putIfAbsent(article.getColumnId(), charset);
            }
        }
        return charset;
    }

    private final static ConcurrentHashMap<Integer, String> WEB_CHAR_CACHE = new ConcurrentHashMap<>();
}
