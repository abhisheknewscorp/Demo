package com.abhishek.topgithub.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhishek.topgithub.model.Repo;
import com.abhishek.topgithub.model.Repository;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import butterknife.ButterKnife;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DetailFragment.class, ButterKnife.class,
        Glide.class, Repository.class, Gson.class})
public class DetailFragmentTest {

    DetailFragment testDetailFragment;
    Bundle bundle;
    Repository repository;
    Gson mockGson;

    @Before
    public void setup() throws Exception {
        bundle = PowerMockito.mock(Bundle.class);
        mockGson = PowerMockito.mock(Gson.class);
        PowerMockito.whenNew(Bundle.class).withNoArguments().thenReturn(bundle);
        PowerMockito.whenNew(Gson.class).withNoArguments().thenReturn(mockGson);
        repository = PowerMockito.mock(Repository.class);
        testDetailFragment = DetailFragment.getInstance(repository);
        Assert.assertNotNull(testDetailFragment);
        Mockito.verify(bundle).putString(anyString(), anyString());
    }


    @Test
    public void onActivityCreated() {
        DetailFragment spyFragment = PowerMockito.spy(testDetailFragment);
        PowerMockito.doReturn(bundle).when(spyFragment).getArguments();
        PowerMockito.doNothing().when(spyFragment).showDetails(any(Repository.class));
        spyFragment.onActivityCreated(null);
        Mockito.verify(spyFragment).getArguments();
        Mockito.verify(spyFragment).showDetails(any(Repository.class));
    }

    @Test
    public void showDetails() {
        DetailFragment spyFragment = PowerMockito.spy(testDetailFragment);
        spyFragment.nameTextView = PowerMockito.mock(TextView.class);
        spyFragment.usernameTextView = PowerMockito.mock(TextView.class);
        spyFragment.urlTextView = PowerMockito.mock(TextView.class);
        spyFragment.repoNameTextView = PowerMockito.mock(TextView.class);
        spyFragment.repoUrlTextView = PowerMockito.mock(TextView.class);
        spyFragment.descriptionTextView = PowerMockito.mock(TextView.class);
        spyFragment.avatarImageView = PowerMockito.mock(ImageView.class);
        Context context = PowerMockito.mock(Context.class);
        PowerMockito.doReturn(context).when(spyFragment).getContext();
        PowerMockito.mockStatic(Glide.class);
        RequestManager requestManager = PowerMockito.mock(RequestManager.class);
        RequestBuilder<Drawable> builder = PowerMockito.mock(RequestBuilder.class);
        PowerMockito.when(Glide.with(any(Context.class))).thenReturn(requestManager);
        PowerMockito.doReturn(builder).when(requestManager).load(anyString());
        Repo repo = new Repo("name repo", "repo desc", "repo url");
        Repository repository = new Repository("username test", "name test",
                "https://test.com", "https://test.com/img.jpg", repo);
        spyFragment.showDetails(repository);
        Mockito.verify(spyFragment).getContext();
        Mockito.verify(requestManager).load(anyString());
        Mockito.verify(spyFragment.nameTextView).setText("name test");
        Mockito.verify(spyFragment.usernameTextView).setText("username test");
        Mockito.verify(spyFragment.repoNameTextView).setText("name repo");
        Mockito.verify(spyFragment.repoUrlTextView).setText("repo url");
        Mockito.verify(spyFragment.descriptionTextView).setText("repo desc");
    }
}