package br.com.nlw.events.controller;

import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    @PostMapping("/subscription/{prettyName}")
    public ResponseEntity<Subscription> createSusbcription(@PathVariable String prettyName, @RequestBody User subscriber){
        Subscription res = service.createNewSubscription(prettyName, subscriber);
    }
}
