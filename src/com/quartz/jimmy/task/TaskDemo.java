package com.quartz.jimmy.task;

import java.util.Date;

import org.apache.log4j.Logger;

import com.quartz.jimmy.Task;


public class TaskDemo implements Task {

	private static Logger logger = Logger.getLogger(TaskDemo.class);
	
	//唯一构造函数
	public TaskDemo()
	{
		
		
	}

	@Override
	public void execute() {
		logger.info(" TaskDemo execute："+new Date().toString());
	}

	@Override
	public void interrupt() {
		logger.info("TaskDemo interrupt");
	}

}
