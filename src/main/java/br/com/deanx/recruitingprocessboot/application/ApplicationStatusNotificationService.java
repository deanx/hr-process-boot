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

    public static final String APPLICATION_STATUS_CHANGE_EVENT = "applicationStatusChange";
    private static final int APPLICATION_STATUS_CHANGE_AWAIT_PERIOD = 100;

    public void notify(Application application) throws InterruptedException {
        eventBus.notify(APPLICATION_STATUS_CHANGE_EVENT, Event.wrap(application));
        latch.await(APPLICATION_STATUS_CHANGE_AWAIT_PERIOD, TimeUnit.MILLISECONDS);
    }
}
