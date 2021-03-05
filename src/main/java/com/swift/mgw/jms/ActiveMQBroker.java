//package com.swift.mgw.jms;
//
//import org.apache.activemq.broker.BrokerService;
//
///**
// * Utility class to start/stop an ActiveMQ Broker instance
// */
//public final class ActiveMQBroker {
//
//    public static final String BROKER_URL = "tcp://localhost:61616";
//
//    private BrokerService broker;
//
//    ActiveMQBroker() throws Exception {
//        broker = new BrokerService();
//        broker.setPersistent(false);
//        broker.addConnector(BROKER_URL);
//        broker.start();
//    }
//
//    public void stop() throws Exception {
//        broker.stop();
//        broker.waitUntilStopped();
//    }
//}
