package com.manh.meetup;

import static junit.framework.TestCase.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.model.PactFragment;

import com.meetup.api.group.Group;
import com.meetup.api.group.pact.GroupPort;

public class GroupTest
{
    @Rule
    public PactRule rule = new PactRule("localhost", 9023, this);

    @Pact(state = "Gateway_Group_State", provider = "Group", consumer = "Gateway")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider.PactDslWithState builder)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder
                .uponReceiving("a request for Payments")
                .path("/group/1")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("[{\"id\":1,\"name\": \"microservice\",\"description\": \"microservice development\"}, {\"id\":2,\"name\": \"docker\",\"description\": \"Docker study using Docker Maching and Docker Swarm\"}]")
                .toFragment();
    }

    @Test
    @PactVerification("Gateway_Group_State")
    public void runTest()
    {
        Group group1 = new Group(1, "microservice", "microservice development");
        Group group2 = new Group(2, "docker", "Docker study using Docker Maching and Docker Swarm");
        assertEquals(new GroupPort("http://localhost:9023").getGroup(1), Arrays.asList(group1, group2));

    }

}
