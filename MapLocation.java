package reiokyu.muhich.mymangacollection;

import android.os.Parcel;
import android.os.Parcelable;


public class MapLocation implements Parcelable {
    private double lat, lng;
    private String title;
    public MapLocation(String title, double lat, double lng){
        this.title = title;
        this.lat = lat;
        this.lng = lng;
    }
    public MapLocation(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.title = data[0];
        this.lat = Double.parseDouble(data[1]);
        this.lng = Double.parseDouble(data[2]);
    }
    @Override
    public int describeContents(){
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.title,
                String.valueOf(this.lat),
                String.valueOf(this.lng)});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MapLocation createFromParcel(Parcel in) {
            return new MapLocation(in);
        }

        public MapLocation[] newArray(int size) {
            return new MapLocation[size];
        }
    };

    public double getLat(){ return lat; }
    public double getLng(){ return lng; }
    public String getTitle(){ return title; }
}
