
package com.abhishek.topgithub.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.abhishek.topgithub.R;
import com.abhishek.topgithub.fragment.DetailFragment;
import com.abhishek.topgithub.fragment.ListFragment;
import com.abhishek.topgithub.model.Repository;
import com.abhishek.topgithub.presenter.MainActivityPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MainActivity.class, DetailFragment.class, Repository.class})
public class MainActivityTest {

    private TestActivity testMainActivity;

    @Before
    public void setup() {
        testMainActivity = new TestActivity();
        Assert.assertNotNull(testMainActivity);
        testMainActivity.listFragment = PowerMockito.mock(ListFragment.class);
        testMainActivity.detailFragment = PowerMockito.mock(DetailFragment.class);
        testMainActivity.presenter = PowerMockito.mock(MainActivityPresenter.class);
    }

    @Test
    public void onError() {
        TestActivity spyTestActivity = PowerMockito.spy(testMainActivity);
        PowerMockito.doReturn("test").when(spyTestActivity).getString(anyInt());
        spyTestActivity.onError();
        verify(testMainActivity.listFragment).onError();
    }

    @Test
    public void onSuccess() {
        List list = new ArrayList<Repository>();
        testMainActivity.isFinishing = true;
        testMainActivity.onSuccess(list);
        verify(testMainActivity.listFragment, times(0)).showRepositories(list);
        testMainActivity.isFinishing = false;
        testMainActivity.onSuccess(list);
        verify(testMainActivity.listFragment).showRepositories(list);
    }

    @Test
    public void loadRepositoryList() {
        testMainActivity.loadRepositoryList();
        verify(testMainActivity.presenter).fetchRepositoryList();
    }

    @Test
    public void showRepositoryDetails() {
        PowerMockito.mockStatic(DetailFragment.class);
        TestActivity spyTestActivity = PowerMockito.spy(testMainActivity);
        PowerMockito.doNothing().when(spyTestActivity).loadFragment(anyInt(),
                any(Fragment.class), anyString(), anyBoolean());
        Repository repository = PowerMockito.mock(Repository.class);
        spyTestActivity.showRepositoryDetails(repository);
        PowerMockito.verifyStatic();
        DetailFragment.getInstance(repository);
        verify(spyTestActivity).loadFragment(R.id.screenContainer,
                spyTestActivity.detailFragment, "DetailFragment", true);

    }

    @Test
    public void displayProgress() {
        testMainActivity.displayProgress();
        verify(testMainActivity.listFragment).displayProgressBar();
    }

    @Test
    public void removeProgress() {
        testMainActivity.removeProgress();
        verify(testMainActivity.listFragment).removeProgressBar();
    }

    @Test
    public void onDestroy() {
        PowerMockito.suppress(MemberMatcher.method(AppCompatActivity.class, "onDestroy"));
        testMainActivity.onDestroy();
        verify(testMainActivity.presenter).destroy();
    }

    class TestActivity extends MainActivity {
       public boolean isFinishing;

        @Override
        public boolean isFinishing() {
            return isFinishing;
        }
    }

}