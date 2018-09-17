package reiokyu.muhich.mymangacollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PickFromRowAdapter extends BaseAdapter {
    private String[] _toStringBeer, _image;
    private static LayoutInflater inflater = null;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    PickFromRowAdapter(Context context, String[] _toStringBeer, String[] _image){
        this._toStringBeer = _toStringBeer;
        this._image = _image;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount(){
        return _toStringBeer.length;
    }

    @Override
    public Object getItem(int position) {
        return _toStringBeer[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null){
            vi = inflater.inflate(R.layout.row_pick_from_item, null);
        }
        ImageView beerPic = vi.findViewById(R.id.pictureHolder);
        StorageReference httpsReference = storage.getReferenceFromUrl(_image[position]);
        Glide.with(parent.getContext())
                .using(new FirebaseImageLoader())
                .load(httpsReference)
                .into(beerPic);
        TextView textView = vi.findViewById(R.id.toStringOfBeer);
        textView.setText(_toStringBeer[position]);
        return vi;
    }
}
