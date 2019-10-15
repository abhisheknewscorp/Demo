package com.abhishek.topgithub.activity;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.abhishek.topgithub.MockServerDispatcher;
import com.abhishek.topgithub.R;
import com.abhishek.topgithub.recyclerview.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class,false,false);

    private MockWebServer webServer;
    private MockServerDispatcher.RequestDispatcher requestDispatcher;

    @Before
    public void setup() throws Exception {
        requestDispatcher = new MockServerDispatcher().new RequestDispatcher();
        webServer = new MockWebServer();
        webServer.setDispatcher(requestDispatcher);
        webServer.start(8080);
    }

    @After
    public void tearDown() throws Exception {
        webServer.shutdown();
    }

    @Test
    public void testSuccessCondition() {
        requestDispatcher.setTestResponseCode(200);
        activityRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.repo_list_view)).check(matches((isDisplayed())));
        testDataDisplayedOnRecyclerView();
    }

    public void testDataDisplayedOnRecyclerView() {
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.scrollToPosition(0));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(0, R.id.tv_name))
                .check(matches(withText("Tencent")));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(0, R.id.tv_username))
                .check(matches(withText("Tencent")));
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.scrollToPosition(1));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(1, R.id.tv_name))
                .check(matches(withText("Airbnb")));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(1, R.id.tv_username))
                .check(matches(withText("airbnb")));
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.scrollToPosition(2));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(2, R.id.tv_name))
                .check(matches(withText("Flutter")));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(2, R.id.tv_username))
                .check(matches(withText("flutter")));
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.scrollToPosition(3));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(3, R.id.tv_name))
                .check(matches(withText("Google")));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(3, R.id.tv_username))
                .check(matches(withText("google")));
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.scrollToPosition(4));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(4, R.id.tv_name))
                .check(matches(withText("Facebook")));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(4, R.id.tv_username))
                .check(matches(withText("facebook")));
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.scrollToPosition(5));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(5, R.id.tv_name))
                .check(matches(withText("The Apache Software Foundation")));
        Espresso.onView(TestUtils.withRecyclerView(R.id.repo_list_view)
                .atPositionOnView(5, R.id.tv_username))
                .check(matches(withText("apache")));

        onItemClick();
    }

    public void onItemClick() {
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.scrollToPosition(5));
        Espresso.onView(withId(R.id.repo_list_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));
        Espresso.onView(withId(R.id.tv_repo_desc)).check(matches(withText("Apache Dubbo (incubating) is a high-performance, java based, open source RPC framework.")));
        Espresso.onView(withId(R.id.tv_repo_name)).check(matches(withText("incubator-dubbo")));
        Espresso.onView(withId(R.id.tv_repo_url)).check(matches(withText("https://github.com/apache/incubator-dubbo")));
        Espresso.onView(withId(R.id.tv_detail_url)).check(matches(withText("https://github.com/apache")));
        Espresso.onView(withId(R.id.tv_detail_name)).check(matches(withText("The Apache Software Foundation")));
        Espresso.onView(withId(R.id.tv_detail_username)).check(matches(withText("apache")));
    }

    @Test
    public void pageNotFound() {
        requestDispatcher.setTestResponseCode(404);
        activityRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.tv_error)).check(matches(withText("An Error Occurred While Loading Data!.")));

    }

    @Test
    public void badRequest() {
        requestDispatcher.setTestResponseCode(400);
        activityRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.tv_error)).check(matches(withText("An Error Occurred While Loading Data!.")));
    }

    @Test
    public void serverError() {
        requestDispatcher.setTestResponseCode(500);
        activityRule.launchActivity(new Intent());
        Espresso.onView(withId(R.id.tv_error)).check(matches(withText("An Error Occurred While Loading Data!.")));
    }
  }