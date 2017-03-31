package com.esy.stack.task;

import com.esy.stack.dao.impl.WebSiteColumnMapper;
import com.esy.stack.download.CheckErrorService;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.StatusEnum;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by guanjie on 2017/3/24.
 */
@Component
@Log4j
public class EndCheck {


    /**
     * 用于检查，并改变栏目的状态
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void endUpdateStatus() {
        log.warn("=============用于检查，并改变栏目的状态==================");
        checkErrorService.checkAndUpdate();
    }
    @Autowired
    private CheckErrorService checkErrorService;

    /**
     * 用于检查错误
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void checkError() {
        checkErrorService.checkError();
    }

}
