package com.swift.mgw;

import com.swift.mgw.rest.Member;
import com.swift.mgw.rest.RestClient;
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

    private final String queueName = "mgw-requests-queue";
    private String mapName = "mgw-token-map";

    @Autowired
    private TCPClient client;

    @Autowired
    private Member restMember;

    @Autowired
    private RestClient restClient;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.out.println("Application started");
    }

    @PostConstruct
    public void init() throws Exception {
        restService();
        tcpService();
    }

    private void restService() throws Exception {
        restMember.addPerson();
        restClient.getPerson("/maps/object/key1");
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