package com.mindai.cf.scheduler.center.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;

import lombok.Data;

@Data
public abstract class QuartzJobBean implements Job{
	
	/**
	 * 指定定时任务的名称
	 */
	private String jobName;
	
	/**
	 * 指定定时任务所属的组
	 */
	private String jobGroup;
	
	
	/**
	 * 定时任务的执行计划
	 */
	private String cronExpression;

	/**
	 * This implementation applies the passed-in job data map as bean property
	 * values, and delegates to {@code executeInternal} afterwards.
	 * @see #executeInternal
	 */
	@Override
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
			MutablePropertyValues pvs = new MutablePropertyValues();
			pvs.addPropertyValues(context.getScheduler().getContext());
			pvs.addPropertyValues(context.getMergedJobDataMap());
			bw.setPropertyValues(pvs, true);
		}
		catch (SchedulerException ex) {
			throw new JobExecutionException(ex);
		}
		executeInternal(context);
		
		
		
	}
	
	/**
	 * Execute the actual job. The job data map will already have been applied
	 * as bean property values by execute. The contract is exactly the same as
	 * for the standard Quartz execute method.
	 * 
	 * @see #execute
	 */
	protected abstract void executeInternal(JobExecutionContext context) throws JobExecutionException;

	/**
	 * 给出job对应的触发器
	 * @return
	 */
	public abstract String setCronExpression();
}
