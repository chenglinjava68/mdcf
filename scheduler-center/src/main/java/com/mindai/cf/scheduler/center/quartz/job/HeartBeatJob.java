package com.mindai.cf.scheduler.center.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Trigger;

import com.mindai.common.annotation.QuartzJobTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统心跳Job
 * @author lilei 2016年11月30日
 *
 */
@QuartzJobTask
@Slf4j
public class HeartBeatJob extends QuartzJobBean{
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobKey jobkey = context.getJobDetail().getKey();
		Trigger trigger = context.getTrigger();
		log.info(trigger.getCalendarName()+"------系统调度中心心跳Job---------" + jobkey.getGroup() + "/"+ jobkey.getName());
	}

	@Override
	public String setCronExpression() {
		return "0 */5 * ? * *";
	}
}