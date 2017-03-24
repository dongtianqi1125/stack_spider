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
 * 腾讯
 * @author guanjie
 *
 */
@Component
public class QQAnalysis extends BaseAnalysis{
	
	private static final StringManager stringManager = StringManager.getStringManageByFileName(Constants.HANDLER_PROP_PATH);
	
	private static final String SELECT = ".Q-tpList .Q-tpWrap a";
	
	@Override
	protected int getWebSiteId() {
		return stringManager.getIntValue("qq_web_site_id");
	}

	@Override
	protected List<ArticleWithBLOBs> parseArticles(String content, WebSiteColumn aWebSiteColumn) {
		List<ArticleWithBLOBs> result = new ArrayList<ArticleWithBLOBs>();
		Document doc = Jsoup.parse(content);
		Elements atagList = doc.select(SELECT);
		for (Element each : atagList) {
			ArticleWithBLOBs record = new ArticleWithBLOBs();
			record.setTitle(each.text());
			record.setUrl(aWebSiteColumn.getDomainUrl() + each.attr(Constants.ATTR_HREF));
			record.setCreateTime(new Date());
			record.setColumnId(aWebSiteColumn.getId());
			result.add(record);
		}
		return result;
	}

	@Override
	protected List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn) {
		List<String> result = new ArrayList<String>();
			for(int i = 1; i <= aWebSiteColumn.getTotalPages(); i++)
				result.add(MessageFormat.format(aWebSiteColumn.getColumnUrl(), i));
		return result;
	}

}
