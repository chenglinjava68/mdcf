package com.mindai.cf.scheduler.center.quartz.job;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.stereotype.Component;

import com.mindai.cf.scheduler.center.enums.RabbitQueues;
import com.mindai.cf.scheduler.center.rabbit.sender.Sender;

import lombok.extern.slf4j.Slf4j;

/**
 * 宝付代付交易状态查询
 * @author lilei 2016年11月22日
 */
@Component
@Slf4j
public class PayProxyQueryJob extends QuartzJobBean {

	@Resource(name ="sender")
	private Sender sender;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("PayProxyQueryJob定时调度开始-----------------------------------start");
		JobKey jobkey = context.getJobDetail().getKey();
		sender.sendMsg(RabbitQueues.SCHEDULER_PAYGATEWAY_WITHHOLDING.getCode(), jobkey.getName());
		log.info("PayProxyQueryJob定时调度结束-----------------------------------end");
	}

	@Override
	public String setCronExpression() {
		return "0 */30 * ? * *";
	}

}
