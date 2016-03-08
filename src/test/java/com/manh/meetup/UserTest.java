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

import com.meetup.api.user.User;
import com.meetup.api.user.pact.UserPort;

public class UserTest
{
    @Rule
    public PactRule rule = new PactRule("localhost", 9023, this);

    @Pact(state = "Gateway_User_State", provider = "User", consumer = "Gateway")
    public PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider.PactDslWithState builder)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.uponReceiving("a request for Users").path("/user/1").method("GET").willRespondWith()
                .headers(headers).status(200)
                .body("{\"id\":1,\"firstName\": \"Amardeep\",\"middleName\": \"Singh\",\"lastName\": \"Talwar\"}")
                .toFragment();
    }

    @Test
    @PactVerification("Gateway_User_State")
    public void runTest()
    {
        User user = new User(1, "Amardeep", "Singh", "Talwar");

        assertEquals(new UserPort("http://localhost:9023").getUser(1), user);

    }

}
