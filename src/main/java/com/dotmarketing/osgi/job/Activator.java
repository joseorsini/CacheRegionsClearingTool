package com.dotmarketing.osgi.job;

import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.CacheLocator;
import com.dotmarketing.osgi.GenericBundleActivator;
import com.dotmarketing.quartz.CronScheduledTask;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.MaintenanceUtil;

import org.osgi.framework.BundleContext;
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

        //Clear Host Cache
        //clearHostCache();

        // Job params
        Map<String, Object> params = new HashMap<String, Object>();
        params.put( "param1", "Host" );
        //params.put( "param2", "value2" );

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 60);

        String cron = new SimpleDateFormat("ss mm H d M ? yyyy").format(cal.getTime());

                //Creating our custom Quartz Job
        CronScheduledTask cronScheduledTask =
                new CronScheduledTask( JOB_NAME, JOB_GROUP, JOB_NAME, JOB_CLASS,
                        new Date(), null, CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW,
                        params, cron );

        //Schedule our custom job
        scheduleQuartzJob( cronScheduledTask );

    }
	
	
    private void clearHostCache() {
        try{

        	try{
        		CacheLocator.getCache("Host").clearCache();
        	}catch (NullPointerException e) {
        		MaintenanceUtil.flushCache();
        	}
        	APILocator.getPermissionAPI().resetAllPermissionReferences();
        }catch (Exception ex){
        	Logger.error(this,"There was a problem flushing the Host Cache");
        }
    }

    @Override
    public void stop ( BundleContext bundleContext ) throws Exception {
        unregisterServices(bundleContext);
    }
}
