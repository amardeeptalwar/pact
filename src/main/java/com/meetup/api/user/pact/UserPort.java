package com.meetup.api.user.pact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.meetup.api.user.User;

public class UserPort
{
    private String url;

    private RestTemplate restTemplate;

    @Autowired
    public UserPort(@Value("${producer}") String url)
    {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    public User getUser(Integer userId)
    {
        ParameterizedTypeReference<User> responseType = new ParameterizedTypeReference<User>()
        {
        };
        return restTemplate.exchange(url + "/user/" + userId, HttpMethod.GET, null, responseType).getBody();
    }
}
