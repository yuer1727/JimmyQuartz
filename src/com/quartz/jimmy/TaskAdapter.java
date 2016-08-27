package com.quartz.jimmy;

import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;

public class TaskAdapter implements StatefulJob, InterruptableJob {
	
	private static Logger logger = Logger.getLogger(TaskAdapter.class);
	
	public TaskAdapter() {}

	private Task task = null;

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		Object taskObj = context.getJobDetail().getJobDataMap().get(
				Constants.TASK_JOB_INSTANTS);
		if (taskObj instanceof Task) {
			task = (Task) taskObj;
			task.execute();
		} else {
			logger.info("未知的job类型:" + taskObj.getClass());
		}
	}


	public void interrupt() throws UnableToInterruptJobException {
		task.interrupt();
	}
}