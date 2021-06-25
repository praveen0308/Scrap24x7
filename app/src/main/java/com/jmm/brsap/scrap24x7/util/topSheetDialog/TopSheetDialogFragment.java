package com.jmm.brsap.scrap24x7.util.topSheetDialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

/**
 * Created by andrea on 23/08/16.
 */
public class TopSheetDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TopSheetDialog(getContext(), getTheme());
    }
}
