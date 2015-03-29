package fi.zah.ollpu.materialtempo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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

    public void edit(Context context) {
        Resources res = context.getResources();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.edit_favorite, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText inputName = (EditText) promptView.findViewById(R.id.editName);
        final EditText inputBPM = (EditText) promptView.findViewById(R.id.editBPM);

        alertDialogBuilder
                .setTitle(res.getString(R.string.edit_fav))
                .setCancelable(false)
                .setPositiveButton(res.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                inputBPM.getText();
                                inputName.getText();
                            }
                        })
                .setNegativeButton(res.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,	int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();

    }
}
