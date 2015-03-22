package fi.zah.ollpu.materialtempo;

/**
 * Created by ollpu on 22.3.2015.
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
        simple = Math.round((accurate - 2.5f) / 5) * 5;
    }
}
