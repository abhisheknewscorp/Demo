package com.abhishek.topgithub.fragment;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.abhishek.topgithub.activity.MainActivity;
import com.abhishek.topgithub.adapter.RepositoryListAdapter;
import com.abhishek.topgithub.listener.RepoFragmentListener;
import com.abhishek.topgithub.model.Repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ListFragment.class, ButterKnife.class})
public class ListFragmentTest {

    ListFragment testFragment;

    @Before
    public void setup() {
        testFragment = ListFragment.getInstance();
        Assert.assertNotNull(testFragment);
        testFragment.progressBar = PowerMockito.mock(ProgressBar.class);
    }

    @Test
    public void onViewCreated() {
        PowerMockito.mockStatic(ButterKnife.class);
        View view = PowerMockito.mock(View.class);
        testFragment.onViewCreated(view, null);
        PowerMockito.verifyStatic();
        ButterKnife.bind(testFragment, view);
    }

    @Test
    public void onActivityCreated() {
        ListFragment spyFragment = PowerMockito.spy(testFragment);
        MainActivity activity = PowerMockito.mock(MainActivity.class);
        PowerMockito.when(spyFragment.getActivity()).thenReturn(activity);
        PowerMockito.doNothing().when(spyFragment).initView();
        RepoFragmentListener fragmentListener = PowerMockito.mock(RepoFragmentListener.class);
        spyFragment.onActivityCreated(null);
        Mockito.verify(spyFragment).initView();
    }

    @Test
    public void initView() throws Exception {
        ListFragment spyFragment = PowerMockito.spy(testFragment);
        spyFragment.recyclerView = PowerMockito.mock(RecyclerView.class);
        MainActivity activity = PowerMockito.mock(MainActivity.class);
        PowerMockito.when(spyFragment.getActivity()).thenReturn(activity);
        Context context = PowerMockito.mock(MainActivity.class);
        PowerMockito.when(spyFragment.getContext()).thenReturn(context);
        LinearLayoutManager linearLayoutManager = PowerMockito.mock(LinearLayoutManager.class);
        PowerMockito.whenNew(LinearLayoutManager.class).withArguments(context).thenReturn(linearLayoutManager);
        DividerItemDecoration itemDecoration = PowerMockito.mock(DividerItemDecoration.class);
        PowerMockito.whenNew(DividerItemDecoration.class).withArguments(activity, DividerItemDecoration.VERTICAL).thenReturn(itemDecoration);
        spyFragment.initView();
        Mockito.verify(spyFragment.recyclerView).setLayoutManager(any(LinearLayoutManager.class));
    }

    @Test
    public void displayProgressBar() {
        testFragment.displayProgressBar();
        Mockito.verify(testFragment.progressBar).setVisibility(View.VISIBLE);
    }

    @Test
    public void removeProgressBar() {
        testFragment.removeProgressBar();
        Mockito.verify(testFragment.progressBar).setVisibility(View.GONE);
    }

    @Test
    public void showRepositories() throws Exception {
        List<Repository> repositoryList = new ArrayList<>();
        ListFragment spyFragment = PowerMockito.spy(testFragment);
        Context context = PowerMockito.mock(MainActivity.class);
        PowerMockito.when(spyFragment.getContext()).thenReturn(context);
        RepositoryListAdapter listAdapter = PowerMockito.mock(RepositoryListAdapter.class);
        PowerMockito.whenNew(RepositoryListAdapter.class).withArguments(any(Context.class), anyList(), any(RepositoryListAdapter.class))
                .thenReturn(listAdapter);
        spyFragment.recyclerView = PowerMockito.mock(RecyclerView.class);
        spyFragment.showRepositories(repositoryList);
        Mockito.verify(spyFragment.recyclerView).setAdapter(listAdapter);

    }
}