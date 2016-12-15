package com.mindai.cf.scheduler.center.rocketmq.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.mindai.cf.scheduler.center.enums.RocketQueues;
import com.mindai.cf.scheduler.center.rocketmq.consumer.DefaultConsumer;
import com.mindai.cf.scheduler.center.util.EnumsUtil;
import com.mindai.common.annotation.RocketMqListener;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RocketConsumerConfig {
	
	@Autowired
	public ApplicationContext context;
	
	@Bean(name = "consumerProperties")
	public Properties consumerProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/rocketmq.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public Map<Subscription, MessageListener> getSubscriptionTable() throws Exception {

		Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();
		Map<String, Object> beansWithAnnotationMap = context.getBeansWithAnnotation(RocketMqListener.class);
		for (Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()) {
			Class<? extends Object> classIns = entry.getValue().getClass();// 获取到实例对象的class信息
			log.info("-------add MessageListener ----start-----------" + classIns.getSimpleName());
			RocketQueues rocketQueue = EnumsUtil.getRocketQueues(classIns.getSimpleName());
			if (rocketQueue == null)continue;
			Subscription subscription = new Subscription();
			subscription.setTopic(rocketQueue.getTopic());
			subscription.setExpression("*");
			MessageListener listener = (MessageListener) context.getBean(classIns);
			subscriptionTable.put(subscription, listener);
			log.info("-------add MessageListener -----end----------" + subscriptionTable.toString());

		}
		return subscriptionTable;
	}
	
	@Bean
	public Consumer createConsumer(@Qualifier("consumerProperties") Properties properties) throws Exception {
		DefaultConsumer consumer = new DefaultConsumer();
		consumer.setProperties(properties);
		consumer.setSubscriptionTable(getSubscriptionTable());
		consumer.start();
		return consumer;
	}
}
