package reiokyu.muhich.mymangacollection;

import android.app.Fragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

public class FragBlank extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.frag_blank, container, false);
    }
    @Override
    public String toString(){
        return "BLANK_FRAGMENT";
    }
}
