package fi.zah.ollpu.materialtempo;

import android.content.Context;

/**
 * Created by ollpu on 22.3.2015.
 * int simple: A simplification of the original BPM, used as key in SharedPreferences
 *  - Also used to match similar BPMs into one
 * float accurate: The original BPM
 */
public class FavoriteBPM {

    String name;

    int simple;
    float accurate;


    public FavoriteBPM(int simple, float accurate, String name) {
        this.simple = simple;
        this.accurate = accurate;
        this.name = name;
    }

    public FavoriteBPM(float accurate, String name) {
        this.accurate = accurate;
        simple = simplify(accurate);
        this.name = name;
    }

    public static int simplify(float original) {
        return Math.round(original / 5) * 5;
    }


    /*public static FavoriteBPM findMatch(float of, Context context) {
        int ofSimple = simplify(of);

    }*/


    public String getName() { return name; }
    public String getDescription() {
        return
            String.format("%.1f", accurate)
            + " BPM ≈ "
            + simple
            + " BPM";
}
}
