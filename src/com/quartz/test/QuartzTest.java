package com.quartz.test;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

    public static void main(String[] args) {

        try {
            // Grab the Scheduler instance from the Factory 
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();
            
         // define the job and tie it to our HelloJob class
            JobDetail job = newJob(HelloJob.class)
              .withIdentity("myJob", "group1") // name "myJob", group "group1"
              .build();

            // Trigger the job to run now, and then every 40 seconds
            Trigger trigger = newTrigger()
              .withIdentity("myTrigger", "group1")
              .startNow()
              .withSchedule(simpleSchedule()
                  .withIntervalInSeconds(40)
                  .repeatForever())            
              .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

          //  scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}