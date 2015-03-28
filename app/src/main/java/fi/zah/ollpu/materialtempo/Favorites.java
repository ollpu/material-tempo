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

    TabTap tapFragment;
    public void setTapFragment(TabTap newFragment) {
        tapFragment = newFragment;
    }


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
        ArrayList<FavoriteBPM> favorites;
        favorites = new ArrayList<>();

        Map<String, ?> favMap = favoritesHook.getAll();

        for(Map.Entry<String, ?> entry : favMap.entrySet()) {
            favorites.add(new FavoriteBPM((Float) entry.getValue(), entry.getKey()));
        }

        adapter.clear();
        adapter.addAll(favorites);

    }


    public void setNewFavourite(float val) {
        SharedPreferences.Editor favEditor = favoritesHook.edit();
        favEditor.putFloat(getResources().getString(R.string.fav) + " #" + (favoritesHook.getAll().size() + 1), val);
        favEditor.commit();

        loadFavorites();
    }


    public void removeFavorite(View view) {


    }
}

