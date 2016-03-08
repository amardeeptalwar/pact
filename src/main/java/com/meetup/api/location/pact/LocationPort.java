package com.meetup.api.location.pact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.meetup.api.location.Location;

public class LocationPort
{
    private String url;

    private RestTemplate restTemplate;

    @Autowired
    public LocationPort(@Value("${producer}") String url)
    {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    public Location getLocation(Integer locationId)
    {
        ParameterizedTypeReference<Location> responseType = new ParameterizedTypeReference<Location>()
        {
        };
        return restTemplate.exchange(url + "/location/" + locationId, HttpMethod.GET, null, responseType).getBody();
    }
}
