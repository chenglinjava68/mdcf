package com.mindai.cf.scheduler.center.rocketmq.consumer;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PreDestroy;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.ons.api.bean.SubscriptionExt;
import com.aliyun.openservices.ons.api.exception.ONSClientException;

public class DefaultConsumer implements Consumer{

    private Properties properties;
    private Map<Subscription, MessageListener> subscriptionTable;

    private Consumer consumer;
    
    @Override
    public void start() {
        if (null == this.properties) {
            throw new ONSClientException("properties not set");
        }

        if (null == this.subscriptionTable) {
            throw new ONSClientException("subscriptionTable not set");
        }

        this.consumer = ONSFactory.createConsumer(this.properties);

        Iterator<Entry<Subscription, MessageListener>> it = this.subscriptionTable.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Subscription, MessageListener> next = it.next();
            if (this.consumer.getClass().getCanonicalName()
                    .equals("com.aliyun.openservices.ons.api.impl.notify.ConsumerImpl")
                    && (next.getKey() instanceof SubscriptionExt)) {
                SubscriptionExt subscription = (SubscriptionExt) next.getKey();
                for (Method method : this.consumer.getClass().getMethods()) {
                    if ("subscribeNotify".equals(method.getName())) {
                        try {
                            method.invoke(consumer, subscription.getTopic(), subscription.getExpression(),
                                    subscription.isPersistence(), next.getValue());
                        } catch (Exception e) {
                            throw new ONSClientException("subscribeNotify invoke exception", e);
                        }
                        break;
                    }
                }

            } else {
                this.subscribe(next.getKey().getTopic(), next.getKey().getExpression(), next.getValue());
            }

        }

        this.consumer.start();
    }

    @PreDestroy
    @Override
    public void shutdown() {
        if (this.consumer != null) {
            this.consumer.shutdown();
        }
    }


    @Override
    public void subscribe(String topic, String subExpression, MessageListener listener) {
        if (null == this.consumer) {
            throw new ONSClientException("subscribe must be called after consumerBean started");
        }
        this.consumer.subscribe(topic, subExpression, listener);
    }

    @Override
    public void unsubscribe(String topic) {
        if (null == this.consumer) {
            throw new ONSClientException("unsubscribe must be called after consumerBean started");
        }
        this.consumer.unsubscribe(topic);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Map<Subscription, MessageListener> getSubscriptionTable() {
        return subscriptionTable;
    }

    public void setSubscriptionTable(Map<Subscription, MessageListener> subscriptionTable) {
        this.subscriptionTable = subscriptionTable;
    }

    @Override
    public boolean isStarted() {
        return this.consumer.isStarted();
    }

    @Override
    public boolean isClosed() {
        return this.consumer.isClosed();
    }
}
