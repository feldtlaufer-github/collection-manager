package reiokyu.muhich.mymangacollection;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FragFound extends Fragment {
    protected ListView lvFoundListFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_found, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //more than one item with the same barcode has been found
        super.onActivityCreated(savedInstanceState);
        lvFoundListFound = getView().findViewById(R.id.lvFoundListFound);
        //grab the list of items
        final ArrayList<Beer> beerList = getArguments().getParcelableArrayList("list");
        //final ArrayList<Manga> mangaList = getArguments().getParcelableArrayList("list");

        //ArrayAdapter<Manga> mangaArrayAdapter = new ArrayAdapter<Manga>(getContext(), android.R.layout.simple_list_item_1, mangaList);
        String[] toStringList = new String[beerList.size()];
        String[] imageList = new String[beerList.size()];
        for(int i = 0; i < beerList.size(); i++){
            toStringList[i] = beerList.get(i).toString();
            imageList[i] = beerList.get(i).getImage();
        }
        lvFoundListFound.setAdapter(new PickFromRowAdapter(getContext(), toStringList, imageList));
        //lvFoundListFound.setAdapter(mangaArrayAdapter);
        lvFoundListFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int position, long l) {
                Beer beer = beerList.get(position);
                //Manga manga = (Manga)adapterView.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), VideoPlayer.class);
                intent.putExtra("beer", beer);
                //intent.putExtra("manga", manga);
                startActivity(intent);
            }
        });

    }
    @Override
    public String toString(){
        return "FOUND_FRAGMENT";
    }
}
