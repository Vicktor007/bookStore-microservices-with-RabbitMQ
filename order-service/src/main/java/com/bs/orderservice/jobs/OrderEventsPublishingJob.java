package com.bs.orderservice.jobs;

import com.bs.orderservice.domain.OrderEventService;
import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderEventsPublishingJob {

    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    private final OrderEventService orderEventService;

    OrderEventsPublishingJob(OrderEventService orderEventService) {
        this.orderEventService = orderEventService;
    }

    @Scheduled(cron = "${orders.publish-order-events-job-cron}")
    @SchedulerLock(name = "publishOrderEvents")
    public void publishOrderEvents() {
        LockAssert.assertLocked();
        log.info("Publishing order events at {}", Instant.now());
        orderEventService.publishOrderEvents();
    }
}
