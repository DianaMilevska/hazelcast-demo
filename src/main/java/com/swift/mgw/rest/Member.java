package com.swift.mgw.rest;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.RestApiConfig;
import com.hazelcast.config.RestEndpointGroup;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//@SpringBootApplication
public class Member {

    public static void main1(String[] args) throws Exception {
        Config config = new Config();
        RestApiConfig restApiConfig = new RestApiConfig()
                .setEnabled(true)
                .disableAllGroups()
                .enableGroups(RestEndpointGroup.DATA);
        config.getNetworkConfig().setRestApiConfig(restApiConfig);
//        config.setLicenseKey("prpcUtils to apply HFix-46681");

        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);

//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.getNetworkConfig().addAddress("192.168.1.5");
//
//        RestApiConfig restApiConfig = new RestApiConfig()
//                .setEnabled(true)
//                .disableAllGroups()
//                .enableGroups(RestEndpointGroup.DATA);
////        clientConfig.getNetworkConfig().setRestApiConfig(restApiConfig);
//
//        HazelcastInstance hz = HazelcastClient.newHazelcastClient(clientConfig);

        System.out.println("Application started");
        Scanner in = new Scanner(System.in);

        while (true) {
            int res = in.nextInt();

            int n = ThreadLocalRandom.current().nextInt(0, 3);
            if (res == 1) {
                System.out.println("Put simple value!");
                IMap<String, String> hzSimpleMap = hz.getMap("simple");
                hzSimpleMap.set("key" + n, "value" + n);

                System.out.println("Put object!");
                Person person = new Person("Joe" + n);
                IMap<String, Person> hzObjectMap = hz.getMap("object");
                hzObjectMap.set("key" + n, person);
            } else if (res == 0) {
                System.out.println("Exit!");
                break;
            }
        }

    }
}
