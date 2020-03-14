package com.roman.batsu.ui.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.roman.batsu.R;
import com.roman.batsu.utils.TextUtils;

import static android.content.Context.MODE_PRIVATE;

public class OnBoardingDialog extends DialogFragment implements View.OnClickListener {

    /**
     *  imageLogo.setOnClickListener(new View.OnClickListener() {
     *
     *             public void onClick(View v) {
     *                 if (noConnection) {
     *                     showToast(getString(R.string.connection_error));
     *                     return;
     *                 }
     *
     *                 if (happyNewYearDialog.isAdded()) {
     *                     happyNewYearDialog.dismiss(); //or return false/true, based on where you are calling from
     *                 } else {
     *                     happyNewYearDialog.setCancelable(true);
     *                     happyNewYearDialog.show(getSupportFragmentManager(), "HappyNewYearDialogFragment");
     *                 }
     *
     *             }
     *         });*/

    private Button letsGoButton;
    private TextView text_title, text_description;
    private static final String PREFS_NAME = "firstStartRun";
    private SharedPreferences settings;

    public static OnBoardingDialog newInstance() {
        return new OnBoardingDialog();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.onboarding_dialog, null);
        builder.setView(v);
        settings = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        text_title = v.findViewById(R.id.text_title);
        text_description = v.findViewById(R.id.text_description);
        letsGoButton = v.findViewById(R.id.letsGoButton);
        text_title.setText(getString(R.string.on_boarding_dialog_title));
        TextUtils.setTextWithLinks(text_description, TextUtils.fromHtml(getString(R.string.on_boarding_dialog_description)));
        letsGoButton.setOnClickListener(this);
        return builder.create();
    }


    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isFirstRun", false);
        editor.apply();
        dismiss();
    }
}
