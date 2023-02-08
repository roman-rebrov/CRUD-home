package com.crud;

import com.crud.csr.PostController;
import com.crud.csr.PostRepository;
import com.crud.csr.PostService;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class CrudServlet extends HttpServlet {

    private PostController postController;

    @Override
    public void init(ServletConfig servletConfig) {
        final PostRepository postRepository = new PostRepository();
        final PostService postService = new PostService(postRepository);
        this.postController = new PostController(postService);
    }

    @Override
    public void service(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse) throws ServletException, IOException {


        final String method = servletRequest.getMethod();
        final String path = servletRequest.getRequestURI();


        try {

            if (path.equals(HttpConstants.PATH) && method.equals(HttpConstants.METHOD_GET)) {

                this.postController.getAll(servletResponse);
            }

            if (path.matches(HttpConstants.PATH_WITH_ID) && method.equals(HttpConstants.METHOD_GET)) {

                final long id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                this.postController.getById(id, servletResponse);
            }

            if (path.matches(HttpConstants.PATH_WITH_ID) && method.equals(HttpConstants.METHOD_POST)) {

                final long id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                Reader body = servletRequest.getReader();
                this.postController.save(id, body, servletResponse);
            }

            if (path.matches(HttpConstants.PATH_WITH_ID) && method.equals(HttpConstants.METHOD_DELETE)) {

                final long id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                this.postController.removeById(id, servletResponse);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            servletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            servletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }


}
