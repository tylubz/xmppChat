package net.tylubz.chat.contact_list.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

import net.tylubz.chat.R;

/**
 * @author Sergei Lebedev
 */

public class AddUserDialogFragment extends DialogFragment {

    private OnCompleteListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_add_user, null))
                // Add action buttons
                .setPositiveButton(R.string.title_add_user, (dialog, id) -> {
                    EditText editText = this.getDialog().findViewById(R.id.username_edit_text);
                    mListener.onComplete(editText.getText().toString());
                })
                .setNegativeButton(R.string.title_cancel,
                        (dialog, id) -> AddUserDialogFragment.this.getDialog().cancel());
        return builder.create();
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnCompleteListener) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

    public interface OnCompleteListener {
        void onComplete(String result);
    }
}


