package com.quartz.jimmy;



import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;


/**
 * 调度任务服务程序启动入口
 * author jiaming.rjm
 * 
 */
public class QuartzHelper
{
	private static   Logger logger = Logger.getLogger(QuartzHelper.class);
	
	private static Scheduler server = null;
	private static int JobIdCount=0;
	
	/**
	 * 初始化调度器
	 */
	public static void initSchedulerServer()
		throws SchedulerException
	{
		if(server == null)
		{
			SchedulerFactory sf = new StdSchedulerFactory();
			server = sf.getScheduler();
			JobIdCount=0;
		}
	}
	
	/**
	 * 调度一个任务
	 */
	public static void scheduleJob(TaskConfig conf) 
		throws SchedulerException, ParseException
	{
		JobIdCount++;
		
		//定时任务的处理逻辑对象
		Task deliveryJob = TaskFactory.createTask(conf);
		
		//job的名称
		String jobName = conf.getName()+"_JobId_"+JobIdCount;
		String groupName = "Settlement";
		
		//创建任务job
		JobDetail jobAdapter = newJob(TaskAdapter.class)
				.withIdentity(jobName, groupName)
			    .build();
		//附带相关job数据
		jobAdapter.getJobDataMap().put(Constants.TASK_JOB_INSTANTS, deliveryJob);
		jobAdapter.getJobDataMap().put(Constants.JOBNAME, jobName);
		jobAdapter.getJobDataMap().put(Constants.GROUPNAME, groupName);

		//注意一个trigger和一个job绑定
        Trigger trigger =null;
		try {
			trigger = createCronTrigger(jobName, groupName, conf.getScanPeriod());
		} catch (Exception e) {
			logger.error("createCronTrigger error", e);
		}finally{
			if(trigger==null){
				logger.error("trigger conf is error!");
				return;
			}
		}		
		
		//server.addJob(jobAdapter, true);用此方法时，JobDetail必须为durable
		
		//注册job和trigger
		server.scheduleJob(jobAdapter, trigger);
		//启动
		server.start();
	}
	
	/**
	 * 启动调度服务
	 * @throws SchedulerException
	 */
	public static void startScheduleServer() throws SchedulerException{
		if(server != null)
			server.start();
	}
	
	/**
	 * 关闭调度服务
	 * @throws SchedulerException
	 */
	public static void stopScheduleServer() throws SchedulerException{
		if(server != null)
			server.shutdown();
	}
	
	/**
	 * 创建一个SimpleTrigger
	 */
	public static SimpleTrigger createSimpleTrigger(String jobName,
			String groupName, int interval) throws InvalidAttributesException{
		if(interval<=0){
			throw new InvalidAttributesException();
		}
		SimpleTrigger trigger = newTrigger()
				.withIdentity(jobName, groupName)
				.forJob(jobName, groupName)
				.withSchedule(simpleSchedule().withIntervalInSeconds(interval).repeatForever())
				.build();
		return trigger;
	}
	
	/**
	 * 创建一个CronTrigger
	 */
	public static CronTrigger createCronTrigger(String jobName,
			String groupName, String cronExpression) throws InvalidAttributesException{
		if(cronExpression==null||cronExpression.equals("")){
			throw new InvalidAttributesException();
		}
		CronTrigger trigger = newTrigger()
				.withIdentity(jobName, groupName)
				.forJob(jobName, groupName)
				.withSchedule(cronSchedule(cronExpression))
				.build();
		return trigger;
	}
}
