package com.tofas.yky.config;
/* T40127 @ 20.10.2015. */

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.TfAsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class TfEventBusConfig {

    @Bean
    EventBus eventBus() {
        return new TfAsyncEventBus(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1));
    }

}
