package com.crud.csr;

import com.crud.exceptions.NotFoundPostException;
import com.crud.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }


    public List<Post> getAll() {
        ConcurrentHashMap<Long, Post> posts = this.repository.getAllPosts();
        return new ArrayList<Post>(posts.values());
    }

    public Post getById(long id) throws NotFoundPostException {
        Post post = this.repository.getById(id);

        if (post == null) {
            throw new NotFoundPostException();
        }
        return post;
    }

    public Post save(long id, Post post) throws NotFoundPostException {

        if (id == 0) {
            final Post newPost = this.repository.createPost(post);
            return newPost;
        } else {

            ConcurrentHashMap<Long, Post> posts = this.repository.getAllPosts();
            if (posts.containsKey(id)) {
                final Post updatedPost = new Post(id, post.getContent());
                posts.put(updatedPost.getID(), updatedPost);
                return updatedPost;
            } else {
                throw new NotFoundPostException();
            }
        }
    }

    public Post removeById(long id) {
        ConcurrentHashMap<Long, Post> posts = this.repository.getAllPosts();
        Post post = null;
        if (posts.containsKey(id)) {
            post = posts.remove(id);
        }
        return post;
    }

}
