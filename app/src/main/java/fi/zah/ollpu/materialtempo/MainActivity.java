package fi.zah.ollpu.materialtempo;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;


public class MainActivity extends ActionBarActivity {
    long[] taps;
    int pointer = 0;
    TextView display;
    TextView avg_display;

    ProgressBar progressBar;

    float currentBPM;
    float lastBPM = 0;

    BPMInfo bpmInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configure toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        display = (TextView) findViewById(R.id.display);
        avg_display = (TextView) findViewById(R.id.avg_display);
        progressBar = (ProgressBar) findViewById(R.id.tap_progress);
        taps = new long[4];


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
                (TextView) findViewById(R.id.info_title),
                (TextView) findViewById(R.id.info),
                this
        );

    }

    CountDownTimer inactivityTimer;

    public void updateBPM(View view) {
        setProgressBar(pointer +1);

        taps[pointer] = SystemClock.uptimeMillis();
        calculateBPM();
        pointer++;
        if(pointer >= 4) {
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
        Resources res = getResources();
        display.setText(String.format("%.1f", currentBPM) + res.getText(R.string._bpm));
    }

    private void publishLastBPM() {
        Resources res = getResources();
        avg_display.setText(
                res.getText(R.string.average_)
                + String.format("%.1f", lastBPM)
                + res.getText(R.string._bpm)
        );
        bpmInfo.updateBPM(lastBPM);

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
