
package com.abhishek.topgithub.data;

import com.abhishek.topgithub.model.Repository;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface RepoApi {

    @GET("developers?language=java&amp;since=weekly")
    Observable<List<Repository>> getRepositories();
}
