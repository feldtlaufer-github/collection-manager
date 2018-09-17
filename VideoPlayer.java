package reiokyu.muhich.mymangacollection;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Arrays;

public class VideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        youTubeView = findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, this);

        ListView lvFound = findViewById(R.id.lvFoundList);
        final Beer beer = getIntent().getParcelableExtra("beer");
        //final Manga manga = getIntent().getParcelableExtra("manga");

        videoId = beer.getVideoId();
        //videoId = manga.getYoutube();

        String _locations = "Click here to open Google Maps";
        if(beer.getLocations().size() == 0) _locations = "No locations found";
        //if(manga.getLocation().size() == 0) _locations = "No locations found";
       String[] attrDataFromScan = new String[]{
                beer.getName(),
                beer.getManufacturer(),
                beer.getBarcode(),
                beer.getHops(),
                beer.getMalts(),
                beer.getYeasts(),
                beer.getFlavorings(),
                beer.getBarrelAging(),
                beer.getVideoId(),
                _locations,
                String.valueOf(beer.getOrigGrav()),
                String.valueOf(beer.getFinalGrav()),
                String.valueOf(beer.getAbv()),
                String.valueOf(beer.getIbu()),
                String.valueOf(beer.getSrm()),
                beer.getDryHop(),
                beer.getWetHop(),
                beer.getBottleCond(),
                beer.getNitro()
        };
       ArrayList<String> attrDataFromScan1 = new ArrayList<>();
       attrDataFromScan1.addAll(Arrays.asList(attrDataFromScan));
       attrDataFromScan1.addAll(9, beer.getWebsites());
       final String[] attrDataFromScan0 = attrDataFromScan1.toArray(new String[1]);
        /*
        final String[] attrDataFromScan = new String[]{
                manga.getBarcode(),
                manga.getTitle(),
                manga.getDescription(),
                manga.getOwnership(),
                manga.getYoutube(),
                _locations
        };
        */
        String[] titleList = new String[]{
                "Name: ",
                "Manufacturer: ",
                "Barcode: ",
                "Hops: ",
                "Malts: ",
                "Yeasts: ",
                "Flavorings: ",
                "BarrelAging: ",
                "VideoId: ",
                "Websites: ",
                "Locations: ",
                "Original Gravity: ",
                "Final Gravity: ",
                "ABV: ",
                "IBU: ",
                "SRM: ",
                "Dry Hopping: ",
                "Wet Hopping: ",
                "Bottle Conditioned: ",
                "Nitrogen: "
        };

        ArrayList<String> titleList1 = new ArrayList<>();
        titleList1.addAll(Arrays.asList(titleList));
        ArrayList<String> titleListEmptySpaces = new ArrayList<>();
        for(int i = 0; i < beer.getWebsites().size()-1; i++){
            titleListEmptySpaces.add("                   ");
        }
        titleList1.addAll(10, titleListEmptySpaces);
        String[] titleList0 = titleList1.toArray(new String[1]);
        lvFound.setAdapter(new FoundRowAdapter(this, titleList0,
                attrDataFromScan0));
        /*
        lvFound.setAdapter(new FoundRowAdapter(this,
                new String[]{
                        "Barcode: ",
                        "Title: ",
                        "Description: ",
                        "Ownership: ",
                        "Youtube: ",
                        "Location: "
                },
                attrDataFromScan));
                */
        //long press to copy info to clipboard
        lvFound.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", attrDataFromScan0[position]);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                }
                Toast.makeText(getBaseContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        //click on youtube link to go to website, or location to google maps
        lvFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position >= 9 && position <= (8+beer.getWebsites().size())){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attrDataFromScan0[position]));
                    startActivity(browserIntent);
                }else if(position == (9+beer.getWebsites().size())){
                    Intent intent = new Intent(VideoPlayer.this, MapsActivity.class);
                    intent.putExtra("locationList", beer.getLocations());
                    startActivity(intent);
                }
                    /*
                    case 4:
                        //currently youtube link, but will be various web links related to the item
                        if(!attrDataFromScan[4].equals("")) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(attrDataFromScan[4]));
                            startActivity(browserIntent);
                        }
                        break;
                    case 5:
                        //location
                        if(!attrDataFromScan[5].equals("No locations found")) {
                            Intent intent = new Intent(VideoPlayer.this, MapsActivity.class);
                            intent.putExtra("locationList", manga.getLocation());
                            startActivity(intent);
                        }
                        break;
                        */
            }
        });
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            //takes string in form of https://www.youtube.com/watch?v=KnmQdHSRQ1U
            //and takes KnmQdHSRQ1U part which should be always after the first '='
            player.cueVideo(videoId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}