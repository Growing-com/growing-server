package org.sarangchurch.growing.config;

import lombok.RequiredArgsConstructor;
import org.sarangchurch.growing.core.interfaces.common.Events;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@RequiredArgsConstructor
public class EventsConfig implements InitializingBean {

    private final ApplicationContext publisher;

    @Override
    public void afterPropertiesSet() throws Exception {
        Events.setPublisher(publisher);
    }
}
