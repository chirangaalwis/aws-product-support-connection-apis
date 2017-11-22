package org.wso2.marketplace.apis.utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.marketplacecommerceanalytics.AWSMarketplaceCommerceAnalyticsClient;
import com.amazonaws.services.marketplacecommerceanalytics.model.GenerateDataSetRequest;
import com.amazonaws.services.marketplacecommerceanalytics.model.GenerateDataSetResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

public class AWSMarketplaceUtils {
    private static final Logger logger = Logger.getLogger(AWSMarketplaceUtils.class.getName());

    public static void exportCustomerData() throws ParseException {
        AWSCredentials credentials;

        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception exception) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles " +
                    "file (~/.aws/credentials), please make sure that your credentials file is at the correct " +
                    "location, and is in valid format", exception);
        }

        AWSMarketplaceCommerceAnalyticsClient client = new AWSMarketplaceCommerceAnalyticsClient(credentials);
        Region usEast2 = Region.getRegion(Regions.US_EAST_2);
        client.setRegion(usEast2);

        // Create a data set request with the desired parameters
        GenerateDataSetRequest request = new GenerateDataSetRequest();

        request.setDataSetType("test_customer_support_contacts_data");
        request.setDataSetPublicationDate(convertISO8601StringToDateUTC("2017-10-20T00:00:00Z"));
        request.setRoleNameArn("arn:aws:iam::516727968244:role/MarketplaceCommerceAnalyticsRole");
        request.setDestinationS3BucketName("wso2-aws-mp-customers");
        request.setDestinationS3Prefix("test-prefix");
        request.setSnsTopicArn("arn:aws:sns:us-east-2:516727968244:wso2-aws-mp-customers");

        logger.info(String.format("Creating a request for data set %s for publication date %s",
                request.getDataSetType(), request.getDataSetPublicationDate()));

        try {
            // Make the request to the service
            GenerateDataSetResult result = client.generateDataSet(request);
            // The Data Set Request ID is a unique identifier that you can use to correlate the
            // request with responses on your SNS Topic
            logger.info("Request successful, unique ID: " + result.getDataSetRequestId());
        } catch (AmazonServiceException exception) {
            String warning = "The request received by the AWS Marketplace Commerce Analytics service was rejected " +
                    "with an error response";
            logger.warning(warning);
        } catch (AmazonClientException exception) {
            String warning = "An internal error occurred when accessing Marketplace Commerce Analytics service: " +
                    exception.getMessage();
            logger.warning(warning);
        }
    }

    private static Date convertISO8601StringToDateUTC(String dateString) throws ParseException {
        TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
        DateFormat utcDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        utcDateFormat.setTimeZone(utcTimeZone);
        return utcDateFormat.parse(dateString);
    }
}
