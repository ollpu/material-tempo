package fi.zah.ollpu.materialtempo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

    private float reassignBPM(float newBPM) {
        accurate = newBPM;
        simple = simplify(accurate);
        return accurate;
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

    public void edit(final Context context, final FavoritesAdapter adapter, final SharedPreferences sharedPref) {
        editPreset(context, adapter, sharedPref, name, String.format("%.2f", accurate).replace(",", "."));

    }

    private void editPreset(final Context context, final FavoritesAdapter adapter, final SharedPreferences sharedPref, String nm, String vl) {
        Resources res = context.getResources();

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.edit_favorite, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText inputName = (EditText) promptView.findViewById(R.id.editName);
        inputName.setText(nm);
        final EditText inputBPM = (EditText) promptView.findViewById(R.id.editBPM);
        inputBPM.setText(vl);

        alertDialogBuilder
                .setTitle(res.getString(R.string.edit_fav))
                .setCancelable(false)
                .setPositiveButton(res.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove(name);
                                String newName = inputName.getText().toString();
                                String valueText = inputBPM.getText().toString();
                                if (!sharedPref.contains(newName)) {
                                    submitEdit(newName, valueText, editor, adapter);
                                } else edit_sameName(context, adapter, sharedPref, newName, valueText);
                            }
                        })
                .setNegativeButton(res.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertD = alertDialogBuilder.create();

        alertD.show();
    }


    private int edit_sameName(final Context context, final FavoritesAdapter adapter, final SharedPreferences sharedPref, final String newName, final String valueText) {
        final Resources res = context.getResources();

        final FavoriteBPM me = this;

        final int[] toReturn = new int[1];
        toReturn[0] = 0;
        new AlertDialog.Builder(context)
                .setTitle(res.getText(R.string.favEdit_overwrite_title))
                .setMessage(res.getText(R.string.favEdit_overwrite_message))
                .setPositiveButton(res.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {edit_sameNameCallback(2, context, adapter, sharedPref, newName, valueText);
                            }
                        })
                .setNegativeButton(res.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {edit_sameNameCallback(0, context, adapter, sharedPref, newName, valueText);
                            }
                        })
                .setNeutralButton(res.getString(R.string.action_edit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {edit_sameNameCallback(1, context, adapter, sharedPref, newName, valueText);
                            }
                        })
                .show();
        return toReturn[0];
    }


    private void edit_sameNameCallback(int result, Context context, FavoritesAdapter adapter, SharedPreferences sharedPref, String newName, String valueText) {
        SharedPreferences.Editor editor = sharedPref.edit();
        switch(result) {
            case 1:
                editPreset(context, adapter, sharedPref, newName, valueText);
            case 2:
                submitEdit(newName, valueText, editor, adapter);
        }
    }

    private void submitEdit(String newName, String valueText, SharedPreferences.Editor editor, FavoritesAdapter adapter) {
        name = newName;
        editor.putFloat(name, reassignBPM(Float.parseFloat(valueText)));
        editor.apply();

        adapter.notifyDataSetChanged();
    }
}
