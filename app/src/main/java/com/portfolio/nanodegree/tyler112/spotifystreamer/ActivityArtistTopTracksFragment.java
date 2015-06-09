package com.portfolio.nanodegree.tyler112.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityArtistTopTracksFragment extends Fragment {

    private static final String LOG_TAG = ActivityArtistTopTracksFragment.class.getSimpleName();
    private Artist artist;
    private TrackAdapter adapter;
    private SpotifyService spotify;
    private String country;

    public ActivityArtistTopTracksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        String artistId = intent.getStringExtra(Intent.EXTRA_TEXT);
        country = "US";

        FetchArtistTask fetchArtistTask = new FetchArtistTask();
        fetchArtistTask.execute(artistId, country);

        adapter = new TrackAdapter(getActivity(), R.layout.song_item, new ArrayList<Track>());

        View rootView = inflater.inflate(R.layout.fragment_artist_top_tracks, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list_top_songs);
        listView.setAdapter(adapter);

        return rootView;
    }

    public class FetchArtistTask extends AsyncTask<String, Void, Tracks> {

        @Override
        protected Tracks doInBackground(String... params) {
            String artistId = params[0];
            String country = params[1];

            SpotifyApi api = new SpotifyApi();
            spotify = api.getService();
            artist = spotify.getArtist(artistId);

            Map<String, Object> options = new HashMap<>();
            options.put("country", country);

            return spotify.getArtistTopTrack(artistId, options);
        }

        @Override
        protected void onPostExecute(Tracks tracks) {
            super.onPostExecute(tracks);
            adapter.clear();
            adapter.addAll(tracks.tracks);
            ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            if (artist != null && artist.name != null) {
                actionBar.setSubtitle(artist.name);
            }
        }
    }
}
