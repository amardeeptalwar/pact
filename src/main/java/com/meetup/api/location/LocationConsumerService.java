package com.meetup.api.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@Component
public class LocationConsumerService
{
    @Autowired
    LocationConsumer consumer;

    @HystrixCommand(fallbackMethod = "defaultLocationObservable")
    public Observable<Location> getLocationObservable(Integer id)
    {

        return Observable.create(new Observable.OnSubscribe<Location>()
        {
            @Override
            public void call(Subscriber<? super Location> t)
            {
                System.out.println(
                        "getLocation:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getLocation(id));
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());

    }

    @HystrixCommand(fallbackMethod = "defaultLocationsObservable")
    public Observable<List<Location>> getLocationsObservable()
    {
        return Observable.create(new Observable.OnSubscribe<List<Location>>()
        {
            @Override
            public void call(Subscriber<? super List<Location>> t)
            {
                System.out.println(
                        "getLocation:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getLocations());
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Location> defaultLocationObservable(Integer id)
    {
        System.out.println("defaultLocationObservable");
        return null;
    }

    public Observable<Location> defaultLocationsObservable()
    {
        System.out.println("defaultLocationsObservable");
        return null;
    }

}
