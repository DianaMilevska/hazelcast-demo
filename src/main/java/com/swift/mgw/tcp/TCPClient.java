package com.swift.mgw.tcp;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceNotActiveException;
import com.hazelcast.map.IMap;
import com.swift.mgw.tcp.dto.Message;
import com.swift.mgw.tcp.dto.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

@Service
public class TCPClient {

    private ClientConfig clientConfig;
    private HazelcastInstance hazelcastClient;

    public TCPClient(@Value("${hz.node.ip}") String hzNodeIp) {
        clientConfig = new ClientConfig();
        clientConfig.getNetworkConfig().addAddress(hzNodeIp);

        this.hazelcastClient = HazelcastClient.newHazelcastClient(clientConfig);

    }

    public Message readQueue(String queueName) {
        BlockingQueue<Message> queue = hazelcastClient.getQueue(queueName);
        try {
            if (queue.isEmpty()) {
                System.out.println("===== Message queue is empty! =====");
                return null;
            } else {
                Message message = queue.poll();
                System.out.println("===== Pull the next message from queue: =====");
                System.out.println(message);
                return message;
            }
        } catch (HazelcastInstanceNotActiveException e) {
            System.err.println("Unable to take from the queue. Hazelcast Member is probably going down!");
        }

        return null;
    }

    public Set<Token> readMap(String mapName) {
        System.out.println("===== All tokens from map: =====");

        IMap map = hazelcastClient.getMap(mapName);
        try {
            map.entrySet().stream().forEach(System.out::println);
            return map.entrySet();
        } catch (HazelcastInstanceNotActiveException e) {
            System.err.println("Unable to take from the queue. Hazelcast Member is probably going down!");
        }
        return null;
    }

    public void writeMap(String mapName, String key, Token token) {
        System.out.println("+++++ Put token to map with key: " + key + " +++++");

        IMap map = hazelcastClient.getMap(mapName);
        map.put(key, token);
    }

    public void writeQueue(String queueName, Message message) throws InterruptedException {
        System.out.println("+++++ Put message to queue with uetr: " + message.getUetr() + " +++++");

        BlockingQueue<Message> queue = hazelcastClient.getQueue(queueName);
        queue.put(message);
    }

    public void close() {
        hazelcastClient.shutdown();
    }
}
