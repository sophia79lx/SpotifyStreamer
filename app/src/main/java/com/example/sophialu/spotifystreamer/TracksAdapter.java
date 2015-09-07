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
import java.util.List;

import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by sophia.lu on 7/27/15.
 */
public class TracksAdapter extends ArrayAdapter<TracksListData>{

    private static class ViewHolder {
        ImageView trackImgView;
        TextView albumNameView;
        TextView trackNameView;
    }

    public TracksAdapter(Context context, ArrayList<TracksListData> objects) {
        super(context, 0, objects);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        TracksListData track = getItem(position);

        ViewHolder viewHolder =  new ViewHolder();

        LayoutInflater inflater = LayoutInflater.from(getContext());

        convertView = inflater.inflate(R.layout.list_item_track, parent, false);

        viewHolder.albumNameView = (TextView) convertView.findViewById(R.id.album_name);
        viewHolder.trackNameView = (TextView) convertView.findViewById(R.id.track_name);
        viewHolder.trackImgView = (ImageView) convertView.findViewById(R.id.album_image);

        convertView.setTag(viewHolder);

        viewHolder.trackNameView.setText(track.trackName);
        viewHolder.albumNameView.setText(track.albumName);

        Picasso.with(getContext())
                .load(track.albumImage)
                .into(viewHolder.trackImgView);

        return convertView;
    }
}
