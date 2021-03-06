package com.mindai.cf.scheduler.center.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mindai.cf.scheduler.center.domain.model.BaseModel;
import com.mindai.cf.scheduler.center.enums.RocketQueues;
import com.mindai.cf.scheduler.center.rocketmq.producer.RocketProducerService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
/**
 * 发送业务中心自动还款业务调度
 * */
public class AutoPaymentJob extends QuartzJobBean{

	@Autowired
	RocketProducerService senderService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		JobKey jobkey = context.getJobDetail().getKey();
		log.info("AutoPaymentJob定时调度开始-----------------------------------start://"+jobkey.getName());
		BaseModel<?> baseModel = new BaseModel<>();
		baseModel.setCode(RocketQueues.SCHEDULER_BIZ_AUTOPAY.getTag());
		baseModel.setMsg(RocketQueues.SCHEDULER_BIZ_AUTOPAY.getDesc());
		senderService.oneWaySender(RocketQueues.SCHEDULER_BIZ_AUTOPAY, JSON.toJSONString(baseModel));
		log.info("AutoPaymentJob定时调度结束-----------------------------------end");
	}

	@Override
	public String setCronExpression() {
		return "0 */30 * ? * *";
	}
}
