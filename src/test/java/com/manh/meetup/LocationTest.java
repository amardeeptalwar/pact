package com.manh.meetup;

import static junit.framework.TestCase.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.model.PactFragment;

import com.meetup.api.location.Location;
import com.meetup.api.location.pact.LocationPort;

public class LocationTest
{
    @Rule
    public PactRule rule = new PactRule("localhost", 9023, this);

    @Pact(state = "Gateway_Location_State", provider = "Location", consumer = "Gateway")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider.PactDslWithState builder)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder
                .uponReceiving("a request for Locations")
                .path("/location/1")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("{\"id\":100,\"userId\": 1,\"name\": \"Bangalore\"}")
                .toFragment();
    }

    @Test
    @PactVerification("Gateway_Location_State")
    public void runTest()
    {
        Location location = new Location(100, 1, "Bangalore");

        assertEquals(new LocationPort("http://localhost:9023").getLocation(1), location);

    }

}
