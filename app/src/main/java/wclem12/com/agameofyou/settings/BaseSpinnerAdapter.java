package wclem12.com.agameofyou.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class BaseSpinnerAdapter extends ArrayAdapter<String>{
    protected final Context context;
    protected final int txtViewResourceId;
    protected final String[] objects;
    protected final LayoutInflater inflater;

    public BaseSpinnerAdapter(Context context, int txtViewResourceId, String[] objects) {
        super(context, txtViewResourceId, objects);

        this.context = context;
        this.txtViewResourceId = txtViewResourceId;
        this.objects = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = inflater.inflate(txtViewResourceId, parent, false);
        }

        return view;
    }
}
