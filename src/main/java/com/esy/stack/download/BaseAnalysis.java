package com.esy.stack.download;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.esy.stack.dao.impl.ArticleMapper;
import com.esy.stack.dao.impl.WebSiteColumnMapper;
import com.esy.stack.dao.impl.WebSiteMainMapper;
import com.esy.stack.entity.ArticleWithBLOBs;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.entity.WebSiteMain;
import com.esy.stack.util.Constants;
import com.esy.stack.util.HttpClientUtil;
import com.esy.stack.util.StatusEnum;
/**
 * 网页分析基础类
 * @author guanjie
 *
 */
@Slf4j
public abstract class BaseAnalysis implements Analystor{
	

	
	/**
	 * 分析网站栏目接口，解析文章并存储
	 */
	@Override
	public void analys() {
		List<WebSiteColumn> columns = getWebSiteColumnDOs();
		if(CollectionUtils.isEmpty(columns))
			return;
		for (WebSiteColumn aWebSiteColumn : columns) {
			updateColumnStatus(aWebSiteColumn.getId(), StatusEnum.PROCESSING);
			for(String colunUrl : parseColumnUrl(aWebSiteColumn)) {
				String content = getIntefaceContent(colunUrl);
				if(StringUtils.isEmpty(content)) {
					updateColumnStatus(aWebSiteColumn.getId(), StatusEnum.FAILURE);
					continue;
				}
				List<ArticleWithBLOBs> articles = parseArticles(content, aWebSiteColumn);
				for (ArticleWithBLOBs each : articles) {
					try {
						articleMapper.insert(each);
					} catch (Exception e) {
						log.error("article插入异常", e);
					}
				}
				updateColumnStatus(aWebSiteColumn.getId(), StatusEnum.SUCCESS);
			}
		}
	}
	
	
	/**
	 * 获取站点id
	 * <p>网站实体处理对象</p>
	 * @return int
	 */
	protected abstract int getWebSiteId();
	
	/**
	 * 解析文章内容，并返回稿件对象
	 * @param content
	 * @return
	 */
	protected abstract List<ArticleWithBLOBs> parseArticles(String content, WebSiteColumn aWebSiteColumn);
	
	/**
	 * 拼凑栏目url
	 * @return
	 */
	protected abstract List<String> parseColumnUrl(WebSiteColumn aWebSiteColumn);
	

	
	/**
	 * 获取站点栏目对象
	 * @return
	 */
	private List<WebSiteColumn> getWebSiteColumnDOs(){
		WebSiteColumn record = new WebSiteColumn();
		record.setWebSiteId(getWebSiteMainDO().getId());
		record.setStatus(StatusEnum.WAIT.getValue());
		return webSiteColumnMapper.select(record);
	}
	
	/**
	 * 改变栏目状态
	 * @param aWebSiteColumn
	 * @param status
	 */
	private void updateColumnStatus(int columnId, StatusEnum status){
		WebSiteColumn aWebSiteColumn = new WebSiteColumn();
		aWebSiteColumn.setId(columnId);
		aWebSiteColumn.setStatus(status.getValue());
		webSiteColumnMapper.updateByPrimaryKeySelective(aWebSiteColumn);
	}


	/**
	 * 获取接口提供的网页内容
	 * @param url
	 * @return null 如果接口异常
	 */
	private String getIntefaceContent(String url) {
		String content = null;
		try {
			content = HttpClientUtil.downloadToString(url, getWebSiteMainDO().getWebChar());
		} catch (IOException e) {
			log.error("url内容提取异常", e);
			return null;
		}
		if(getWebSiteMainDO().getWebChar().equals(Constants.BASE_CHARSET))
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
	 * @return
	 */
	private WebSiteMain getWebSiteMainDO() {
		if(webSiteMain == null) {
			synchronized (this) {
				if(webSiteMain == null)
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
