package reiokyu.muhich.mymangacollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class FoundRowAdapter extends BaseAdapter {
    private String[] data1, data2;
    private static LayoutInflater inflater = null;

    FoundRowAdapter(Context context, String[] data1, String[] data2){
        this.data1 = data1;
        this.data2 = data2;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return data1.length;
    }

    @Override
    public Object getItem(int position) {
        return data1[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null){
            vi = inflater.inflate(R.layout.row_found_item, null);
        }
        TextView attrName = vi.findViewById(R.id.attributeName);
        attrName.setText(data1[position]);
        TextView attrData = vi.findViewById(R.id.attributeData);
        attrData.setText(data2[position]);
        return vi;
    }
}
