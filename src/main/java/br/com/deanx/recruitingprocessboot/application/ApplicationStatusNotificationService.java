package br.com.deanx.recruitingprocessboot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class ApplicationStatusNotificationService {
    @Autowired
    private CountDownLatch latch;

    @Autowired
    EventBus eventBus;

    public void notify(Application application) throws InterruptedException {
        eventBus.notify("applicationStatusChange", Event.wrap(application));
        latch.await(100, TimeUnit.MILLISECONDS);
    }
}
