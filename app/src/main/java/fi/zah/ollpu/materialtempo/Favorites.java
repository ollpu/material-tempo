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

        String[] values = new String[] { "Message1", "Message2", "Message3" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.favorite_entry, R.id.listText, values);
        setListAdapter(adapter);
        return rootView;
    }
}