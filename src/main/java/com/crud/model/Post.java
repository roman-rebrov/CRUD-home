package com.crud.model;

public class Post {

    private final long ID;
    private String content;

    public Post(final long id, final String content){
        this.ID = id;
        this.content = content;
    }

    public long getID() {
        return ID;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
