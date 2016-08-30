package com.quartz.jimmy;

/**
 * 任务工厂类
 * @author jiaming.rjm
 *
 */
public class TaskFactory {

	 /**
	  * 根据任务配置对象，创建任务类的对象实例，采用反射。
	  * @param config
	  * @return
	  */
	 public static Task createTask(TaskConfig config) {
		String classname = config.getClassName();
		Task task = null;
		try {
			task = (Task) Class.forName(classname).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return task;
	}

}