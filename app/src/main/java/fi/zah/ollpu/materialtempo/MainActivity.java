package fi.zah.ollpu.materialtempo;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;

import java.util.Objects;


public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[];
    int numTabs;

    private CharSequence[] getTabs() {
        return new CharSequence[] { "Tap Tempo", "Metronome" };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Titles = getTabs();
        numTabs = Titles.length;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, numTabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.textPrimary);
            }
        });

        tabs.setViewPager(pager);




    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch(id) {
            case R.id.action_settings:
                return true;

            case R.id.action_display_italians:
                String BPM = getResources().getText(R.string._bpm).toString();

                BPMInfo.Italian[] italians = BPMInfo.Italian.values();
                String list = "";

                for (BPMInfo.Italian i : italians) {
                    String start, end;
                    if(i.start == Integer.MIN_VALUE) start = "-∞" + BPM;
                    else start = i.start + BPM;

                    if(i.end == Integer.MAX_VALUE) end = "∞" + BPM;
                    else end = i.end + BPM;

                    list = list + "\n" + i.toString() + ": " + start + " — " + end;
                }
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getText(R.string.list_italians))
                        .setMessage(list)
                        .show()
                ;

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }



    }


}
