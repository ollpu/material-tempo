package fi.zah.ollpu.materialtempo;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by ollpu on 24.3.2015.
 */
public class TabTap extends Fragment {

    final static int TAP_CYCLE = 8;

    Context context;
    MainActivity activity;

    long[] taps;
    int pointer = 0;
    TextView display;
    TextView avg_display;

    ProgressBar progressBar;

    float currentBPM;
    float lastBPM = 0;

    BPMInfo bpmInfo;

    CountDownTimer inactivityTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_tap,container,false);
    }

    public void onActivityCreated(Bundle stuff) {
        super.onActivityCreated(stuff);


        display = (TextView) getView().findViewById(R.id.display);
        avg_display = (TextView) getView().findViewById(R.id.avg_display);
        progressBar = (ProgressBar) getView().findViewById(R.id.tap_progress);
        taps = new long[TAP_CYCLE];


        inactivityTimer = new CountDownTimer(2000, 2000) {

            public void onTick(long remaining) {
                //Not used
            }

            public void onFinish() {
                pointer = 0;
                setProgressBar(0);
                lastBPM = 0;
            }

        };


        bpmInfo = new BPMInfo(
                (TextView) getView().findViewById(R.id.info_title),
                (TextView) getView().findViewById(R.id.info),
                this
        );

        final Button button = (Button) getView().findViewById(R.id.tap);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateBPM();
            }
        });

        System.out.println("Pointer = " + pointer);
    }

    public void onDestroyView() {
        super.onDestroyView();
        display = null;
        avg_display = null;
        progressBar = null;
    }

    public void updateBPM() {
        setProgressBar(pointer +1);

        taps[pointer] = SystemClock.uptimeMillis();
        calculateBPM();
        pointer++;
        if(pointer >= TAP_CYCLE) {
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
        if(lastBPM == 0) lastBPM = currentBPM;
        lastBPM = (currentBPM + lastBPM) / 2;
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
                        + String.format("%.1f", lastBPM)
                        + res.getText(R.string._bpm)
        );
        bpmInfo.updateBPM(lastBPM);

    }
}
