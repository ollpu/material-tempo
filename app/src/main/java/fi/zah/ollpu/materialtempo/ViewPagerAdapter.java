package fi.zah.ollpu.materialtempo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;


/**
 * Created by ollpu on 24.3.2015.
 * Originally copied from http://www.android4devs.com/2015/01/how-to-make-material-design-sliding-tabs.html
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOfTabs;

    MainActivity parent;


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, MainActivity parent) {
        super(fm);

        this.parent = parent;

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }




    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new TabTap();
            case 1:
                return new Favorites();
            case 2:
                return new Metronome();

            default:
                return null;
        }



    }



    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }


    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}