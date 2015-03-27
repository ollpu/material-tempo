package fi.zah.ollpu.materialtempo;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ollpu on 27.3.2015.
 */
public class FavoritesAdapter extends ArrayAdapter<FavoriteBPM> {

    public FavoritesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FavoritesAdapter(Context context, int resource, List<FavoriteBPM> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.favorite_entry, null);

        }

        FavoriteBPM p = getItem(position);

        if (p != null) {

            TextView title = (TextView) v.findViewById(R.id.title);
            TextView desc = (TextView) v.findViewById(R.id.description);

            if (title != null) {
                title.setText(p.getName());
            }

            if (desc != null) {
                desc.setText(p.getDescription());
            }
        }

        return v;

    }

}
