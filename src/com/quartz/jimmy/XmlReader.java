package com.quartz.jimmy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 任务配置xml解析类
 * 
 * @author jiaming.rjm
 * 
 */
@SuppressWarnings("unchecked")
public class XmlReader {
	
	private static Logger logger = Logger.getLogger(XmlReader.class);

	// 配置文件路径
	public final static String configFileName = File.separator
			+ "taskconfig.xml";

	public static List<TaskConfig> getTasks(String dir) {

		List<TaskConfig> tasks = new ArrayList<TaskConfig>();

		logger.info("load task config start...");

		File file = new File(dir + configFileName);

		if (file.exists() && !file.isDirectory()) {

			try {
				SAXBuilder sx = new SAXBuilder();
				Document doc = sx.build(file);
				Element rootelement = doc.getRootElement();

				List<Element> childs = rootelement.getChildren();
				for (int i = 0; i < childs.size(); i++) {
					TaskConfig taskConfig = new TaskConfig();

					logger.info("name:" + childs.get(i).getChildText("name"));
					logger.info("activity:" + childs.get(i).getChildText("activity"));
					logger.info("scanPeriod:" + childs.get(i).getChildText("scanPeriod"));
					logger.info("className:" + childs.get(i).getChildText("className"));

					taskConfig.setName(childs.get(i).getChildText("name"));

					if (childs.get(i).getChildText("activity").equals("true")) {
						taskConfig.setActivity(true);
					} else {
						taskConfig.setActivity(false);
					}
					taskConfig.setScanPeriod(childs.get(i).getChildText("scanPeriod"));
					taskConfig.setTaskClass(childs.get(i).getChildText("className"));
					tasks.add(taskConfig);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JDOMException e) {

				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			logger.info("task config file no exist!");
		}
		logger.info("load task config end !");
		return tasks;

	}

}