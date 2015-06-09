package com.portfolio.nanodegree.tyler112.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;

public class TrackAdapter extends ArrayAdapter<Track> {

    private static final String LOG_TAG = TrackAdapter.class.getSimpleName();
    private Context context;
    private List<Track> data;
    private LayoutInflater mInflater;

    public TrackAdapter(Context context, int resource, List<Track> data) {
        super(context, resource, data);

        this.context = context;
        this.data = data;
        mInflater = ((Activity)context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrackHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.song_item, null);

            holder = new TrackHolder();
            holder.albumName = (TextView) convertView.findViewById(R.id.item_song_album);
            holder.trackName = (TextView) convertView.findViewById(R.id.item_song_track);
            holder.albumImg = (ImageView) convertView.findViewById(R.id.item_song_image);

            convertView.setTag(holder);
        } else {
            holder = (TrackHolder) convertView.getTag();
        }

        Track currTrack = data.get(position);
        Log.d(LOG_TAG, "Track Name: " + currTrack.name);
        Log.d(LOG_TAG, "Album Name: " + currTrack.album.name);
        holder.trackName.setText(currTrack.name);
        holder.albumName.setText(currTrack.album.name);


        if (!currTrack.album.images.isEmpty()) {
            Picasso.with(context).load(currTrack.album.images.get(0).url).into(holder.albumImg);
        } else {
            // TODO: Load stock image when there isn't one to display.
        }

        return convertView;
    }

    static class TrackHolder {
        ImageView albumImg;
        TextView albumName;
        TextView trackName;
    }
}
