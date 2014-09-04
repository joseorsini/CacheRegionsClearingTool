package com.dotmarketing.osgi.viewtools;

import com.dotmarketing.osgi.GenericBundleActivator;
import org.osgi.framework.BundleContext;

import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.CacheLocator;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.MaintenanceUtil;

public class Activator extends GenericBundleActivator {

    @Override
    public void start ( BundleContext bundleContext ) throws Exception {

        //Initializing services...
        initializeServices( bundleContext );

        //Clear Host Cache
        clearHostCache();
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
        unregisterViewToolServices();
    }

}
