
package com.abhishek.topgithub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abhishek.topgithub.R;
import com.abhishek.topgithub.adapter.RepositoryListAdapter;
import com.abhishek.topgithub.base.BaseFragment;
import com.abhishek.topgithub.listener.RepoFragmentListener;
import com.abhishek.topgithub.model.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ListFragment extends BaseFragment {

    private RepoFragmentListener fragmentListener;

    @BindView(R.id.repo_list_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.progress_circular)
    protected ProgressBar progressBar;
    @BindView(R.id.tv_error)
    protected TextView errorTextView;

    protected Unbinder mUnbinder;

    public static ListFragment getInstance() {
        return new ListFragment();
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_list;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        fragmentListener = (RepoFragmentListener) getActivity();
        fragmentListener.loadRepositoryList();
    }

    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void displayProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void removeProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void onError() {
        errorTextView.setText(R.string.error);
    }

    public void showEmpty() {
        errorTextView.setText(R.string.empty_repository_list);
    }

    public void showRepositories(List<Repository> repositoryList) {

        RepositoryListAdapter adapter = new RepositoryListAdapter(getContext(), repositoryList,
                new RepositoryListAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Repository item) {
                        fragmentListener.showRepositoryDetails(item);
                    }
                });

        recyclerView.setAdapter(adapter);
    }
}
