package com.esy.stack.task;

import java.util.List;
import java.util.concurrent.Executor;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.esy.stack.download.Downloadable;
import com.esy.stack.download.TimerTask;
@Component
@Log4j
public class IntefaceProcessTask implements TimerTask{
//	@Autowired
//	private List<Downloadable> analysis;
	
	@Autowired
	private Executor executor;
	
	@Scheduled(cron = "0 0/5 * * * ?")
	@Override
	public void work() {
		log.warn("###################定时启动分析网页数据开始#####################");
		
//		for (BaseAnalysis analystor : analysis) {
//			log.info("处理：" + analystor.getClass().getName());
//			executor.execute(analystor);
//		}
		log.warn("###################定时启动分析网页数据结束#####################");
		
	}
}
