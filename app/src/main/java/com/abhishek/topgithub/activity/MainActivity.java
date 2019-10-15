package com.abhishek.topgithub.activity;

import android.os.Bundle;

import com.abhishek.topgithub.R;
import com.abhishek.topgithub.base.BaseActivity;
import com.abhishek.topgithub.base.BaseApplication;
import com.abhishek.topgithub.data.RepoService;
import com.abhishek.topgithub.fragment.DetailFragment;
import com.abhishek.topgithub.fragment.ListFragment;
import com.abhishek.topgithub.listener.MainActivityPresenterListener;
import com.abhishek.topgithub.listener.RepoFragmentListener;
import com.abhishek.topgithub.model.Repository;
import com.abhishek.topgithub.presenter.MainActivityPresenter;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainActivityPresenterListener,
        RepoFragmentListener {

    private static final String LIST_FRAGMENT = "ListFragment";
    private static final String DETAIL_FRAGMENT = "DetailFragment";

    @Inject
    public RepoService repoService;

    protected MainActivityPresenter presenter;
    protected ListFragment listFragment;
    protected DetailFragment detailFragment;


    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseApplication)getApplication()).getAppComponent().inject(this);
        listFragment = ListFragment.getInstance();
        loadFragment(R.id.screenContainer, listFragment, LIST_FRAGMENT, false);
        presenter = new MainActivityPresenter(repoService, this);
    }

    @Override
    public void loadRepositoryList() {
        presenter.fetchRepositoryList();
    }

    @Override
    public void showRepositoryDetails(Repository item) {
        detailFragment = DetailFragment.getInstance(item);
        loadFragment(R.id.screenContainer, detailFragment, DETAIL_FRAGMENT, true);
    }


    @Override
    public void displayProgress() {
        listFragment.displayProgressBar();
    }

    @Override
    public void removeProgress() {
        listFragment.removeProgressBar();
    }


    @Override
    public void onError() {
        listFragment.onError();
    }

    @Override
    public void onSuccess(List<Repository> repositories) {
        if(!isFinishing()) {
            listFragment.showRepositories(repositories);
        }
    }

    @Override
    public void showEmpty() {
        listFragment.showEmpty();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
