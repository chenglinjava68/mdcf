package com.mindai.cf.scheduler.center.quartz.config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.mindai.cf.scheduler.center.enums.JobGroup;
import com.mindai.cf.scheduler.center.quartz.job.QuartzJobBean;
import com.mindai.cf.scheduler.center.util.ClassUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置任务调度中心 [QRTZ_JOB_DETAILS], [QRTZ_TRIGGERS] and [QRTZ_CRON_TRIGGERS]
 * @author lilei 2016年11月30日
 */
@Configuration
@EnableScheduling
@ComponentScan("com.mindai.cf.scheduler.center.quartz.*")
@Slf4j
public class QuartzConfig {
	
	/**
	 * 加载quartz数据源配置,quartz集群时用到
	 * @throws IOException
	 */
	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		log.info("-------------------- DataSource init ---------------------");
		// return new com.alibaba.druid.pool.DruidDataSource();//此方式启动数据源会报错
		return DataSourceBuilder.create().build();
	}

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		QuartzJobBeanFactory jobFactory = new QuartzJobBeanFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	/**
	 * Scheduler(SchedulerFactoryBean)的配置需要Trigger这个参数.
	 * @param cronTriggerBean
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactory(JobFactory jobFactory) throws Exception {

		SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
		// 用于quartz集群,加载quartz数据源
		schedulerFactory.setDataSource(dataSource());
		schedulerFactory.setJobFactory(jobFactory);
		//schedulerFactory.setConfigLocation(new ClassPathResource("/quartz.properties"));
		// this allows to update triggers in DB when updating settings in config
		// file:
		// 用于quartz集群,QuartzScheduler
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		schedulerFactory.setOverwriteExistingJobs(true);
		//QuartzScheduler 延时启动，应用启动完20秒后 QuartzScheduler 再启动
		 schedulerFactory.setStartupDelay(5);
		// 用于quartz集群,加载quartz数据源配置
		schedulerFactory.setQuartzProperties(quartzProperties());
		// 注册触发器
//		schedulerFactory.setTriggers(getAllCronTrigger());
		return schedulerFactory;
	}
	@SuppressWarnings("unchecked")
	@Bean
	public Scheduler scheduler(SchedulerFactoryBean schedulerFactory) throws Exception {
		
		Scheduler scheduler = schedulerFactory.getScheduler();
		for (Class<?> classIns : ClassUtil.getAllAssignedClass(QuartzJobBean.class)) {
			
			Method method = classIns.getMethod("setCronExpression");
			String cronExpression = (String) method.invoke(classIns.newInstance());
			JobKey jobkey = new JobKey(classIns.getSimpleName(), JobGroup.TRANS_CENTER.getCode());
			JobBuilder jobBuilder =  newJob((Class<? extends Job>) classIns);
			jobBuilder.withIdentity(jobkey);
			JobDetail job = jobBuilder.build();
			
			TriggerKey triggerKey = new TriggerKey("trigger_"+classIns.getSimpleName(), JobGroup.TRANS_CENTER.getCode());
			Trigger trigger = scheduler.getTrigger(triggerKey);
			if(trigger!=null&&trigger.getJobKey().equals(jobkey)){
				scheduler.deleteJob(jobkey);
				scheduler.unscheduleJob(triggerKey);
			}
			TriggerBuilder<Trigger> triggerBuilder = newTrigger();
			triggerBuilder.withIdentity(triggerKey);
			triggerBuilder.withSchedule(cronSchedule(cronExpression));
			trigger = triggerBuilder.build();
		    scheduler.scheduleJob(job, trigger);
		    log.info(job.getKey() + " will run at: " + cronExpression);
		}
		return scheduler;
	}
}