package com.meetup.api.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@Component
public class GroupConsumerService
{
    @Autowired
    GroupConsumer consumer;


    @HystrixCommand(fallbackMethod = "defaultGroupObservable")
    public Observable<Group> getGroupObservable(Integer id)
    {
        return Observable.create(new Observable.OnSubscribe<Group>()
        {
            @Override
            public void call(Subscriber<? super Group> t)
            {
                System.out.println("getGroup:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getGroup(id));
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());

    }



    @HystrixCommand(fallbackMethod = "defaultGroupsObservable")
    public Observable<List<Group>> getGroupsObservable()
    {
        return Observable.create(new Observable.OnSubscribe<List<Group>>()
        {
            @Override
            public void call(Subscriber<? super List<Group>> t)
            {
                System.out.println("getGroups:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getGroups());
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Group> defaultGroupObservable(Integer id)
    {
        System.out.println("defaultGroupObservable");
        return null;
    }

    public Observable<Group> defaultGroupsObservable()
    {
        System.out.println("defaultGroupsObservable");
        return null;
    }

}
