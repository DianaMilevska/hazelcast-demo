package com.swift.mgw.rest;

import com.swift.mgw.rest.dto.Person;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.swift.mgw.common.CommonUtils.closeQuietly;

/**
 * Hazelcast REST client.
 * <p>
 * Reads a simple string value from a map.
 * Reads an object from a map.
 */
@Service
public class RestClient {
    @Value("${hz.node.ip}")
    private String hzNodeIp;

    @Value("${hz.node.port}")
    private String hzNodePort;

    // Base Hazelcast REST url
    // @see http://docs.hazelcast.org/docs/latest/manual/html/restclient.html
    private final String HZ_REST_URL = "http://" + hzNodeIp + ":" + hzNodePort + "/hazelcast/rest";

    private WebTarget target;

    public RestClient() {
//        Client client = ClientBuilder.newClient();
        ResteasyClient client = new ResteasyClientBuilder().build();
        this.target = client.target(HZ_REST_URL);
    }

    //"/maps/simple/key1"
    public void getPerson(String serviceUrl) throws IOException, ClassNotFoundException {

        // querying map with String values
        Response stringResponse = target.path(serviceUrl).request().get();
        String responseBody = stringResponse.readEntity(String.class);
        System.out.println("Value for key1 is " + responseBody);

        // querying map with Person object values
        Response objectResponse = target.path(serviceUrl).request().get();
        byte[] entity = objectResponse.readEntity(byte[].class);
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(entity, 8, entity.length);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Person person = (Person) objectInputStream.readObject();
            System.out.println("Object for key1 is " + person.toString());
        } finally {
            closeQuietly(objectInputStream);
            closeQuietly(byteArrayInputStream);
        }
    }

    private static String doGet(String url) throws IOException {
        HttpURLConnection httpUrlConnection = (HttpURLConnection) (new URL(url)).openConnection();
        try {
            InputStream inputStream = httpUrlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int readBytes;
            while ((readBytes = inputStream.read(buffer)) > -1) {
                builder.append(new String(buffer, 0, readBytes));
            }
            return builder.toString();
        } finally {
            httpUrlConnection.disconnect();
        }
    }
}
