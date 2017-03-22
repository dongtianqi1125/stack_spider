package com.esy.stack.task;

import com.esy.stack.download.Analystor;
import com.esy.stack.download.TimerTask;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
@Component
@Log4j
public class IntefaceProcessTask implements TimerTask{
	@Autowired
	private List<Analystor> analystors;

	@Autowired
	private Executor executor;
	
	@Scheduled(cron = "0 38 8 * * ?")
	@Override
	public void work() {
		log.warn("###################定时启动分析网页数据开始#####################");
		for (Analystor analystor : analystors) {
			log.info("处理：" + analystor.getClass().getName());
			executor.execute(() -> analystor.analys());
		}
		log.warn("###################定时启动分析网页数据结束#####################");
		
	}
}
