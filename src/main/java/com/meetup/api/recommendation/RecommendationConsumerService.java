package com.meetup.api.recommendation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meetup.api.location.Location;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@Component
public class RecommendationConsumerService
{
    @Autowired
    RecommendationConsumer consumer;

    @HystrixCommand(fallbackMethod = "defaultRecommendationObservable")
    public Observable<Recommendation> getRecommendationObservable(Integer id)
    {

        return Observable.create(new Observable.OnSubscribe<Recommendation>()
        {
            @Override
            public void call(Subscriber<? super Recommendation> t)
            {
                System.out.println(
                        "getRecommendation:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getRecommendation(id));
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    @HystrixCommand(fallbackMethod = "defaultRecommendationsObservable")
    public Observable<List<Recommendation>> getRecommendationsObservable()
    {        
        return Observable.create(new Observable.OnSubscribe<List<Recommendation>>()
        {
            @Override
            public void call(Subscriber<? super List<Recommendation>> t)
            {
                System.out.println(
                        "getLocation:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getRecommendations());
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());     
        
    }

    public Observable<Recommendation> defaultRecommendationObservable(Integer id)
    {
        System.out.println("defaultRecommendationObservable");
        return null;
    }

    public Observable<Recommendation> defaultRecommendationsObservable()
    {
        System.out.println("defaultRecommendationsObservable");
        return null;
    }

}
