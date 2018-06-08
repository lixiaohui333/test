package com.meeting.client.domain.eventbus;

/**
 * Created by Administrator on 2016/10/14.
 */
public class EventMessage<T> {

    public final String rel;

    public String method;

    public String text;

    public Object obj;


    public EventMessage(Class<T> activityName) {
        this.rel = activityName.getName();
        this.text = null;
        this.method = null;
    }

    public EventMessage(Class<T> activityName, String text) {
        this.rel = activityName.getName();
        this.text = text;
        this.method = null;
    }

    public EventMessage(Class<T> activityName, String method, String text) {
        this.rel = activityName.getName();
        this.method = method;
        this.text = text;
    }

    public EventMessage(Class<T> activityName, String method, Object obj) {
        this.rel = activityName.getName();
        this.method = method;
        this.obj = obj;
    }

    public EventMessage(Class<T> activityName, Object obj) {
        this.rel = activityName.getName();
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "rel='" + rel + '\'' +
                ", method='" + method + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
