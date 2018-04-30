package com.eduardosantos.foursquareexercise;

import android.view.View;

import com.eduardosantos.foursquareexercise.base.BaseRobolectricTest;
import com.eduardosantos.foursquareexercise.data.local.DatabaseManager;
import com.eduardosantos.foursquareexercise.venuelist.VenueListActivity;

import org.easymock.Mock;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;

public class VenueListTest extends BaseRobolectricTest {

    private VenueListActivity activity;
    private ShadowActivity shadowActivity;

    @Mock
    DatabaseManager databaseManager;

    @Test
    public void venueList_whenStarted_showsSearchPlaceholder() {
        startActivity();

        assertEquals(View.VISIBLE, activity.placeholderText.getVisibility());
        assertEquals(View.GONE, activity.list.getVisibility());
        assertEquals(View.GONE, activity.progressBar.getVisibility());
    }


    private void startActivity() {
        activity = Robolectric.buildActivity(VenueListActivity.class)
                .create()
                .resume()
                .visible()
                .get();

        shadowActivity = Shadows.shadowOf(activity);
    }

}
