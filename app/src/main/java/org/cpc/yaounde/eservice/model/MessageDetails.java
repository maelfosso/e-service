package org.cpc.yaounde.eservice.model;

import java.io.Serializable;

/**
 * Created by maelfosso on 11/9/16.
 */
public class MessageDetails implements Serializable {
    private long id;
    private String date;
    private User user;
    private String content;
    private boolean fromMe;

    public MessageDetails(long id, String date, User user, String content, boolean fromMe) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.content = content;
        this.fromMe = fromMe;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
