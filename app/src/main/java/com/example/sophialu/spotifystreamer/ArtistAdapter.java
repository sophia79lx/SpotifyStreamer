package com.example.sophialu.spotifystreamer;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sophia.lu on 7/8/15.
 */
public class ArtistAdapter extends ArrayAdapter<ArtistsListData> {
    public ArtistAdapter(Context context, ArrayList<ArtistsListData> listArtists) {
        super(context, 0, listArtists);

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ArtistsListData listArtists = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.artistNameView  = (TextView) convertView.findViewById(R.id.list_item_artist_textview);
        viewHolder.artistImgView = (ImageView) convertView.findViewById(R.id.list_item_artist_imageview);

        viewHolder.artistNameView.setText(listArtists.artistName);

        Picasso.with(getContext())
                .load(listArtists.artistImage)
                .resize(200, 200)
                .into(viewHolder.artistImgView);

        return convertView;
    }

    private class ViewHolder {
        ImageView artistImgView;
        TextView artistNameView;
    }

}
