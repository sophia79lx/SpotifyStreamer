package com.example.sophialu.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sophia.lu on 7/11/15.
 */
public class ArtistsListData implements Parcelable {
    String artistName;
    String artistId;
    String artistImage;
    String country;

    public ArtistsListData(String aImage, String aName, String aId, String aCountry) {
        this.artistImage = aImage;
        this.artistName = aName;
        this.artistId = aId;
        this.country = aCountry;
    }

    private ArtistsListData(Parcel in){
        artistImage = in.readString();
        artistName = in.readString();
        artistId = in.readString();
        country = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistImage);
        dest.writeString(artistName);
        dest.writeString(artistId);
    }

    public final Parcelable.Creator<ArtistsListData> CREATOR = new Parcelable.Creator<ArtistsListData>() {
        @Override
        public ArtistsListData createFromParcel(Parcel parcel) {
            return new ArtistsListData(parcel);
        }

        @Override
        public ArtistsListData[] newArray(int i) {
            return new ArtistsListData[i];
        }

    };
}
