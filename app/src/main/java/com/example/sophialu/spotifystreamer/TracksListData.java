package com.example.sophialu.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by sophia.lu on 7/16/15.
 */
public class TracksListData implements Parcelable {
    String trackName;
    String albumName;
    String albumImage;
    String country;


    public TracksListData(String atrackName, String aalbumName, String aalbumImage, String aCountry) {
        this.trackName = atrackName;
        this.albumName = aalbumName;
        this.albumImage = aalbumImage;
        this.country = aCountry;
    }

    private TracksListData(Parcel in){
        trackName = in.readString();
        albumName = in.readString();
        albumImage = in.readString();
        country = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trackName);
        dest.writeString(albumName);
        dest.writeString(albumImage);
    }
}
