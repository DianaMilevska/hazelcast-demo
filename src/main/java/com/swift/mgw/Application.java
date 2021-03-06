package com.swift.mgw;

import com.swift.mgw.tcp.TCPClient;
import com.swift.mgw.tcp.dto.Message;
import com.swift.mgw.tcp.dto.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@ComponentScan(basePackages = "com.swift.mgw")
public class Application {

    private final static String queueName = "mgw-requests-queue";
    private final static String mapName = "mgw-token-map";

    @Autowired
    private TCPClient client;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.out.println("Application started");
    }

    @PostConstruct
    public void init() throws InterruptedException {
        tcpService();
    }

    private void tcpService() throws InterruptedException {
        Scanner in = new Scanner(System.in);

        while (true) {
            int res = in.nextInt();
            int n = ThreadLocalRandom.current().nextInt(1, 5);

            if (res == 1) {
                System.out.println("Put token!");

                Message message = new Message("uetr" + n, "MT10" + n);
                client.writeMap(mapName, "token" + n, new Token("key" + n, message));
                client.writeQueue(queueName, message);
            } else if (res == 2) {
                System.out.println("Get token and message!");

                client.readMap(mapName);
                client.readQueue(queueName);
            } else if (res == 0) {
                System.out.println("Exit!");

                client.close();
                break;
            }
        }
    }
}