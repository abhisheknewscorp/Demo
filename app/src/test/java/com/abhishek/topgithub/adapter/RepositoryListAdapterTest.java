package com.abhishek.topgithub.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.topgithub.model.Repository;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RepositoryListAdapter.class, LayoutInflater.class, Glide.class, ButterKnife.class})
public class RepositoryListAdapterTest {

    private static final int TEST_VIEW_TYPE = 0;
    RepositoryListAdapter testAdapter;
    private RepositoryListAdapter.OnItemClickListener onItemClickListener = PowerMockito.mock(RepositoryListAdapter.OnItemClickListener.class);
    private List<Repository> repositories;
    private Context context = PowerMockito.mock(Context.class);

    protected LayoutInflater mockLayoutInflater = PowerMockito.mock(LayoutInflater.class);
    protected ViewGroup mockParent = PowerMockito.mock(ViewGroup.class);
    protected Context mockContext = PowerMockito.mock(Context.class);
    protected View mockInflatedView = PowerMockito.mock(View.class);
    protected RepositoryListAdapter.OnItemClickListener mockListner = PowerMockito.mock(RepositoryListAdapter.OnItemClickListener.class);

    @Before
    public void setup() {
        repositories = getSampleList();
        testAdapter = new RepositoryListAdapter(context, repositories, onItemClickListener);
        Assert.assertNotNull(testAdapter);
    }

    protected void setupCreateViewHolderTest(final int layoutRes) {
        PowerMockito.mockStatic(LayoutInflater.class);
        PowerMockito.mockStatic(ButterKnife.class);
        PowerMockito.when(LayoutInflater.from(mockContext)).thenReturn(mockLayoutInflater);
        PowerMockito.when(mockParent.getContext()).thenReturn(mockContext);
        PowerMockito.when(mockLayoutInflater.inflate(layoutRes, null)).thenReturn(mockInflatedView);
    }

    @Test
    public void onBindViewHolder() {
        PowerMockito.mockStatic(Glide.class);
        PowerMockito.mockStatic(ButterKnife.class);
        RequestManager requestManager = PowerMockito.mock(RequestManager.class);
        RequestBuilder<Drawable> builder = PowerMockito.mock(RequestBuilder.class);
        PowerMockito.when(Glide.with(any(Context.class))).thenReturn(requestManager);
        PowerMockito.doReturn(builder).when(requestManager).load(anyString());
        RepositoryListAdapter.RepoViewHolder holder = new RepositoryListAdapter.RepoViewHolder(mockInflatedView,
                mockListner);
        holder.name = PowerMockito.mock(TextView.class);
        holder.username = PowerMockito.mock(TextView.class);
        holder.avatar = PowerMockito.mock(ImageView.class);
        testAdapter.onBindViewHolder(holder, 0);
        verify(holder.name).setText("target");
        verify(holder.username).setText("Target");
        verify(requestManager).load("https://target.com/sample.jpg");
        testAdapter.onBindViewHolder(holder, 1);
        verify(holder.name).setText("facebook");
        verify(holder.username).setText("Facebook");
        verify(requestManager).load("https://facebook.com/sample.jpg");

    }

    @Test
    public void getItemCount() {
        Assert.assertEquals(2, testAdapter.getItemCount());
    }

    private List<Repository> getSampleList() {
        ArrayList<Repository> list = new ArrayList<>();
        Repository repo_one = new Repository("Target", "target",
                "https://target.com", "https://target.com/sample.jpg");
        list.add(repo_one);

        Repository repo_two = new Repository("Facebook", "facebook",
                "https://facebook.com", "https://facebook.com/sample.jpg");
        list.add(repo_two);

        return list;
    }
}