package com.portfolio.nanodegree.tyler112.spotifystreamer;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import kaaes.spotify.webapi.android.models.Tracks;

public class TrackAdapter extends ArrayAdapter<Tracks> {

    public TrackAdapter(Context context, int resource, List<Tracks> objects) {
        super(context, resource, objects);
    }
}
