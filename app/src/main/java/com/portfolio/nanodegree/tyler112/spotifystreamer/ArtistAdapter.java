package com.portfolio.nanodegree.tyler112.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;
import java.util.zip.Inflater;

import kaaes.spotify.webapi.android.models.Artist;

public class ArtistAdapter extends ArrayAdapter<Artist> {

    private Context context;
    private List<Artist> data;
    private LayoutInflater mInflater;

    public ArtistAdapter(Context context, int resource, List<Artist> data) {
        super(context, resource, data);

        this.context = context;
        this.data = data;
        mInflater = ((Activity)context).getLayoutInflater();
    }

    public Artist getArtist(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.artist_item, null);

            holder = new ArtistHolder();
            holder.artistName = (TextView) convertView.findViewById(R.id.item_artist_text);
            holder.artistImg = (ImageView) convertView.findViewById(R.id.item_artist_image);

            convertView.setTag(holder);
        } else {
            holder = (ArtistHolder) convertView.getTag();
        }

        Artist currArtist = data.get(position);
        holder.artistName.setText(currArtist.name);

        if (!currArtist.images.isEmpty()) {
            Picasso.with(context).load(currArtist.images.get(0).url).into(holder.artistImg);
        } else {
            // TODO: Load stock image when there isn't one to display.
        }

        return convertView;
    }

    static class ArtistHolder {
        ImageView artistImg;
        TextView artistName;
    }
}
