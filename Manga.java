package reiokyu.muhich.mymangacollection;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Manga implements Parcelable{
    private String barcode, description, title, ownership, youtube;
    private ArrayList<MapLocation> locations;
    Manga(String barcode, String description, ArrayList<MapLocation> locations, String title, String ownership, String youtube){
        this.barcode = barcode;
        this.description = description;
        this.locations = locations;
        this.title = title;
        this.ownership = ownership;
        this.youtube = youtube;
    }
    public String getBarcode() {
        return barcode;
    }
    String getDescription() {
        return description;
    }
    ArrayList<MapLocation> getLocation() {
        return locations;
    }
    public String getTitle() {
        return title;
    }
    String getOwnership() {
        return ownership;
    }
    String getYoutube() {
        return youtube;
    }
    public String toString(){
        return "Barcode: " + barcode + " \n| Title: " + title + " \n| Description: " + description +
                " \n| Own: " + ownership;
    }

    private Manga(Parcel in){
        super();
        readFromParcel(in);
    }
    public static final Parcelable.Creator<Manga> CREATOR = new Parcelable.Creator<Manga>() {
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };
    private void readFromParcel(Parcel in) {
        barcode = in.readString();
        description = in.readString();
        locations = new ArrayList<>();
        in.readTypedList(locations, MapLocation.CREATOR);
        title = in.readString();
        ownership = in.readString();
        youtube = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(barcode);
        parcel.writeString(description);
        parcel.writeTypedList(locations);
        parcel.writeString(title);
        parcel.writeString(ownership);
        parcel.writeString(youtube);

    }
}
