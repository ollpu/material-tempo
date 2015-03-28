package fi.zah.ollpu.materialtempo;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ollpu on 27.3.2015.
 */
public class FavoritesAdapter extends ArrayAdapter<FavoriteBPM> {

    SharedPreferences favoritesHook;

    public FavoritesAdapter(Context context, int textViewResourceId, SharedPreferences data) {
        super(context, textViewResourceId);
        favoritesHook = data;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

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
            Button delete = (Button) v.findViewById(R.id.buttonDelete);

            if (title != null) {
                title.setText(p.getName());
            }

            if (desc != null) {
                desc.setText(p.getDescription());
            }

            if (delete != null) {
                delete.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          FavoriteBPM item = getItem(position);
                          remove(item);
                          removeFromSharedPref(item.name);
                      }
                    }
                );
            }


        }

        return v;

    }


    private void removeFromSharedPref(String key) {
        SharedPreferences.Editor editor = favoritesHook.edit();
        editor.remove(key);
        editor.apply();
    }

}
