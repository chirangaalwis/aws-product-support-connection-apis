package org.wso2.marketplace.apis;

import java.util.Date;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.model.PublishResult;

// Example SNS Sender
public class AmazonSNSSender {

    // AWS credentials -- replace with your credentials
    private static String ACCESS_KEY = "<XXXXXXXX>";
    private static String SECRET_KEY = "<XXXXXXXXXXXX>";

    // Sender loop
    public static void main(String... args) throws Exception {

        // Create a client
        AmazonSNSClient service = new AmazonSNSClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));

        Region usEast2 = Region.getRegion(Regions.US_EAST_2);
        service.setRegion(usEast2);

        // Create a topic
        CreateTopicRequest createReq = new CreateTopicRequest()
                .withName("wso2-aws-mp-customers");
        CreateTopicResult createRes = service.createTopic(createReq);

        for (; ; ) {

            String message = "Example notification sent at " + new Date();

            // Publish to a topic
            PublishRequest publishReq = new PublishRequest()
                    .withTopicArn(createRes.getTopicArn())
                    .withMessage(message);
            PublishResult publishResult = service.publish(publishReq);

            System.out.println(publishResult.toString());

            Thread.sleep(1000);
        }
    }
}
