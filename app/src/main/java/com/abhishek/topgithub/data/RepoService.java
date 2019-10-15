
package com.abhishek.topgithub.data;

import com.abhishek.topgithub.model.Repository;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RepoService {

    private final RepoApi repoApi;

    public RepoService(RepoApi repoApi) {
        this.repoApi = repoApi;
    }

    public interface RepositoryListCallback{
        void onSuccess(List<Repository> repositories);
        void onError();
    }

    public Subscription getRepositoryList(final RepositoryListCallback callback) {
        return repoApi.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Repository>>>() {
                    @Override
                    public Observable<? extends List<Repository>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<Repository>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError();

                    }

                    @Override
                    public void onNext(List<Repository> repositories) {
                       callback.onSuccess(repositories);

                    }
                });
    }
}
