package com.crud.csr;


import com.crud.HttpConstants;
import com.crud.exceptions.NotFoundPostException;
import com.crud.model.Post;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class PostController {

    private final PostService service;

    public PostController(PostService service){
        this.service = service;
    }

    public void getAll(HttpServletResponse servletResponse){

        try {

            final List<Post> posts = this.service.getAll();
            final Gson gson = new Gson();

            String jsonPosts = gson.toJson(posts);
            servletResponse.setStatus(HttpServletResponse.SC_OK);
            servletResponse.setContentType(HttpConstants.APPLICATION_JSON);
            servletResponse.getWriter().write(jsonPosts);

        } catch (IOException e) {
            servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
    public void getById(long id, HttpServletResponse servletResponse){
        try {

            final Post post = this.service.getById(id);
            Gson gson = new Gson();
            String postJson = gson.toJson(post);

            servletResponse.setStatus(HttpServletResponse.SC_OK);
            servletResponse.setContentType(HttpConstants.APPLICATION_JSON);
            servletResponse.getWriter().write(postJson);

        } catch (NotFoundPostException e) {
            try {
                servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                servletResponse.getWriter().write("That post is not exist");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }
    }
    
    public void save(long id, Reader body, HttpServletResponse servletResponse){

        Gson gson = new Gson();
        final Post post = gson.fromJson(body, Post.class);
        try {
            final Post newPost = this.service.save(id, post);
            final String newPostJson = gson.toJson(newPost);

            servletResponse.setStatus(HttpServletResponse.SC_OK);
            servletResponse.setContentType(HttpConstants.APPLICATION_JSON);
            servletResponse.getWriter().write(newPostJson);

        } catch (NotFoundPostException e) {
            try {
                servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
                servletResponse.getWriter().write("That post is not exist");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void removeById(long id, HttpServletResponse servletResponse){
        final Post deletedPost = this.service.removeById(id);

        if (deletedPost == null) {
            servletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }else {
            final Gson gson = new Gson();
            String deletedPostJson = gson.toJson(deletedPost);

            try{
                servletResponse.setStatus(HttpServletResponse.SC_OK);
                servletResponse.setContentType(HttpConstants.APPLICATION_JSON);
                servletResponse.getWriter().write(deletedPostJson);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}