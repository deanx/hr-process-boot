package br.com.deanx.recruitingprocessboot;

import br.com.deanx.recruitingprocessboot.application.ApplicationStatusChangeService;
import br.com.deanx.recruitingprocessboot.application.ApplicationStatusNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;

import static reactor.bus.selector.Selectors.$;

@SpringBootApplication
public class RecruitingProcessBootApplication {

    @Bean
    public reactor.Environment env() {
        return reactor.Environment.initializeIfEmpty().assignErrorJournal();
    }

    @Bean
    EventBus createEventBus(reactor.Environment env) {
        EventBus eventBus = EventBus.create(env, reactor.Environment.THREAD_POOL);
        eventBus.on($(ApplicationStatusNotificationService.APPLICATION_STATUS_CHANGE_EVENT), receiver);
        return eventBus;
    }

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    @Autowired
    private ApplicationStatusChangeService receiver;

    @Autowired
    EventBus eventBus;

	public static void main(String[] args) {
	    SpringApplication.run(RecruitingProcessBootApplication.class, args);
	}
}
