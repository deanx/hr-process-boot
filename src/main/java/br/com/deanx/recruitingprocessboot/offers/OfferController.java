package br.com.deanx.recruitingprocessboot.offers;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class OfferController {
    @RequestMapping(value = "/offers", method = RequestMethod.GET)
    String listOrders() {
        return "";
    }
}
