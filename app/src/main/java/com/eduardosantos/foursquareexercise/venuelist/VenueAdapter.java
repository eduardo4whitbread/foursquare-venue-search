package com.eduardosantos.foursquareexercise.venuelist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduardosantos.foursquareexercise.R;
import com.eduardosantos.foursquareexercise.data.model.local.Venue;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class VenueAdapter extends RealmRecyclerViewAdapter<Venue, VenueAdapter.LogEntryViewHolder> {


    public VenueAdapter(@Nullable OrderedRealmCollection<Venue> data, boolean autoUpdate, boolean updateOnModification) {
        super(data, autoUpdate, updateOnModification);
    }

    @NonNull
    @Override
    public LogEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View content = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venue, parent, false);
        return new LogEntryViewHolder(content);
    }

    @Override
    public void onBindViewHolder(@NonNull LogEntryViewHolder holder, int position) {
        Venue searchResult = getItem(position);
        holder.bind(searchResult);
    }

    public class LogEntryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView nameTextView;
        @BindView(R.id.address)
        TextView addressTextView;
        @BindView(R.id.icons)
        LinearLayout iconsContainer;


        public LogEntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Venue searchResult) {

            if (StringUtils.isNotEmpty(searchResult.getAddress())) {
                itemView.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("google.navigation:q=" + searchResult.getAddress()));
                        itemView.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //todo could suggest user to install maps
                    }
                });
            } else {
                itemView.setOnClickListener(null);
            }

            nameTextView.setText(searchResult.getName());
            addressTextView.setText(searchResult.getAddress());

            iconsContainer.removeAllViews();

            for (String iconUrl : searchResult.getCategoryIcons()) {
                ImageView imageView = new ImageView(itemView.getContext());

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(lp);

                iconsContainer.addView(imageView);

                Picasso.with(itemView.getContext()).
                        load(iconUrl).
                        noPlaceholder().
                        into(imageView);

                imageView.setColorFilter(R.color.colorPrimary, android.graphics.PorterDuff.Mode.MULTIPLY);
            }

        }
    }
}
