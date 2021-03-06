package com.swift.mgw.rest;

import com.hazelcast.config.Config;
import com.hazelcast.config.RestApiConfig;
import com.hazelcast.config.RestEndpointGroup;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.swift.mgw.rest.dto.Person;

//@SpringBootApplication
public class Member {
    private HazelcastInstance hazelcastClient;

    public Member() {
        Config config = new Config();
        RestApiConfig restApiConfig = new RestApiConfig()
                .setEnabled(true)
                .disableAllGroups()
                .enableGroups(RestEndpointGroup.DATA);
        config.getNetworkConfig().setRestApiConfig(restApiConfig);

        this.hazelcastClient = Hazelcast.newHazelcastInstance(config);
    }

    public void addPerson() throws Exception {
        Person person = new Person("Joe");

        IMap<String, String> hzSimpleMap = hazelcastClient.getMap("simple");
        hzSimpleMap.set("key1", "value1");

        IMap<String, Person> hzObjectMap = hazelcastClient.getMap("object");
        hzObjectMap.set("key1", person);

    }
}
