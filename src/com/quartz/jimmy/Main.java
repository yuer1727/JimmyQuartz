package com.quartz.jimmy;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;


public class Main {
	
	private static Logger logger = Logger.getLogger(Main.class);

	private static String appdir = System.getProperty("user.dir");
	private static String APPLICATION_ROOT_DIR = appdir + File.separator+"src";
	
	public static void main(String[] args) throws Exception {
		try {
			
			logger.info(appdir + "," + APPLICATION_ROOT_DIR);

			// 初始化调度服务器
			QuartzHelper.initSchedulerServer();

			List<TaskConfig> tasks = XmlReader.getTasks(APPLICATION_ROOT_DIR);

			if (tasks == null || tasks.size() <= 0){
				logger.error("没有配置任务实例!");
				throw new Exception("没有配置任务实例!");
			}

			for (int i = 0; i < tasks.size(); i++) {
				TaskConfig tc = tasks.get(i);
				try {
					if (tc.isActivity()) {
						QuartzHelper.scheduleJob(tc);
						logger.info("任务实例[" + tc.getName() + "]已加入调度.");
					} else {
						logger.info(tc.getName() + " 任务实例Activity=false 不进行处理");
					}
				} catch (Exception cve) {
					// 配置错误忽略这个任务
					logger.error("任务启动失败：%d"+tc.getClassName(), cve);
				}
			}
			/* 启动调度服务器 */
			QuartzHelper.startScheduleServer();
		} catch (Exception ex) {

			logger.error("启动出现异常,3秒钟后自动关闭");
			Thread.sleep(1000 * 3);
            
			QuartzHelper.stopScheduleServer();
		}

	}

}
