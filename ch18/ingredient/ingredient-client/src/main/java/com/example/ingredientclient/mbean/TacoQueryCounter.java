package com.example.ingredientclient.mbean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@ManagedResource(objectName = "taco:name=TacoQueryCounter")
public class TacoQueryCounter {
    private final AtomicLong count;

    public TacoQueryCounter() {
        this.count = new AtomicLong(0L);
    }

    @ManagedAttribute
    public long getCount() {
        return count.get();
    }

    @ManagedOperation
    public long increment(long delta) {
        return count.addAndGet(delta);
    }
}
