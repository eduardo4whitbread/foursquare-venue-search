package com.eduardosantos.foursquareexercise.venuelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eduardosantos.foursquareexercise.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;

public class VenueListActivity extends AppCompatActivity implements VenueListContract.View {

    @BindView(R.id.list)
    public RecyclerView list;
    @BindView(R.id.progressBarPage)
    public ProgressBar progressBarPage;
    @BindView(R.id.progressBarSearch)
    public ProgressBar progressBarSearch;
    @BindView(R.id.placeholderText)
    public TextView placeholderText;
    public SearchView searchView;
    public MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);
        ButterKnife.bind(this);
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                //todo call presenter here
                return false;
            }

        });


        return true;
    }


    @Override
    public void showSearchPlaceHolder() {

    }

    @Override
    public void hideSearchPlaceHolder() {

    }

    @Override
    public void showNoResultsPlaceHolder() {

    }

    @Override
    public void hideNoResultsPlaceHolder() {

    }

    @Override
    public void showVenuesList() {

    }

    @Override
    public void hideVenuesList() {

    }

    @Override
    public void setAdapter(RealmBaseAdapter adapter) {

    }

    @Override
    public void setPresenter(VenueListContract.Presenter presenter) {

    }
}
