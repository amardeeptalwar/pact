package com.meetup.api.recommendation.pact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.meetup.api.recommendation.Recommendation;

public class RecommendationPort
{
    private String url;

    private RestTemplate restTemplate;

    @Autowired
    public RecommendationPort(@Value("${producer}") String url)
    {
        this.url = url;
        this.restTemplate = new RestTemplate();
    }

    public List<Recommendation> getRecommendation(Integer recommendationId)
    {
        ParameterizedTypeReference<List<Recommendation>> responseType = new ParameterizedTypeReference<List<Recommendation>>()
        {
        };
        return restTemplate.exchange(url + "/recommendation/" + recommendationId, HttpMethod.GET, null, responseType).getBody();
    }
}
