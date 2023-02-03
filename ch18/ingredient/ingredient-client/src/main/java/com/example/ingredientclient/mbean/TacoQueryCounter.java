package com.example.ingredientclient.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

import javax.management.Notification;
import java.util.concurrent.atomic.AtomicLong;

@Component
@ManagedResource(objectName = "taco:name=TacoQueryCounter")
public class TacoQueryCounter implements NotificationPublisherAware {

    private final AtomicLong count;

    private NotificationPublisher notificationPublisher;

    public TacoQueryCounter() {
        this.count = new AtomicLong(0L);
    }

    @ManagedAttribute
    public long getCount() {
        return count.get();
    }

    @ManagedOperation
    public long increment(long delta) {
        long count = this.count.addAndGet(delta);

        if (count > 10) {
            Notification notification = new Notification("taco.count", this, count, "count");
            notificationPublisher.sendNotification(notification);
        }

        return count;
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }


}
