package com.crud.csr;

import com.crud.model.Post;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {

    private AtomicLong ID = new AtomicLong(0);
    private ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap();

    public Post createPost(Post post){
        Post newPost = new Post(ID.incrementAndGet(), post.getContent());
        posts.put(newPost.getID(), newPost);
        return newPost;
    }

    public Post getById(long id){
        return posts.get(id);
    }

    public ConcurrentHashMap<Long, Post>   getAllPosts(){
        return this.posts;
    }

}
