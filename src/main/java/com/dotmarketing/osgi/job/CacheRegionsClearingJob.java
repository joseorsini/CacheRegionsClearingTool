package com.dotmarketing.osgi.job;

import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.CacheLocator;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.UtilMethods;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dotmarketing.util.MaintenanceUtil;

public class CacheRegionsClearingJob implements Job {

    private String CACHE_REGIONS_PARAM_PREFIX = "param";

    @Override
    public void execute ( JobExecutionContext context ) throws JobExecutionException {

    Logger.info( this, "------------------------------------------" );
    Logger.info( this, "Start CacheRegionsClearing job" );
    Logger.info( this, "" );

    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

    int dataMapSize = jobDataMap.size();

    try{
        for(int i=0;i < dataMapSize;i++){
          int j = i + 1;
          String cacheRegion = jobDataMap.get("param" + j).toString();
          if(UtilMethods.isSet(cacheRegion)){
                try{
                    Logger.info( this, "Clearing " + cacheRegion + " Cache Region...");
                    CacheLocator.getCache(cacheRegion).clearCache();
                } catch (NullPointerException e) {
                    Logger.info( this, "Flushing All Caches...");
                    MaintenanceUtil.flushCache();
                }
            }
        }
        APILocator.getPermissionAPI().resetAllPermissionReferences();
    }catch (Exception ex){
        Logger.error(this,"There was a problem flushing Cache Regions: " + ex.getMessage() );
    }

    Logger.info( this, "" );
    Logger.info( this, "Finish CacheRegionsClearing Job" );
    Logger.info( this, "------------------------------------------" );
    }

}
