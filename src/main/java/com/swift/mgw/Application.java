package com.swift.mgw;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceNotActiveException;
import com.hazelcast.map.IMap;
import com.swift.mgw.tcp.Message;
import com.swift.mgw.tcp.Token;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException, IOException {
        SpringApplication app = new SpringApplication(Application.class);

        //app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        SpringApplication.run(Application.class, args);

        System.out.println("Application started");
        Scanner in = new Scanner(System.in);

        while (true) {
            int res = in.nextInt();
            if (res == 1) {
                System.out.println("Put token!");
                putTokenInContainer();
            } else if (res == 2) {
                System.out.println("Get token!");
                getTokenFromContainer();
            } else if (res == 0) {
                System.out.println("Exit!");
                break;
            }
        }

    }

    private static void putTokenInContainer() throws InterruptedException {
        // Start the Hazelcast Client and connect to an already running Hazelcast Cluster on 127.0.0.1
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("192.168.1.5");

        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        int n = ThreadLocalRandom.current().nextInt(1, 5);

        System.out.println("+++++ Put message to queue with random number: " + n + " +++++");
        BlockingQueue<Message> queue = client.getQueue("mgw-requests-queue");
        Message message = new Message("uetr" + n, "MT10" + n);
        queue.put(message);

        System.out.println("+++++ Put token to map with random number: " + n + " +++++");
        IMap map = client.getMap("mgw-token-map");
        map.put("token" + n, new Token("key" + n, message));
//        map.put("token1", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
//        map.putIfAbsent("token2", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjMiLCJuYW1lIjoiU2ltIFBzb24iLCJpYXQiOjE1MTYyMzkwMjJ9.NfNyt9jKu6-x-NsRHfFcmXwIeU2sFQ2YxG1LzA_FOdI");

        System.out.println("Messages sent by Hazelcast Client!");
        client.shutdown();
    }

    private static void getTokenFromContainer() throws InterruptedException {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress("192.168.1.5");
        HazelcastInstance hz = HazelcastClient.newHazelcastClient(clientConfig);

        BlockingQueue<Message> queue = hz.getQueue("mgw-requests-queue");
        try {
            if (queue.isEmpty()) {
                System.out.println("===== Message queue is empty! =====");
            } else {
                Message message = queue.poll();
                System.out.println("===== The next message in queue: =====");
                System.out.println(message);
            }
        } catch (HazelcastInstanceNotActiveException e) {
            System.err.println("Unable to take from the queue. Hazelcast Member is probably going down!");
        }

        System.out.println("===== All tokens from map: =====");
        IMap map = hz.getMap("mgw-token-map");
        try {
            map.entrySet().stream().forEach(System.out::println);
        } catch (HazelcastInstanceNotActiveException e) {
            System.err.println("Unable to take from the queue. Hazelcast Member is probably going down!");
        }
    }

}