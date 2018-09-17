package reiokyu.muhich.mymangacollection;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Beer implements Parcelable{
    private String name, barcode, manufacturer, hops, malts, yeasts,
            flavorings, barrelAging, videoId, dryHop, wetHop, bottleCond, nitro,
            image;
    private ArrayList<MapLocation> locations;
    private ArrayList<String> websites;
    private double origGrav, finalGrav, abv;
    private int ibu, srm;
    Beer(String name, String manufacturer, String barcode){
        this.name = name;
        this.manufacturer = manufacturer;
        this.barcode = barcode;
        hops = malts = yeasts = flavorings = barrelAging = videoId = dryHop = wetHop = bottleCond = nitro =  null;
        websites = new ArrayList<>();
        locations = new ArrayList<>();
        origGrav = finalGrav = abv = 0.0;
        ibu = srm = 0;
        image = null;
    }
    public String toString(){
        return "Name: " + getName() + ";\nManufacturer: " + getManufacturer();
    }

    private Beer(Parcel in){
        super();
        readFromParcel(in);
    }
    public static final Parcelable.Creator<Beer> CREATOR = new Parcelable.Creator<Beer>() {
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };
    private void readFromParcel(Parcel in) {
        name = in.readString();
        manufacturer = in.readString();
        barcode = in.readString();
        hops = in.readString();
        malts = in.readString();
        yeasts = in.readString();
        flavorings = in.readString();
        barrelAging = in.readString();
        videoId = in.readString();
        websites = new ArrayList<>();
        in.readStringList(websites);
        locations = new ArrayList<>();
        in.readTypedList(locations, MapLocation.CREATOR);
        origGrav = in.readDouble();
        finalGrav = in.readDouble();
        abv = in.readDouble();
        ibu = in.readInt();
        srm = in.readInt();
        dryHop = in.readString();
        wetHop = in.readString();
        bottleCond = in.readString();
        nitro = in.readString();
        image = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(manufacturer);
        parcel.writeString(barcode);
        parcel.writeString(hops);
        parcel.writeString(malts);
        parcel.writeString(yeasts);
        parcel.writeString(flavorings);
        parcel.writeString(barrelAging);
        parcel.writeString(videoId);
        parcel.writeStringList(websites);
        parcel.writeTypedList(locations);
        parcel.writeDouble(origGrav);
        parcel.writeDouble(finalGrav);
        parcel.writeDouble(abv);
        parcel.writeInt(ibu);
        parcel.writeInt(srm);
        parcel.writeString(dryHop);
        parcel.writeString(wetHop);
        parcel.writeString(bottleCond);
        parcel.writeString(nitro);
        parcel.writeString(image);
    }
    public String getImage(){ return image; }
    public void setImage(String image){ this.image = image; }
    public String getBarcode(){ return barcode; }
    public void setBarcode(String barcode){ this.barcode = barcode; }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
    public String getManufacturer(){ return manufacturer; }
    public void setManufacturer(String manufacturer){ this.manufacturer = manufacturer; }
    public String getHops(){ return hops; }
    public void setHops(String hops){ this.hops = hops; }
    public String getMalts(){ return malts; }
    public void setMalts(String malts){ this.malts = malts; }
    public String getYeasts(){ return yeasts; }
    public void setYeasts(String yeasts){ this.yeasts = yeasts; }
    public String getFlavorings(){ return flavorings; }
    public void setFlavorings(String flavorings){ this.flavorings = flavorings; }
    public String getBarrelAging(){ return barrelAging; }
    public void setBarrelAging(String barrelAging){ this.barrelAging = barrelAging; }
    public double getOrigGrav(){ return origGrav; }
    public void setOrigGrav(double origGrav){ this.origGrav = origGrav; }
    public double getFinalGrav(){ return finalGrav; }
    public void setFinalGrav(double finalGrav){ this.finalGrav = finalGrav; }
    public double getAbv(){ return abv; }
    public void setAbv(double abv){ this.abv = abv; }
    public int getIbu(){ return ibu; }
    public void setIbu(int ibu){ this.ibu = ibu; }
    public int getSrm(){ return srm; }
    public void setSrm(int srm){ this.srm = srm; }
    public String getDryHop(){ return dryHop; }
    public void setDryHop(String dryHop){ this.dryHop = dryHop; }
    public String getWetHop(){ return wetHop; }
    public void setWetHop(String wetHop){ this.wetHop = wetHop; }
    public String getBottleCond(){ return bottleCond; }
    public void setBottleCond(String bottleCond){ this.bottleCond = bottleCond; }
    public String getNitro(){ return nitro; }
    public void setNitro(String nitro){ this.nitro = nitro; }
    public ArrayList<String> getWebsites(){ return websites; }
    public void setWebsites(ArrayList<String> websites){ this.websites = websites; }
    public ArrayList<MapLocation> getLocations(){ return locations; }
    public void setLocations(ArrayList<MapLocation> locations){ this.locations = locations; }
    public String getVideoId(){ return videoId; }
    public void setVideoId(String videoId){ this.videoId = videoId; }
}
