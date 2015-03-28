package fi.zah.ollpu.materialtempo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by ollpu on 24.3.2015.
 */
public class TabTap extends Fragment {



    Context context;

    long[] taps;
    int pointer = 0;
    TextView display;
    TextView avg_display;
    CheckBox favBtn;
    TextView favName;

    ProgressBar progressBar;

    float currentBPM;
    float lastBPM = 0;

    float inconsistentLastBPM = 0;

    BPMInfo bpmInfo;

    CountDownTimer inactivityTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_tap, container, false);
    }

    public void onActivityCreated(Bundle stuff) {
        super.onActivityCreated(stuff);


        display = (TextView) getView().findViewById(R.id.display);
        avg_display = (TextView) getView().findViewById(R.id.avg_display);
        progressBar = (ProgressBar) getView().findViewById(R.id.tap_progress);
        favBtn = (CheckBox) getView().findViewById(R.id.favorite);
        favName = (TextView) getView().findViewById(R.id.favName);
        taps = new long[Constants.TAP_CYCLE];

        progressBar.setMax(Constants.TAP_CYCLE * 100);


        inactivityTimer = new CountDownTimer(2000, 2000) {

            public void onTick(long remaining) {
                //Not used
            }

            public void onFinish() {
                pointer = 0;
                setProgressBar(0);
                inconsistentLastBPM = 0;
            }

        };


        bpmInfo = new BPMInfo(
                (TextView) getView().findViewById(R.id.info_title),
                (TextView) getView().findViewById(R.id.info),
                this
        );


        favBtn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               favBtnPressed();
           }
        });

        final Button button = (Button) getView().findViewById(R.id.tap);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateBPM();
            }
        });

        System.out.println("Pointer = " + pointer);
    }




    public void favBtnPressed() {
        if(lastBPM != 0) {
            if (favBtn.isChecked()) {
                Favorites favFragment = getFavFragment();
                if (favFragment != null) favFragment.setNewFavorite(lastBPM);
            } else {
                //isn't checked
                Favorites favFragment = getFavFragment();
                if (favFragment != null) favFragment.removeFavorite(lastBPM);
            }
        } else favBtn.setChecked(false);
        updateFavName(getFavFragment().isFavorite(lastBPM));
    }

    private Favorites getFavFragment() {
        ViewPagerAdapter adapter = (ViewPagerAdapter)
                ((ViewPager) getActivity().findViewById(R.id.pager)).getAdapter();
        Favorites favFragment = null;
        if(adapter != null) favFragment = (Favorites) adapter.getRegisteredFragment(Constants.FAV_TAB_LOCATION);
        return favFragment;
    }

    public void onDestroyView() {
        super.onDestroyView();
        display = null;
        avg_display = null;
        progressBar = null;
        favBtn = null;
        favName = null;
    }

    public void updateBPM() {
        setProgressBar(pointer +1);

        taps[pointer] = SystemClock.uptimeMillis();
        calculateBPM();
        pointer++;
        if(pointer >= Constants.TAP_CYCLE) {
            pointer = 0;
            calculateAverageBPM();
        }
        inactivityTimer.cancel();
        inactivityTimer.start();

    }

    private void setProgressBar(int value) {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", value*100);
        animation.setDuration(200);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void calculateBPM() {
        if(pointer >= 1) {
            long totalDiff = 0;
            for (int i = 1; i <= pointer; i++) {
                totalDiff += taps[i] - taps[i - 1];
            }
            long avgDiff = totalDiff / pointer;
            currentBPM = 60 / ((float) avgDiff / 1000);
            publishBPM();
        }
    }

    private void calculateAverageBPM() {
        if(inconsistentLastBPM == 0) inconsistentLastBPM = currentBPM;
        inconsistentLastBPM = (currentBPM + inconsistentLastBPM) / 2;
        publishLastBPM();
    }

    private void publishBPM() {
        Resources res = getView().getResources();
        display.setText(String.format("%.1f", currentBPM) + res.getText(R.string._bpm));
    }

    private void publishLastBPM() {
        Resources res = getView().getResources();
        avg_display.setText(
                res.getText(R.string.average_)
                        + String.format("%.1f", inconsistentLastBPM)
                        + res.getText(R.string._bpm)
        );
        bpmInfo.updateBPM(inconsistentLastBPM);

        lastBPM = inconsistentLastBPM;


        String curFavName = getFavFragment().isFavorite(lastBPM);
        favBtn.setChecked(curFavName != null);
        updateFavName(curFavName);
    }

    private void updateFavName(String curFavName) {
        Resources res = getView().getResources();
        if(curFavName != null) favName.setText(res.getText(R.string.favs_name) + curFavName);
            else favName.setText("");
    }
}
