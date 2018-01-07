package br.com.deanx.recruitingprocessboot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;

import java.util.concurrent.CountDownLatch;

@Service
public class ApplicationStatusChangeService implements Consumer<Event<Application>> {
    @Autowired
    CountDownLatch latch;

    @Override
    public void accept(Event<Application> ev) {
        Application application = (Application) ev.getData();
        System.out.println("Event generated to change application for " + application.getOffer().getJobTitle() + " to:  " + application.getApplicationStatus());
        latch.countDown();
    }
}
