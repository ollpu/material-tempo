package fi.zah.ollpu.materialtempo;

import android.view.View;
import android.widget.TextView;

/**
 * Created by ollpu on 22.3.2015.
 */
public class BPMInfo {

    TextView title, text;

    BPMInfo(TextView title, TextView text) {
        this.title = title;
        this.text = text;
    }

    float myBPM;

    public void updateBPM(float newBPM) {
        myBPM = newBPM;
        title.setText("About " + String.format("%.1f", myBPM) + " BPM");

    }


    public enum Italian {

        Unknown(Integer.MIN_VALUE, 39),
        Largo(40, 59),
        Larghetto(60, 65),
        Adagio(66, 75),
        Andante(76, 107),
        Moderato(108, 119),
        Allegro(120, 167),
        Presto(168, 199),
        Prestissimo(200, Integer.MAX_VALUE);


        private final int start, end;

        Italian(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public static Italian lookup(final int BPM) {
            final Italian[] a = Italian.values();

            for(Italian i : a) {
                if(i.start <= BPM && i.end >= BPM) {
                    return i;
                }
            }
            return a[0];
        }


    }
}
