package fi.zah.ollpu.materialtempo;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by ollpu on 24.3.2015.
 */
public class Favorites extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorites, container, false);

        String[] values = new String[] { "Favorite #1", "Favorite #2", "Favorite #3" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.favorite_entry, R.id.listText, values);
        setListAdapter(adapter);
        return rootView;
    }
}