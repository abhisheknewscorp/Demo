package com.abhishek.topgithub.presenter;

import com.abhishek.topgithub.data.RepoService;
import com.abhishek.topgithub.listener.MainActivityPresenterListener;
import com.abhishek.topgithub.model.Repository;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivityPresenter {

    private final RepoService repoService;
    private final MainActivityPresenterListener presenterListener;
    protected CompositeSubscription subscriptions;

    public MainActivityPresenter(RepoService repoService, MainActivityPresenterListener presenterListener) {
        this.repoService = repoService;
        this.presenterListener = presenterListener;
        this.subscriptions = new CompositeSubscription();
    }

    public void fetchRepositoryList() {
        presenterListener.displayProgress();
        Subscription subscription = repoService.getRepositoryList(new RepoService.RepositoryListCallback() {
            @Override
            public void onSuccess(List<Repository> repositoryList) {
                presenterListener.removeProgress();
                if (repositoryList != null && repositoryList.size() > 0) {
                    presenterListener.onSuccess(repositoryList);
                } else {
                    presenterListener.showEmpty();
                }
            }

            @Override
            public void onError() {
                presenterListener.removeProgress();
                presenterListener.onError();
            }

        });
        subscriptions.add(subscription);
    }

    public void destroy() {
        subscriptions.unsubscribe();
    }
}
