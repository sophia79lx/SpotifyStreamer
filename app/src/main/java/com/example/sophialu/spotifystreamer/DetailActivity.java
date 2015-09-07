package com.example.sophialu.spotifystreamer;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import kaaes.spotify.webapi.android.models.TracksPager;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        ArrayList<TracksListData> arrayTracks;
        ListView listViewTracks;
        ArrayAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            //Load and control artist list
            arrayTracks= new ArrayList<TracksListData>();
            listViewTracks = (ListView) rootView.findViewById(R.id.listview_top_tracks);

            // Pass results to ArtistAdapter Class
            adapter = new TracksAdapter(getActivity(), arrayTracks);

            listViewTracks.setAdapter(adapter);

            String artistId = getActivity().getIntent().getStringExtra("artistId");
            String artistName = getActivity().getIntent().getStringExtra("artistName");

            ((ActionBarActivity)getActivity()).getSupportActionBar().setSubtitle(artistName);

            updateTrackData(artistId);

            return rootView;
        }

        private void updateTrackData(String text) {
            new DownloadDataTask().execute(text);
        }

        private class DownloadDataTask extends AsyncTask<String, Integer, List<Track>> {
            protected List<Track> doInBackground(String... params) {

                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                String artistId = params[0];

                Map<String, Object> map = new HashMap<>();
                map.put("country", "US");

                Tracks results = spotify.getArtistTopTrack(artistId, map);
                List<Track> tracks = results.tracks;

                return tracks;
            }


            @Override
            protected void onPostExecute(List<Track> tracks) {
                super.onPostExecute(tracks);

                if (tracks != null) {
                    adapter.clear();
                }


                if(tracks.size() > 0 && tracks.size() < 10) {
                    for (int i = 0; i < tracks.size(); i++) {
                        Track track = tracks.get(i);
                        String imageUrl = track.album.images.get(1).url;
                        String trackName = track.name;
                        String albumName = track.album.name;
                        String country = "US";
                        adapter.add(new TracksListData(trackName, albumName, imageUrl, country));

                    }

                } else if(tracks.size() > 0) {
                    for (int i = 0; i < 10; i++) {
                        Track track = tracks.get(i);
                        String imageUrl = track.album.images.get(1).url;
                        String trackName = track.name;
                        String albumName = track.album.name;
                        String country = "US";
                        adapter.add(new TracksListData(trackName, albumName, imageUrl, country));
                    }
                }
                adapter.notifyDataSetChanged();
            }

        }

    }
}
