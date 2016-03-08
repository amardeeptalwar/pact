package com.meetup.api.group.pact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.meetup.api.group.Group;

public class GroupPort
{
    private String url;

    private RestTemplate restTemplate;

    @Autowired
    public GroupPort(@Value("${producer}") String url)
    {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    public List<Group> getGroup(Integer groupId)
    {
        ParameterizedTypeReference<List<Group>> responseType = new ParameterizedTypeReference<List<Group>>()
        {
        };
        return restTemplate.exchange(url + "/group/" + groupId, HttpMethod.GET, null, responseType).getBody();
    }
}
