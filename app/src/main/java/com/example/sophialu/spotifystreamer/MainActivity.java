package com.example.sophialu.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;



public class MainActivity extends ActionBarActivity {

    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        ArtistAdapter adapter;
        ListView listViewArtists;
        ArrayList<ArtistsListData> arrayArtists;

        public PlaceholderFragment() {
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            updateArtistData("Beyonce");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //Load and control artist list
            arrayArtists = new ArrayList<ArtistsListData>();
            listViewArtists = (ListView) rootView.findViewById(R.id.listview_artists);

            // Pass results to ArtistAdapter Class
            adapter = new ArtistAdapter(getActivity(), arrayArtists);

            // Binds the Adapter to the ListView
            listViewArtists.setAdapter(adapter);

            //Search Artists
            final EditText searchArtist = (EditText) rootView.findViewById(R.id.search_artist);
            searchArtist.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = searchArtist.getText().toString().toLowerCase(Locale.getDefault());
                    if (text != null && !text.isEmpty()) {
                        updateArtistData(text);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            searchArtist.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {


                    switch(keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            int i = listViewArtists.getAdapter().getCount();
                            if (i < 1) {
                                String notFound = "No artist found. Try a different name.";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(getActivity(), notFound, duration);
                                toast.show();
                            }
                        return true;
                    }
                    return false;
                }
            });


            listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(parent.getContext(), DetailActivity.class);
                    intent.putExtra("artistId", arrayArtists.get(position).artistId);
                    intent.putExtra("artistName", arrayArtists.get(position).artistName);

                    startActivity(intent);

                }
            });

            return rootView;
        }

        private void updateArtistData(String text) {
            new DownloadDataTask().execute(text);
        }


        private class DownloadDataTask extends AsyncTask<String, Integer, List<Artist>> {
            protected List<Artist> doInBackground(String... params) {

                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                String artistName = params[0];

                ArtistsPager results = spotify.searchArtists(artistName);
                List<Artist> artists = results.artists.items;
                return artists;

            }

            @Override
            protected void onPostExecute(List<Artist> artists) {
                super.onPostExecute(artists);

                if (artists != null) {
                    adapter.clear();

//                    int rightSize = 0;
                    if(artists.size() > 0) {
                        for (int i = 0; i < artists.size(); i++) {
                            if (!artists.get(i).images.isEmpty()) {
//                                for (int i1 = 0; i1 < artists.get(i).images.size(); i1++) {
//                                    if (artists.get(i).images.get(i1).height < 200 &&
//                                            artists.get(i).images.get(i1).width < 200) {
//                                        rightSize = i1 - 1;
//                                    }
//                                }

                                String image = artists.get(i).images.get(1).url;
                                Artist artist = artists.get(i);
                                String artistId = artists.get(i).id;
                                String country = "US";
                                adapter.add(new ArtistsListData(image, artist.name, artistId, country));

                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}