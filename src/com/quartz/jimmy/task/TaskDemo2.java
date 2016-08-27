package com.quartz.jimmy.task;

import java.util.Date;

import org.apache.log4j.Logger;

import com.quartz.jimmy.Task;


public class TaskDemo2 implements Task {

	private static Logger logger = Logger.getLogger(TaskDemo2.class);
	
	//唯一构造函数
	public TaskDemo2()
	{
		
		
	}

	@Override
	public void execute() {
		logger.info(" TaskDemo2 execute："+new Date().toString());
	}

	@Override
	public void interrupt() {
		logger.info("TaskDemo2 interrupt");
	}

}
