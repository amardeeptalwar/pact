package com.manh.meetup;

import static junit.framework.TestCase.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.model.PactFragment;

import com.meetup.api.recommendation.Recommendation;
import com.meetup.api.recommendation.pact.RecommendationPort;

public class RecommendationTest
{
    @Rule
    public PactRule rule = new PactRule("localhost", 9023, this);

    @Pact(state = "Gateway_Recommendation_State", provider = "Recommendation", consumer = "Gateway")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider.PactDslWithState builder)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.uponReceiving("a request for Recommendations").path("/recommendation/999").method("GET").willRespondWith()
                .headers(headers).status(200)
                .body("[{\"id\":999,\"locationId\":100,\"lead\": \"Biking\",\"alternateLead\": \"River Rafting\"}]")
                .toFragment();
    }

    @Test
    @PactVerification("Gateway_Recommendation_State")
    public void runTest()
    {
        Recommendation r = new Recommendation(999, 100, "Biking", "River Rafting");

        assertEquals(new RecommendationPort("http://localhost:9023").getRecommendation(999), Arrays.asList(r));

    }

}
