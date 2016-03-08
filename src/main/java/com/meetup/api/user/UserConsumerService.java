package com.meetup.api.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

@Component
public class UserConsumerService
{
    @Autowired
    UserConsumer consumer;

    @HystrixCommand(fallbackMethod = "defaultUserObservable")
    public Observable<User> getUserObservable(Integer id)
    {        
        return Observable.create(new Observable.OnSubscribe<User>()
        {
            @Override
            public void call(Subscriber<? super User> t)
            {
                System.out.println("getUser:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getUser(id));
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    @HystrixCommand(fallbackMethod = "defaultUsersObservable")
    public Observable<List<User>> getUsersObservable()
    {
        return Observable.create(new Observable.OnSubscribe<List<User>>()
        {
            @Override
            public void call(Subscriber<? super List<User>> t)
            {
                System.out.println(
                        "getUsers:" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName());
                t.onNext(consumer.getUsers());
                t.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
        
        
    }

    public Observable<User> defaultUserObservable(Integer id)
    {
        System.out.println("defaultUserObservable");
        return null;
    }

    public Observable<User> defaultUsersObservable()
    {
        System.out.println("defaultUsersObservable");
        return null;
    }

}
