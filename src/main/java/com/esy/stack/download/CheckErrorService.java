package com.esy.stack.download;

import com.esy.stack.dao.impl.ArticleMapper;
import com.esy.stack.dao.impl.WebSiteColumnMapper;
import com.esy.stack.entity.Article;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by guanjie on 2017/3/30.
 */
@Component
public class CheckErrorService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private WebSiteColumnMapper webSiteColumnMapper;

    public void checkError() {
        Article article = articleMapper.selectLastOne();
        long now = System.currentTimeMillis();
        long lastTime = article.getCreateTime().getTime();
        if((now - lastTime) > 60 * 60 * 1000L) {
            WebSiteColumn aWebSiteColumn = new WebSiteColumn();
            aWebSiteColumn.setStatus(StatusEnum.WAIT.getValue());
            webSiteColumnMapper.updateStatus(aWebSiteColumn);
        }
    }
}
