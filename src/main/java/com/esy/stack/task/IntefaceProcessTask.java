package com.esy.stack.task;

import com.esy.stack.download.Analystor;
import com.esy.stack.download.BaseAnalysis;
import com.esy.stack.download.TimerTask;
import com.esy.stack.util.StatusEnum;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Component
@Log4j
public class IntefaceProcessTask implements TimerTask, DisposableBean{
	@Autowired
	private List<Analystor> analystors;

	@Autowired
	private ExecutorService executor;
	
	@Scheduled(cron = "0 0/30 * * * ?")
	@Override
	public void work() {
		log.warn("###################定时启动分析网页数据开始#####################");
		for (Analystor analystor : analystors) {
			log.info("处理：" + analystor.getClass().getName());
			executor.execute(() -> analystor.analys());
		}
		log.warn("###################定时启动分析网页数据结束#####################");
	}

	@Override
	public void destroy() throws Exception {
		executor.shutdown();
	}
}
