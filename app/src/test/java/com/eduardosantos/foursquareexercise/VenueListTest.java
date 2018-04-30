package com.eduardosantos.foursquareexercise;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduardosantos.foursquareexercise.base.BaseRobolectricTest;
import com.eduardosantos.foursquareexercise.data.local.DatabaseManager;
import com.eduardosantos.foursquareexercise.data.model.local.Venue;
import com.eduardosantos.foursquareexercise.data.remote.NetworkClient;
import com.eduardosantos.foursquareexercise.venuelist.VenueListActivity;

import org.easymock.Mock;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowActivity;

import java.net.HttpURLConnection;
import java.util.Arrays;

import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import toothpick.Toothpick;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class VenueListTest extends BaseRobolectricTest {

    private VenueListActivity activity;
    private ShadowActivity shadowActivity;

    @Mock
    DatabaseManager databaseManager;

    @Mock
    private RealmResults<Venue> venues;

    @Test
    public void venueList_whenStarted_showsSearchPlaceholder() {
        startActivity();

        assertEquals("Search for venues near a location", activity.placeholderText.getText());
        assertEquals(View.VISIBLE, activity.placeholderText.getVisibility());
        assertEquals(View.GONE, activity.list.getVisibility());
        assertEquals(View.GONE, activity.progressBar.getVisibility());
    }


    @Test
    public void venueList_whenHasResultsForSearch_showsList() {
        Toothpick.openScope(FourSquareExerciseApplication.APPLICATION_SCOPE)
                .getInstance(NetworkClient.class)
                .useMockApiWithResponseCode(RuntimeEnvironment.application.getApplicationContext(),
                        HttpURLConnection.HTTP_OK, "50_venues_response.json");

        startActivity();

        activity.searchMenuItem.expandActionView();
        activity.searchView.setQuery("test", true);

        assertEquals(View.VISIBLE, activity.list.getVisibility());
        assertEquals(View.GONE, activity.progressBar.getVisibility());
        assertEquals(View.GONE, activity.placeholderText.getVisibility());
    }

    @Test
    public void venueList_whenHasNoResultsForSearch_showsNoResultPlaceHolder() {
        Toothpick.openScope(FourSquareExerciseApplication.APPLICATION_SCOPE)
                .getInstance(NetworkClient.class)
                .useMockApiWithResponseCode(RuntimeEnvironment.application.getApplicationContext(),
                        HttpURLConnection.HTTP_BAD_REQUEST, "no_venue_response.json");

        startActivity();

        activity.searchMenuItem.expandActionView();
        activity.searchView.setQuery("test", true);

        assertEquals(View.VISIBLE, activity.placeholderText.getVisibility());
        assertEquals("No results for search", activity.placeholderText.getText());
        assertEquals(View.GONE, activity.list.getVisibility());
        assertEquals(View.GONE, activity.progressBar.getVisibility());
    }

    @Test
    public void venueList_whenHasVenues_correctlyPopulatesData() {
        expect(databaseManager.getVenuesFor(anyString())).andReturn(venues);
        databaseManager.saveOrUpdateVenue(anyObject());
        expectLastCall().anyTimes();
        replay(databaseManager);
        venues.addChangeListener(anyObject(OrderedRealmCollectionChangeListener.class));
        expectLastCall();
        expect(venues.isEmpty()).andReturn(false).anyTimes();
        expect(venues.isManaged()).andReturn(true).anyTimes();
        expect(venues.isValid()).andReturn(true).anyTimes();
        expect(venues.size()).andReturn(1).anyTimes();

        RealmList<String> iconsList = new RealmList<>();
        iconsList.add("https://ss3.4sqi.net/img/categories_v2/arts_entertainment/bowling_bg_32.png");
        Venue mockVenue = new Venue("1",
                "test",
                "Whitbread Plc",
                "120 Holborn, London EC1N 2TD",
                iconsList);

        expect(venues.get(0)).andReturn(mockVenue).anyTimes();
        replay(venues);

        Toothpick.openScope(FourSquareExerciseApplication.APPLICATION_SCOPE)
                .getInstance(NetworkClient.class)
                .useMockApiWithResponseCode(RuntimeEnvironment.application.getApplicationContext(),
                        HttpURLConnection.HTTP_OK, "50_venues_response.json");


        startActivity();

        activity.searchMenuItem.expandActionView();
        activity.searchView.setQuery("test", true);

        assertEquals(1, activity.list.getAdapter().getItemCount());

        View itemView =  activity.list.getLayoutManager().findViewByPosition(0);
        assertEquals("Whitbread Plc", ((TextView)itemView.findViewById(R.id.name)).getText());
        assertEquals("120 Holborn, London EC1N 2TD", ((TextView)itemView.findViewById(R.id.address)).getText());
        assertEquals(1, ((LinearLayout)itemView.findViewById(R.id.icons)).getChildCount());

        verify(databaseManager);
        verify(venues);
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
