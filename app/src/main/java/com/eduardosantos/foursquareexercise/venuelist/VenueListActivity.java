package com.eduardosantos.foursquareexercise.venuelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eduardosantos.foursquareexercise.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;

public class VenueListActivity extends AppCompatActivity implements VenueListContract.View {

    @BindView(R.id.list)
    public RecyclerView list;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.placeholderText)
    public TextView placeholderText;
    public SearchView searchView;
    public MenuItem searchMenuItem;

    private VenueListContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_list);
        ButterKnife.bind(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        setPresenter(new VenueListPresenter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                showSearchPlaceHolder();
                hideProgressBar();
                hideVenueList();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                presenter.searchFor(query);
                return false;
            }

        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showSearchPlaceHolder() {
        placeholderText.setVisibility(View.VISIBLE);
        placeholderText.setText("Search for venues near a location");
    }

    @Override
    public void showNoResultsPlaceHolder() {
        placeholderText.setVisibility(View.VISIBLE);
        placeholderText.setText("No results for search");
    }

    @Override
    public void hidePlaceHolder() {
        placeholderText.setVisibility(View.GONE);
    }

    @Override
    public void showVenueList() {
        list.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVenueList() {
        list.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setAdapter(RealmRecyclerViewAdapter adapter) {
        list.setAdapter(adapter);
    }

    @Override
    public void setPresenter(VenueListContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
