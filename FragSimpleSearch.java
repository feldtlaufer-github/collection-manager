package reiokyu.muhich.mymangacollection;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragSimpleSearch extends Fragment{
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.frag_simple_search, container, false);
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
        lvSearch.setAdapter(new SearchRowAdapter(getContext(),
                new String[]{
                    "Barcode",
                    "Name",
                    "Manufacturer"
                }));
        /*
        lvSearch.setAdapter(new SearchRowAdapter(getContext(),
                new String[]{
                    "Barcode",
                    "Title",
                    "Description"
                }));
                */
    }
    @Override
    public String toString(){
        return "SIMPLE_SEARCH_FRAGMENT";
    }
}
