package com.cragtographer.app.services;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

public class AddItemWithNameClickListener implements View.OnClickListener {
    private AddListener m_addListener;
    private String m_creationType;
    public AddItemWithNameClickListener(String creationType, AddListener addListener){
        m_creationType = creationType;
        m_addListener = addListener;
    }

    public void onClick(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
        alertBuilder.setTitle("Create New " + m_creationType);
        alertBuilder.setMessage(m_creationType + " Name");

        final EditText input = new EditText(view.getContext());
        alertBuilder.setView(input);

        DialogInterface.OnClickListener alertDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                if(whichButton != -1)
                    return;
                m_addListener.onAdd(input.getText().toString());
            }
        };
        alertBuilder.setPositiveButton("Add", alertDialogClickListener);
        alertBuilder.setNegativeButton("Cancel", alertDialogClickListener);

        alertBuilder.show();
    }

    public interface AddListener{
        public abstract void onAdd(String name);
    }
}
