package fi.zah.ollpu.materialtempo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by ollpu on 27.3.2015.
 * Adapter for Favorites, extends ArrayAdapter
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
            Button edit = (Button) v.findViewById(R.id.buttonEdit);

            if (title != null) {
                title.setText(p.getName());
            }

            if (desc != null) {
                desc.setText(p.getDescription());
            }

            if (edit != null) {
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FavoriteBPM item = getItem(position);
                        item.edit();
                    }
                });
            }

            if (delete != null) {
                delete.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          FavoriteBPM item = getItem(position);
                          removeFromSharedPref(item.name);
                          getFavFragment().loadFavorites();
                      }
                    }
                );
            }


        }

        return v;

    }


    private Favorites getFavFragment() {
        ViewPagerAdapter adapter = (ViewPagerAdapter)
                ((ViewPager) ((Activity) getContext()).findViewById(R.id.pager)).getAdapter();
        Favorites favFragment = null;
        if(adapter != null) favFragment = (Favorites) adapter.getRegisteredFragment(Constants.FAV_TAB_LOCATION);
        return favFragment;
    }

    private void removeFromSharedPref(String key) {
        SharedPreferences.Editor editor = favoritesHook.edit();
        editor.remove(key);
        editor.apply();
    }

}
