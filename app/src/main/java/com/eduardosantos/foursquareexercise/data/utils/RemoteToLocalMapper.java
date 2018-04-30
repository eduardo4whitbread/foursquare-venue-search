package com.eduardosantos.foursquareexercise.data.utils;

import com.eduardosantos.foursquareexercise.data.model.local.Venue;
import com.eduardosantos.foursquareexercise.data.model.remote.RestResponseVenue;

import org.apache.commons.lang3.StringUtils;

import io.realm.RealmList;

public final class RemoteToLocalMapper {

    private RemoteToLocalMapper() {
        //no-op
    }

    public static Venue from(RestResponseVenue venue, String searchText) {
        RealmList<String> categoryIconsUrls = new RealmList<String>();
        for (int j = 0; j < venue.getCategories().length; j++) {
            String iconUrl =
                    new StringBuffer(venue.getCategories()[j].getIcon().getPrefix()).
                            append("64").
                            append(venue.getCategories()[j].getIcon().getSuffix()).
                            toString();
            categoryIconsUrls.add(iconUrl);
        }

        StringBuffer addressStringBuffer = new StringBuffer();
        if (StringUtils.isNotEmpty(venue.getLocation().getAddress())) {
            addressStringBuffer.append(venue.getLocation().getAddress());
        }

        if (StringUtils.isNotEmpty(venue.getLocation().getPostalCode())) {
            if (StringUtils.isNotEmpty(addressStringBuffer.toString())) {
                addressStringBuffer.append(", ");
            }

            addressStringBuffer.append(venue.getLocation().getPostalCode());
        }

        if (StringUtils.isNotEmpty(venue.getLocation().getCity())) {
            if (StringUtils.isNotEmpty(addressStringBuffer.toString())) {
                addressStringBuffer.append(", ");
            }

            addressStringBuffer.append(venue.getLocation().getCity());
        }

        return new Venue(
                venue.getId(),
                searchText,
                venue.getName(),
                addressStringBuffer.toString(),
                categoryIconsUrls);
    }

}
