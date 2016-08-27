package com.quartz.test;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements org.quartz.Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

       System.err.println(Integer.toHexString(31268));
	}
	
	public static void main(String[] args) {
		 System.err.println(Integer.toHexString(31268));
	}
	
	 

}
