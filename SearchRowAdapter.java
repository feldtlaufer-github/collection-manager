package reiokyu.muhich.mymangacollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchRowAdapter extends BaseAdapter {
    private String[] data;
    private static LayoutInflater inflater = null;

    SearchRowAdapter(Context context, String[] data){
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.row_search_item, null);
        }
        TextView attrName = vi.findViewById(R.id.attributeName);
        attrName.setText(data[position]);
        return vi;
    }
}
