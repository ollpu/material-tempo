package fi.zah.ollpu.materialtempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ollpu on 24.3.2015.
 */
public class Favorites extends ListFragment {


    SharedPreferences favoritesHook;

    FavoritesAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites, container, false);
    }

    @Override
    public void onActivityCreated(Bundle stuff) {
        super.onActivityCreated(stuff);

        favoritesHook = getActivity().getSharedPreferences(getString(R.string.sharedPref_favs), Context.MODE_PRIVATE);


        adapter = new FavoritesAdapter(getActivity(), R.id.title, favoritesHook);

        loadFavorites();

        setListAdapter(adapter);



    }


    private void loadFavorites() {
        ArrayList<FavoriteBPM> favorites = new ArrayList<>();

        Map<String, ?> favMap = favoritesHook.getAll();

        for(Map.Entry<String, ?> entry : favMap.entrySet()) {
            favorites.add(new FavoriteBPM((Float) entry.getValue(), entry.getKey()));
        }

        adapter.clear();
        adapter.addAll(favorites);

    }


    public void setNewFavorite(float val) {
        SharedPreferences.Editor favEditor = favoritesHook.edit();
        favEditor.putFloat(getResources().getString(R.string.fav) + " #" + (favoritesHook.getAll().size() + 1), val);
        favEditor.commit();

        loadFavorites();
    }

    /**
     * Tells whether val BPM is already a favorite.
     * @param val input BPM
     * @return whether val is already set as favorite.
     */
    public boolean isFavorite(float val) {
        ArrayList<Integer> entries = getSimplifiedArray();
        int simplifiedVal = FavoriteBPM.simplify(val);

        for(Integer simple : entries) {
            if(simplifiedVal == simple) return true;
        }

        return false;
    }


    private ArrayList<Integer> getSimplifiedArray() {
        ArrayList<Integer> toReturn = new ArrayList<>();

        for(int i = 0; i < adapter.getCount(); i++) {
            toReturn.add(adapter.getItem(i).simple);
        }
        return toReturn;
    }





}

