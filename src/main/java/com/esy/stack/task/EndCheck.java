package com.esy.stack.task;

import com.esy.stack.dao.impl.WebSiteColumnMapper;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by guanjie on 2017/3/24.
 */
@Component
public class EndCheck {
    @Autowired
    private WebSiteColumnMapper webSiteColumnMapper;

    /**
     * 用于检查，并改变栏目的状态
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void endUpdateStatus() {
        if (webSiteColumnMapper.checkIfAllHandler() == 1) {
            WebSiteColumn aWebSiteColumn = new WebSiteColumn();
            aWebSiteColumn.setStatus(StatusEnum.WAIT.getValue());
            webSiteColumnMapper.updateStatus(aWebSiteColumn);
        }
    }


}
