//package com.swift.mgw.rest;
//
//import com.hazelcast.client.HazelcastClient;
//import com.hazelcast.client.config.ClientConfig;
//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.HazelcastInstanceNotActiveException;
//import com.hazelcast.map.IMap;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.io.IOException;
//import java.util.Scanner;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ThreadLocalRandom;
//
//@SpringBootApplication
//public class Application {
//
//    public static void main(String[] args) throws InterruptedException, IOException {
//        SpringApplication app = new SpringApplication(Application.class);
//
//        //app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
//        SpringApplication.run(Application.class, args);
//
//        System.out.println("Application started");
//        Scanner in = new Scanner(System.in);
//
//        while (true) {
//            int res = in.nextInt();
//            if (res == 1) {
//                System.out.println("Put token!");
//                putTokenInContainer();
//            } else if (res == 2) {
//                System.out.println("Get token!");
//                getTokenFromContainer();
//            } else if (res == 0) {
//                System.out.println("Exit!");
//                break;
//            }
//        }
//
//    }
//
//}