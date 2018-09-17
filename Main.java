package reiokyu.muhich.mymangacollection;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Main extends AppCompatActivity {
    protected DatabaseReference mRootRef, /*mMangaRef, */mBeerRef;
    protected String[] searchCriteria;
    protected ListView lvSearchList;
    private ProgressDialog mProgress;
    private boolean goingToSettings = false;

    public void onClickScan(View view) {
        startActivityForResult(new Intent(Main.this, ScanBarcodeActivity.class), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Beer App AYU");
        //toolbar.setTitle("My Manga Collection");
        setSupportActionBar(toolbar);

        replaceContainerFragment(new FragMain(), "main");

        mProgress = new ProgressDialog(this);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mBeerRef = mRootRef.child("beer");
        //mMangaRef = mRootRef.child("manga");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initializing views
                lvSearchList = findViewById(R.id.lvSearchList);
                View v;
                EditText et;
                int listLength = lvSearchList.getChildCount();
                searchCriteria = new String[listLength];
                //grab edit text ifnormation
                Boolean allBlank = true;
                for(int i = 0; i < listLength; i++){
                    v = lvSearchList.getChildAt(i);
                    et = v.findViewById(R.id.attributeInput);
                    searchCriteria[i] = et.getText().toString();
                    if(!searchCriteria[i].equals("")) allBlank = false;
                }
                if(!allBlank){
                    //search the database based on the searchCriteria
                    mBeerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mProgress.setMessage("Accessing BeerAppAYU Database...");
                            mProgress.show();

                            ArrayList<Beer> beerList = new ArrayList<>();
                            //dummy default values
                            String criterion = null;
                            String criterionName = null;
                            //determine what we're searching on
                            for (int i = 0; i < searchCriteria.length; i++) {
                                if (!searchCriteria[i].equals("")) {
                                    switch (i) {
                                        case 0:
                                            criterion = searchCriteria[0];
                                            criterionName = "name";
                                            break;
                                        case 1:
                                            criterion = searchCriteria[1];
                                            criterionName = "manufacturer";
                                            break;
                                        case 2:
                                            criterion = searchCriteria[2];
                                            criterionName = "barcode";
                                            break;
                                        case 3:
                                            criterion = searchCriteria[3];
                                            criterionName = "hops";
                                            break;
                                        case 4:
                                            criterion = searchCriteria[4];
                                            criterionName = "malts";
                                            break;
                                        case 5:
                                            criterion = searchCriteria[5];
                                            criterionName = "yeasts";
                                            break;
                                        case 6:
                                            criterion = searchCriteria[6];
                                            criterionName = "flavorings";
                                            break;
                                        case 7:
                                            criterion = searchCriteria[7];
                                            criterionName = "barrelAging";
                                            break;
                                        case 8:
                                            criterion = searchCriteria[8];
                                            criterionName = "videoId";
                                            break;
                                        case 9:
                                            criterion = searchCriteria[9];
                                            criterionName = "websites";
                                            break;
                                        case 10:
                                            criterion = searchCriteria[10];
                                            criterionName = "locations";
                                            break;
                                        case 11:
                                            criterion = searchCriteria[11];
                                            criterionName = "origGrav";
                                            break;
                                        case 12:
                                            criterion = searchCriteria[12];
                                            criterionName = "finalGrav";
                                            break;
                                        case 13:
                                            criterion = searchCriteria[13];
                                            criterionName = "abv";
                                            break;
                                        case 14:
                                            criterion = searchCriteria[14];
                                            criterionName = "ibu";
                                            break;
                                        case 15:
                                            criterion = searchCriteria[15];
                                            criterionName = "srm";
                                            break;
                                        case 16:
                                            criterion = searchCriteria[16];
                                            criterionName = "dryHop";
                                            break;
                                        case 17:
                                            criterion = searchCriteria[17];
                                            criterionName = "wetHop";
                                            break;
                                        case 18:
                                            criterion = searchCriteria[18];
                                            criterionName = "bottleCond";
                                            break;
                                        case 19:
                                            criterion = searchCriteria[19];
                                            criterionName = "nitro";
                                            break;
                                    }
                                    //grab data that matches search and add it to the list
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String lcCriterionName = ds.child(criterionName).getValue(String.class).toLowerCase();
                                        String lcCriterion = criterion.toLowerCase();
                                        if (lcCriterionName.contains(lcCriterion)) {
                                            ArrayList<MapLocation> mapList = new ArrayList<>();
                                            if(ds.child("locations").hasChildren()) {
                                                for (DataSnapshot dx : ds.child("locations").getChildren()) {
                                                    String[] splitData = dx.getValue().toString().split(",");
                                                    mapList.add(new MapLocation(splitData[0], Double.parseDouble(splitData[1]), Double.parseDouble(splitData[2])));
                                                }
                                            }
                                            Beer addedBeer = new Beer(ds.child("name").getValue(String.class),
                                                                        ds.child("manufacturer").getValue(String.class),
                                                                        String.valueOf(ds.child("barcode").getValue()));
                                            addedBeer.setHops(ds.child("hops").getValue(String.class));
                                            addedBeer.setMalts(ds.child("malts").getValue(String.class));
                                            addedBeer.setYeasts(ds.child("yeasts").getValue(String.class));
                                            addedBeer.setFlavorings(ds.child("flavorings").getValue(String.class));
                                            addedBeer.setBarrelAging(ds.child("barrelAging").getValue(String.class));
                                            addedBeer.setVideoId(ds.child("videoId").getValue(String.class));
                                            ArrayList<String> websiteList = new ArrayList<>();
                                            if(ds.child("websites").hasChildren()) {
                                                for (DataSnapshot dx : ds.child("websites").getChildren()) {
                                                    websiteList.add(dx.getValue(String.class));
                                                }
                                            }
                                            addedBeer.setWebsites(websiteList);
                                            addedBeer.setLocations(mapList);
                                            addedBeer.setOrigGrav(ds.child("origGrav").getValue(Double.class));
                                            addedBeer.setFinalGrav(ds.child("finalGrav").getValue(Double.class));
                                            addedBeer.setAbv(ds.child("abv").getValue(Double.class));
                                            addedBeer.setIbu(ds.child("ibu").getValue(Integer.class));
                                            addedBeer.setSrm(ds.child("srm").getValue(Integer.class));
                                            addedBeer.setDryHop(ds.child("dryHop").getValue(String.class));
                                            addedBeer.setWetHop(ds.child("wetHop").getValue(String.class));
                                            addedBeer.setBottleCond(ds.child("bottleCond").getValue(String.class));
                                            addedBeer.setNitro(ds.child("nitro").getValue(String.class));
                                            addedBeer.setImage(ds.child("image").getValue(String.class));
                                            beerList.add(addedBeer);
                                        }
                                    }
                                }
                            }
                            //removes duplicates
                            Set<Beer> hs = new HashSet<>();
                            hs.addAll(beerList);
                            beerList.clear();
                            beerList.addAll(hs);

                            if(beerList.size() > 1){
                                //multiple items were found
                                //sort based on settings SortBy
                                beerSort(beerList);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("list", beerList);
                                FragFound fragFound = new FragFound();
                                fragFound.setArguments(bundle);
                                //mProgress.dismiss();
                                replaceContainerFragment(fragFound, "found");
                            }else if(beerList.size() == 1){
                                //only one was found so go straight to the videoplayer
                                Intent intent = new Intent(Main.this, VideoPlayer.class);
                                intent.putExtra("beer", beerList.get(0));
                                //mProgress.dismiss();
                                startActivity(intent);
                            }else{
                                //none were found
                                //mProgress.dismiss();
                                Toast.makeText(Main.this, "No items found with that search criteria", Toast.LENGTH_SHORT).show();
                            }
                            mProgress.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    /*
                    mMangaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mProgress.setMessage("Accessing MyMangaCollection...");
                            mProgress.show();

                            ArrayList<Manga> mangaList = new ArrayList<>();
                            //dummy default values
                            String criterion = null;
                            String criterionName = null;
                            //determine what we're searching on
                            for (int i = 0; i < searchCriteria.length; i++) {
                                if (!searchCriteria[i].equals("")) {
                                    switch (i) {
                                        case 0:
                                            criterion = searchCriteria[0];
                                            criterionName = "barcode";
                                            break;
                                        case 1:
                                            criterion = searchCriteria[1];
                                            criterionName = "title";
                                            break;
                                        case 2:
                                            criterion = searchCriteria[2];
                                            criterionName = "description";
                                            break;
                                        case 3:
                                            criterion = searchCriteria[3];
                                            criterionName = "ownership";
                                            break;
                                        case 4:
                                            criterion = searchCriteria[4];
                                            criterionName = "youtube";
                                            break;
                                        case 5:
                                            criterion = searchCriteria[5];
                                            criterionName = "location";
                                            break;
                                    }
                                    //grab data that matches search and add it to the list
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String lcCriterionName = ds.child(criterionName).getValue().toString().toLowerCase();
                                        String lcCriterion = criterion.toLowerCase();
                                        if (lcCriterionName.contains(lcCriterion)) {
                                            ArrayList<MapLocation> mapList = new ArrayList<>();
                                            if(ds.child("location").hasChildren()) {
                                                for (DataSnapshot dx : ds.child("location").getChildren()) {
                                                    String[] splitData = dx.getValue().toString().split(",");
                                                    mapList.add(new MapLocation(splitData[0], Double.parseDouble(splitData[1]), Double.parseDouble(splitData[2])));
                                                }
                                            }
                                            mangaList.add(new Manga(
                                                    //barcode normally is a long, so convert to string
                                                    String.valueOf(ds.child("barcode").getValue()),
                                                    ds.child("description").getValue(String.class),
                                                    mapList,
                                                    ds.child("title").getValue(String.class),
                                                    ds.child("ownership").getValue(String.class),
                                                    ds.child("youtube").getValue(String.class)
                                            ));
                                        }
                                    }
                                }
                            }
                            //removes duplicates
                            Set<Manga> hs = new HashSet<>();
                            hs.addAll(mangaList);
                            mangaList.clear();
                            mangaList.addAll(hs);

                            if(mangaList.size() > 1){
                                //multiple manga were found
                                //sort based on settings SortBy
                                mangaSort(mangaList);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("list", mangaList);
                                FragFound fragFound = new FragFound();
                                fragFound.setArguments(bundle);
                                //mProgress.dismiss();
                                replaceContainerFragment(fragFound, "found");
                            }else if(mangaList.size() == 1){
                                //only one was found so go straight to the videoplayer
                                Intent intent = new Intent(Main.this, VideoPlayer.class);
                                intent.putExtra("manga", mangaList.get(0));
                                //mProgress.dismiss();
                                startActivity(intent);
                            }else{
                                //none were found
                                //mProgress.dismiss();
                                Toast.makeText(Main.this, "No items found with that search criteria", Toast.LENGTH_SHORT).show();
                            }
                            mProgress.dismiss();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    */
                }else{
                    Toast.makeText(Main.this, "Please enter something to search based on", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //barcode scanner
        if(requestCode == 0){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    checkDatabaseFromScanner(data.getStringExtra("barcode"));
                }else{
                    if(ActivityCompat.checkSelfPermission(Main.this, android.Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Barcode not detected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void checkDatabaseFromScanner(final String barcode){
        //we have a barcode that was just scanned
        mBeerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Beer> beerList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    /*
                    for each item in the list, check the barcode, if matches add
                    sometimes the same barcode gets used for different objects
                    in that case we are still able to display all appropriate objects
                    and the user can decide what they want to be looking at
                     */
                    if (barcode.equals(ds.child("barcode").getValue(String.class))) {
                        ArrayList<MapLocation> mapList = new ArrayList<>();
                        if(ds.child("locations").hasChildren()) {
                            for (DataSnapshot dx : ds.child("locations").getChildren()) {
                                String[] splitData = dx.getValue(String.class).split(",");
                                mapList.add(new MapLocation(splitData[0], Double.parseDouble(splitData[1]), Double.parseDouble(splitData[2])));
                            }
                        }
                        Beer addedBeer = new Beer(ds.child("name").getValue(String.class),
                                ds.child("manufacturer").getValue(String.class),
                                ds.child("barcode").getValue(String.class));
                        addedBeer.setHops(ds.child("hops").getValue(String.class));
                        addedBeer.setMalts(ds.child("malts").getValue(String.class));
                        addedBeer.setYeasts(ds.child("yeasts").getValue(String.class));
                        addedBeer.setFlavorings(ds.child("flavorings").getValue(String.class));
                        addedBeer.setBarrelAging(ds.child("barrelAging").getValue(String.class));
                        addedBeer.setVideoId(ds.child("videoId").getValue(String.class));
                        ArrayList<String> websiteList = new ArrayList<String>();
                        if(ds.child("websites").hasChildren()) {
                            for (DataSnapshot ds1 : ds.child("websites").getChildren()) {
                                websiteList.add(ds1.getValue().toString());
                            }
                        }
                        addedBeer.setWebsites(websiteList);
                        addedBeer.setLocations(mapList);
                        addedBeer.setOrigGrav(ds.child("origGrav").getValue(Double.class));
                        addedBeer.setFinalGrav(ds.child("finalGrav").getValue(Double.class));
                        addedBeer.setAbv(ds.child("abv").getValue(Double.class));
                        addedBeer.setIbu(ds.child("ibu").getValue(Integer.class));
                        addedBeer.setSrm(ds.child("srm").getValue(Integer.class));
                        addedBeer.setDryHop(ds.child("dryHop").getValue(String.class));
                        addedBeer.setWetHop(ds.child("wetHop").getValue(String.class));
                        addedBeer.setBottleCond(ds.child("bottleCond").getValue(String.class));
                        addedBeer.setNitro(ds.child("nitro").getValue(String.class));
                        addedBeer.setImage(ds.child("image").getValue(String.class));
                        beerList.add(addedBeer);
                    }
                }
                if(beerList.size() > 1){
                    //multiple manga were found
                    //sort list based on settings ByBarcode(not useful), ByTitle(good), maybe by some other method
                    beerSort(beerList);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("list", beerList);
                    FragFound fragFound = new FragFound();
                    fragFound.setArguments(bundle);
                    replaceContainerFragment(fragFound, "found");

                }else if(beerList.size() == 1){
                    //one manga was found
                    Intent intent = new Intent(Main.this, VideoPlayer.class);
                    intent.putExtra("beer", beerList.get(0));
                    startActivity(intent);
                }else{
                    //none were found
                    Toast.makeText(getApplicationContext(), "That barcode is not currently in our database", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*
        mMangaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Manga> mangaList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //for each manga in the list, check the barcode, if matches add
                    //sometimes the same barcode gets used for different objects
                    //in that case we are still able to display all appropriate objects
                    //and the user can decide what they want to be looking at

                    if (barcode.equals(ds.child("barcode").getValue().toString())) {
                        ArrayList<MapLocation> mapList = new ArrayList<>();
                        if(ds.child("location").hasChildren()) {
                            for (DataSnapshot dx : ds.child("location").getChildren()) {
                                String[] splitData = dx.getValue().toString().split(",");
                                mapList.add(new MapLocation(splitData[0], Double.parseDouble(splitData[1]), Double.parseDouble(splitData[2])));
                            }
                        }
                        mangaList.add(new Manga(
                                ds.child("barcode").getValue(String.class),
                                ds.child("description").getValue(String.class),
                                mapList,
                                ds.child("title").getValue(String.class),
                                ds.child("ownership").getValue(String.class),
                                ds.child("youtube").getValue(String.class)
                        ));
                    }
                }
                if(mangaList.size() > 1){
                    //multiple manga were found
                    //sort list based on settings ByBarcode(not useful), ByTitle(good), maybe by some other method
                    mangaSort(mangaList);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("list", mangaList);
                    FragFound fragFound = new FragFound();
                    fragFound.setArguments(bundle);
                    replaceContainerFragment(fragFound, "found");
                }else if(mangaList.size() == 1){
                    //one manga was found
                    Intent intent = new Intent(Main.this, VideoPlayer.class);
                    intent.putExtra("manga", mangaList.get(0));
                    startActivity(intent);
                }else{
                    //none were found
                    Toast.makeText(Main.this, "This barcode does not exist in the database.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Main.this, "Sorry, there was a database issue. Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return !goingToSettings;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main2_drawer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                replaceContainerFragment(new FragSettings(), "settings");
                goingToSettings = true;
                return true;
            case R.id.searchIcon:
                replaceContainerFragment(new FragSearch(), "search");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void replaceContainerFragment(Fragment frag, String name){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, frag, frag.toString());
        ft.addToBackStack(name);
        ft.commit();
    }
    public void onToggle(View v){
        FragSettings.sortByTitle = !FragSettings.sortByTitle;
    }
    private void beerSort(ArrayList<Beer> bList){
        if(FragSettings.sortByTitle){
            //sort by barcode
            Collections.sort(bList, new Comparator<Beer>(){
                @Override
                public int compare(Beer beer, Beer t1){
                    if(beer.getBarcode().compareTo(t1.getBarcode()) > 1){
                        return 1;
                    }else if(beer.getBarcode().compareTo(t1.getBarcode()) < 1){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        }else{
            Collections.sort(bList, new Comparator<Beer>(){
                @Override
                public int compare(Beer beer, Beer t1){
                    if(beer.getName().toLowerCase().compareTo(t1.getName().toLowerCase()) > 0){
                        return 1;
                    }else if(beer.getName().toLowerCase().compareTo(t1.getName().toLowerCase()) < 0){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        }
    }
    /*
    private void mangaSort(ArrayList<Manga> mList){
        if(FragSettings.sortByTitle){
            //sort by barcode
            Collections.sort(mList, new Comparator<Manga>() {
                @Override
                public int compare(Manga manga, Manga t1) {
                    if (manga.getBarcode().compareTo(t1.getBarcode()) > 1) {
                        return 1;
                    } else if (manga.getBarcode().compareTo(t1.getBarcode()) < 1) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }else{
            //sort by title
            Collections.sort(mList, new Comparator<Manga>() {
                @Override
                public int compare(Manga manga, Manga t1) {
                    if(manga.getTitle().toLowerCase().compareTo(t1.getTitle().toLowerCase()) > 0){
                        return 1;
                    }else if(manga.getTitle().toLowerCase().compareTo(t1.getTitle().toLowerCase()) < 0){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        }
    }
    */
    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount()-1).getName().equals("main")){
            this.finish();
            System.exit(0);
        }else{
            super.onBackPressed();
            goingToSettings = false;
            invalidateOptionsMenu();
        }
    }
}
