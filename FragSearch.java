package reiokyu.muhich.mymangacollection;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragSearch extends Fragment{
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.frag_search, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        fab = getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
    }
    @Override
    public void onPause(){
        super.onPause();
        fab.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lvSearch = getView().findViewById(R.id.lvSearchList);
        lvSearch.setAdapter((new SearchRowAdapter(getContext(),
                new String[]{
                    "Name",
                    "Manufacturer",
                    "Barcode",
                    "Hops",
                    "Malts",
                    "Yeasts",
                    "Flavorings",
                    "BarrelAging",
                    "VideoId",
                    "Websites",
                    "Locations",
                    "Original Gravity",
                    "Final Gravity",
                    "ABV",
                    "IBU",
                    "SRM",
                    "Dry Hopping",
                    "Wet Hopping",
                    "Bottle Conditioned",
                    "Nitrogen"
                })));
        /*
        lvSearch.setAdapter(new SearchRowAdapter(getContext(),
                new String[]{
                    "Barcode",
                    "Title",
                    "Description",
                    "Ownership",
                    "Youtube",
                    "Location"
                }));
                */
    }
    @Override
    public String toString(){
        return "SEARCH_FRAGMENT";
    }
}
