package com.mindai.cf.scheduler.center.domain.model;

import lombok.Data;

@Data
public class BaseModel<T> {
	private String code;
	private String msg;
	private T data;
}
