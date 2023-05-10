package com.dteodoro.javari.job;

import com.dteodoro.javari.service.LoaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class LoaderNFLDataJob {
	
	private final LoaderService loaderService;
	private static final String EVERY_DAY_6AM = "0 0 6 1/1 * *";
	private static final String EVERY_MINUTE = "0 0/1 * 1/1 * *";

	@Scheduled(cron = EVERY_DAY_6AM)
	public void loadNFLSchedules() {
		log.info("Loader Schedule JOB: Starting load data...");
		try {
			//loaderService.loadSchedules();
		}catch (Exception e){
			log.error("Loader Schedule JOB: Cannot load schedule data",e.getCause());
		}
		log.info("Load Schedule Job has finished");
	}

}
