package fi.zah.ollpu.materialtempo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by ollpu on 29.3.2015.
 */
public class Metronome extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.metronome, container, false);
    }

    @Override
    public void onActivityCreated(Bundle stuff) {
        super.onActivityCreated(stuff);
    }

}
