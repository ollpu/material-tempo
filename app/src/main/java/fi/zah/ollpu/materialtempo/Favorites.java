package fi.zah.ollpu.materialtempo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by ollpu on 24.3.2015.
 */
public class Favorites extends ListFragment {


    SharedPreferences favoritesHook;

    ArrayList<FavoriteBPM> favorites;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favorites, container, false);
    }

    @Override
    public void onActivityCreated(Bundle stuff) {
        super.onActivityCreated(stuff);

        favoritesHook = getActivity().getSharedPreferences(getString(R.string.sharedPref_favs), Context.MODE_PRIVATE);

        favorites = new ArrayList<>();
        loadFavorites();


        FavoritesAdapter adapter = new FavoritesAdapter(getActivity(), R.id.title, favorites);
        setListAdapter(adapter);
    }


    private void loadFavorites() {
        Map<String, ?> favMap = favoritesHook.getAll();

        favorites.clear();

        for(Map.Entry<String, ?> entry : favMap.entrySet()) {
            favorites.add(new FavoriteBPM((Float) entry.getValue(), entry.getKey()));
        }
    }


    public void setNewFavourite(float val) {
        SharedPreferences.Editor favEditor = favoritesHook.edit();
        favEditor.putFloat(getString(R.id.favorite) + " #" + (favoritesHook.getAll().size() + 1), val);
        favEditor.commit();

        loadFavorites();
    }
}

