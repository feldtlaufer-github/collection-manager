package reiokyu.muhich.mymangacollection;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;


public class FragSettings extends Fragment {
    public static boolean sortByTitle = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.frag_settings, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        ToggleButton tb = getView().findViewById(R.id.tbSortBy);
        tb.setChecked(sortByTitle);
        getActivity().invalidateOptionsMenu();
    }
    @Override
    public String toString(){
        return "SETTINGS_FRAGMENT";
    }
}
