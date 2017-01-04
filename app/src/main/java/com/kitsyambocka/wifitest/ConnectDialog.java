package com.kitsyambocka.wifitest;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Developer on 04.01.2017.
 */

public class ConnectDialog extends DialogFragment implements View.OnClickListener {

    private EditText etPassword;
    private Button bConnect;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_connect, null);
        v.findViewById(R.id.button_connect).setOnClickListener(this);
        etPassword = (EditText)v.findViewById(R.id.et_password);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }


    @Override
    public void onClick(View v) {
        OnConnectClick onConnectClick = (OnConnectClick)getActivity();
        onConnectClick.onConnect(etPassword.getText().toString());
        dismiss();
    }
}
