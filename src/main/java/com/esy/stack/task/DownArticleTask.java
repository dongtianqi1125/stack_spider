package com.esy.stack.task;

import com.esy.stack.dao.impl.ArticleDetailMapper;
import com.esy.stack.dao.impl.ArticleMapper;
import com.esy.stack.download.DownArticleHtmlService;
import com.esy.stack.download.Downloadable;
import com.esy.stack.entity.Article;
import com.esy.stack.util.HttpClientUtil;
import com.esy.stack.util.StatusEnum;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by guanjie on 2017/3/25.
 * 定时任务下载网页内容
 */
@Component
public class DownArticleTask implements Downloadable {

    @Autowired
    private ExecutorService executor;
    @Autowired
    private DownArticleHtmlService downArticleHtmlService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    @Override
    public void download() {
        int count = downArticleHtmlService.countWaitArticles();
        if (count <= 10)
            downArticleHtmlService.download(0, count);
        int taskCount = count / 10;
        for (int i = 0; i < taskCount; i++) {
            final int start = i * 10;
            executor.execute(() -> downArticleHtmlService.download(start, 10));
        }
    }

}
