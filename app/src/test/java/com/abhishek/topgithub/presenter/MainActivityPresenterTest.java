package com.abhishek.topgithub.presenter;

import com.abhishek.topgithub.data.RepoService;
import com.abhishek.topgithub.listener.MainActivityPresenterListener;
import com.abhishek.topgithub.model.Repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainActivityPresenter.class, CompositeSubscription.class, Repository.class})
public class MainActivityPresenterTest {

    MainActivityPresenter testPresenter;
    RepoService repoService;
    MainActivityPresenterListener presenterListener;

    @Before
    public void setup() {
        repoService = PowerMockito.mock(RepoService.class);
        presenterListener = PowerMockito.mock(MainActivityPresenterListener.class);
        testPresenter = new MainActivityPresenter(repoService, presenterListener);
        Assert.assertNotNull(testPresenter);
        testPresenter.subscriptions = PowerMockito.mock(CompositeSubscription.class);
    }

    @Test
    public void getRepoList() {
        List<Repository> repositories = new ArrayList<>();
        Repository repository = PowerMockito.mock(Repository.class);
        ArgumentCaptor<RepoService.RepositoryListCallback> captor =
                ArgumentCaptor.forClass(RepoService.RepositoryListCallback.class);
        Subscription subscription = PowerMockito.mock(Subscription.class);
        PowerMockito.doReturn(subscription).when(repoService).getRepositoryList(captor.capture());
        testPresenter.fetchRepositoryList();
        captor.getValue().onSuccess(repositories);
        Mockito.verify(presenterListener).displayProgress();
        Mockito.verify(testPresenter.subscriptions).add(any(Subscription.class));
        Mockito.verify(presenterListener).removeProgress();
        Mockito.verify(presenterListener, times(0)).onSuccess(repositories);
        Mockito.verify(presenterListener, times(1)).showEmpty();
        repositories.add(repository);
        testPresenter.fetchRepositoryList();
        captor.getValue().onSuccess(repositories);
        testPresenter.fetchRepositoryList();
        captor.getValue().onError();
        Mockito.verify(presenterListener, Mockito.times(3)).removeProgress();
        Mockito.verify(presenterListener).onError();
    }

    @Test
    public void destroy() {
        testPresenter.destroy();
        verify(testPresenter.subscriptions).unsubscribe();
    }
}