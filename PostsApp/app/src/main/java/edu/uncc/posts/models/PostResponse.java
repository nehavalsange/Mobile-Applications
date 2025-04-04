package edu.uncc.posts.models;

import java.io.Serializable;
import java.util.ArrayList;

public class PostResponse implements Serializable {
    String status;
    ArrayList<Post> posts = new ArrayList<>();
    int page;
    int pageSize;
    int totalCount;

    public PostResponse(){

    }

    public PostResponse(String status, ArrayList<Post> posts, int page, int pageSize, int totalCount) {
        this.status = status;
        this.posts = posts;
        this.page = page;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "PostResponse{" +
                "status='" + status + '\'' +
                ", posts=" + posts +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                '}';
    }
}
