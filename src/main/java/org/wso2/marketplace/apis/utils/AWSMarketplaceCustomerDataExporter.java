package org.wso2.marketplace.apis.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AWSMarketplaceCustomerDataExporter {
    private static final Log logger = LogFactory.getLog(AWSMarketplaceCustomerDataExporter.class);

    private final ScheduledExecutorService scheduler;

    public AWSMarketplaceCustomerDataExporter() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }


}
