package fi.zah.ollpu.materialtempo;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ollpu on 22.3.2015.
 *
 */
public class BPMInfo {

    TextView title, text;
    TabTap parent;

    BPMInfo(TextView title, TextView text, TabTap parent) {
        this.title = title;
        this.text = text;
        this.parent = parent;
    }

    float myBPM;

    public void updateBPM(float newBPM) {
        Resources res = parent.getResources();
        myBPM = newBPM;
        title.setText(
                res.getText(R.string.about_)
                + String.format("%.1f", myBPM)
                + res.getText(R.string._bpm)
        );

        Italian[] italians = Italian.lookup(Math.round(myBPM));
        String italianNames = "";
        for(Italian italian : italians) {
            italianNames = italianNames + (italianNames.equals("") ? "" : ", ")
                    + italian.toString().replace("_", " ");
        }
        text.setText(
                (italians.length > 1
                        ? res.getText(R.string.italian_tempo_plural)
                        : res.getText(R.string.italian_tempo)
                )
                + italianNames
        );
    }


    public static enum Italian {

        Unknown(Integer.MIN_VALUE, -1),
        Larghissimo(0, 24),
        Grave(25, 45),
        Largo(40, 60),
        Lento(45, 60),
        Larghetto(60, 66),
        Adagio(66, 76),
        Adagietto(72, 76),
        Andante(76, 108),
        Andantino(80, 108),
        Marcia_moderato(83, 85),
        Andante_moderato(92, 112),
        Moderato(108, 120),
        Allegretto(112, 120),
        Allegro_moderato(116, 120),
        Allegro(120, 168),
        Vivace(168, 176),
        Vivacissimo(172, 176),
        Allegrissimo(172, 176),
        Presto(168, 200),
        Prestissimo(200, Integer.MAX_VALUE);


        public final int start, end;

        Italian(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public static Italian[] lookup(final int BPM) {
            final Italian[] a = Italian.values();

            ArrayList<Italian> matches = new ArrayList<>();
            for(Italian i : a) {
                if(i.start <= BPM && i.end >= BPM) {
                    matches.add(i);
                }
            }
            return matches.toArray(new Italian[matches.size()]);
        }


    }
}
