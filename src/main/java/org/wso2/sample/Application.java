package org.wso2.sample;

import org.wso2.msf4j.MicroservicesRunner;

public class Application {
    public static void main(String[] args) {
        new MicroservicesRunner()
                .deploy(new HelloWorldService())
                .start();
    }
}
