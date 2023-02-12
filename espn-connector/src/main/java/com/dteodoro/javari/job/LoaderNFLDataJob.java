package com.dteodoro.javari.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.dteodoro.javari.domain.ScheduleLoader;
import com.dteodoro.javari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class LoaderNFLDataJob {
	
	private final ScheduleLoader scheduleLoader;
	private final UserRepository repo;

	private static final String EVERY_DAY_6AM = "0 0 6 1/1 * *";
	private static final String EVERY_MINUTE = "0 0/1 * 1/1 * *";

	//@Scheduled(cron = EVERY_MINUTE) 
	public void loadNFLSchedules() {
		//scheduleLoader.load();
//		System.out.println("chamou o job");
//		Bettor bettor = new Bettor();
//		bettor.setUsername("dario.teodoro@live.com");
//		bettor.setNickName("Dário");
//		bettor.setPassword("123456");
//		bettor.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
//				
//		Bettor save = repo.save(bettor);
//		System.out.println(save);
	}

}
