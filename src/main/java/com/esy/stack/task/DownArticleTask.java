package com.esy.stack.task;

import com.esy.stack.download.DownArticleHtmlService;
import com.esy.stack.download.Downloadable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

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

    @Scheduled(cron = "0 0/30 * * * ?")
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
