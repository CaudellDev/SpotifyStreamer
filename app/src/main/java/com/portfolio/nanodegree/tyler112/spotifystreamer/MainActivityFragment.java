package com.portfolio.nanodegree.tyler112.spotifystreamer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    private ArtistAdapter adapter;
    private SpotifyService spotify;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_frag_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SpotifyApi api = new SpotifyApi();
        spotify = api.getService();

        adapter = new ArtistAdapter(
                getActivity(),
                R.layout.artist_item,
                new ArrayList<Artist>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView artists = (ListView) rootView.findViewById(R.id.list_artists);
        artists.setAdapter(adapter);
        artists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ActivityArtistTopTracks.class);
                intent.putExtra(Intent.EXTRA_TEXT, adapter.getArtist(position).id);
                startActivity(intent);
            }
        });

        EditText search = (EditText) rootView.findViewById(R.id.search_artist);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchArtist(s.toString());
            }
        });

        return rootView;
    }

    private void searchArtist(String artist) {
        if (artist == null || artist.isEmpty()) {
            // Do something...
            return;
        }

        spotify.searchArtists(artist, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {
                if (artistsPager == null || artistsPager.artists.items.isEmpty()) {
                    Log.e(LOG_TAG, "success; error: Pager is null or List is empty.");
                }

                for (Artist artist : artistsPager.artists.items) {
                    Log.d(LOG_TAG, "success; artist: " + artist.name);
                }

                final List<Artist> items = artistsPager.artists.items;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        adapter.clear();
                        adapter.addAll(items);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_TAG, "failure; error: " + error.getMessage());
            }
        });
    }
}
