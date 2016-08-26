package com.dotmarketing.osgi.job;

import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.CacheLocator;
import com.dotmarketing.osgi.GenericBundleActivator;
import com.dotmarketing.quartz.CronScheduledTask;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.MaintenanceUtil;

import com.dotcms.repackage.org.osgi.framework.BundleContext;
import org.quartz.CronTrigger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Activator extends GenericBundleActivator {

    public final static String JOB_NAME = "Cache Regions Clearing Job";
    public final static String JOB_CLASS = "com.dotmarketing.osgi.job.CacheRegionsClearingJob";
    public final static String JOB_GROUP = "User Jobs";
    
    @Override
    public void start ( BundleContext bundleContext ) throws Exception {

        //Initializing services...
        initializeServices( bundleContext );

        //Set Cache Regions. Key Values must always start with "param" and end with consecutive numbers.
        //See plugin's README.md for getting a whole list of Cache Regions names.

        Map<String, Object> params = new HashMap<String, Object>();
        params.put( "param1", "default" );
        //params.put( "param2", "Block Directive");
        //params.put( "param3", "Page" );
        

        //Set Job's execution time for 15 seconds after the plugin is deployed. You can set your own cron expression.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 15);

        String cron = new SimpleDateFormat("ss mm H d M ? yyyy").format(cal.getTime());

        //Set your own Cron Expression. Comment the code above before setting this custom cronexp
        //String cron = "0 0 0 1/1 * ? *";//Every day at 00:00 server time

        //Creating our custom Quartz Job
        CronScheduledTask cronScheduledTask =
        new CronScheduledTask( JOB_NAME, JOB_GROUP, JOB_NAME, JOB_CLASS,
            new Date(), null, CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW,
            params, cron );

        //Schedule our custom job
        scheduleQuartzJob( cronScheduledTask );

    }

    @Override
    public void stop ( BundleContext bundleContext ) throws Exception {
        unregisterServices(bundleContext);
    }
}
