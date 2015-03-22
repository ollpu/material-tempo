package fi.zah.ollpu.materialtempo;

import android.content.Context;

/**
 * Created by ollpu on 22.3.2015.
 * int simple: A simplification of the original BPM, used as key in SharedPreferences
 *  - Also used to match similar BPMs into one
 * float accurate: The original BPM
 */
public class FavoriteBPM {
    int simple;
    float accurate;


    FavoriteBPM(int simple, float accurate) {
        this.simple = simple;
        this.accurate = accurate;
    }

    FavoriteBPM(float accurate) {
        this.accurate = accurate;
        simple = simplify(accurate);
    }

    private static int simplify(float original) {
        return Math.round((original - 2.5f) / 5) * 5;
    }


    public static FavoriteBPM findMatch(float of, Context context) {
        int ofSimple = simplify(of);

    }
}
