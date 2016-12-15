package com.mindai.cf.scheduler.center.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mindai.cf.scheduler.center.domain.model.BaseModel;
import com.mindai.cf.scheduler.center.enums.RocketQueues;
import com.mindai.cf.scheduler.center.rocketmq.producer.RocketProducerService;
import com.mindai.common.annotation.QuartzJobTask;

import lombok.extern.slf4j.Slf4j;
/**
 * 宝付代扣支付状态查询
 * create by lilei 2016-11-18
 * 
 * */
@QuartzJobTask
@Slf4j
public class WithholdingQueryJob  extends QuartzJobBean{
	
	@Autowired
	RocketProducerService senderService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		JobKey jobkey = context.getJobDetail().getKey();
		log.info("WithholdingQueryJob定时调度开始-----------------------------------start//"+jobkey.getName());
		BaseModel<?> baseModel = new BaseModel<>();
		baseModel.setCode(RocketQueues.SCHEDULER_PAYGATEWAY_WITHHOLDING.getTag());
		baseModel.setMsg(RocketQueues.SCHEDULER_PAYGATEWAY_WITHHOLDING.getDesc());
		senderService.oneWaySender(RocketQueues.SCHEDULER_PAYGATEWAY_WITHHOLDING, JSON.toJSONString(baseModel));
		log.info("WithholdingQueryJob定时调度结束-----------------------------------end");
	
	}

	@Override
	public String setCronExpression() {
		return "0 */1 * ? * *";
	}
	
}
