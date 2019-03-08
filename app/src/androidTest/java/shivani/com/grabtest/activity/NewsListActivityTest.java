package shivani.com.grabtest.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import shivani.com.grabtest.GrabApplication;
import shivani.com.grabtest.R;
import shivani.com.grabtest.di.AppComponent;
import shivani.com.grabtest.di.DaggerAppComponent;
import shivani.com.grabtest.util.AppUtil;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Shivani on 08/03/19.
 */

@RunWith(AndroidJUnit4.class)
public class NewsListActivityTest {

    @Rule
    public ActivityTestRule<NewsListActivity> activityTestRule = new ActivityTestRule<>(NewsListActivity.class);
    private NewsListActivity newsListActivity = null;
    private Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(NewsDetailActivity.class.getName(), null, false);
    private IdlingResource resource;

    @Before
    public void setUp() throws Exception {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Context app = instrumentation.getTargetContext().getApplicationContext();
        AppComponent appComponent = DaggerAppComponent.builder().context(app)
                .appUtil(new AppUtil(app))
                .build();
        appComponent.inject((GrabApplication) app);
        newsListActivity = activityTestRule.getActivity();
        resource = OkHttp3IdlingResource.create("OkHttp", appComponent.getOkhttpClient());
        Espresso.registerIdlingResources(resource);
    }

    @Test
    public void testLaunch() {

        getInstrumentation().waitForIdleSync();

        final int[] numberOfAdapterItems = new int[1];
        onView(withId(R.id.recycler_view)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View view) {
                RecyclerView rv = (RecyclerView) view;
                numberOfAdapterItems[0] = rv.getAdapter().getItemCount();
                assertThat(numberOfAdapterItems[0] > 0).isTrue();
                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        }));

        onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.parent_layout)));

        Activity newsDetailActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);

        assertNotNull(newsDetailActivity);

    }

    @After
    public void tearDown() throws Exception {
        newsListActivity = null;
        Espresso.unregisterIdlingResources(resource);
        resource = null;
    }

    public static class MyViewAction {

        static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

}