package com.kitsyambocka.wifitest;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnListItemClick, OnConnectClick {

    private RecyclerView recyclerView;
    private Button button;

    private WifiManager wifi;

    private List<ScanResult> results;

    private WifiListAdapter wifiListAdapter;

    private ProgressDialog progressDialog;
    private ScanResult mScanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        button = (Button) findViewById(R.id.button_refresh);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                results = wifi.getScanResults();
                wifiListAdapter.setList(results);
                progressDialog.dismiss();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifi.startScan();

        results = new ArrayList<>();

        wifiListAdapter = new WifiListAdapter(results, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(wifiListAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results = wifi.getScanResults();
                progressDialog.show();
                wifi.startScan();

            }
        });
    }


    @Override
    public void onWifiClick(ScanResult scanResult) {
        mScanResult = scanResult;
        new ConnectDialog().show(getFragmentManager(), "TAG");

    }

    @Override
    public void onConnect(String password) {
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + mScanResult.SSID + "\"";

        if (mScanResult.capabilities.contains("WPA")) {
            conf.preSharedKey = "\"" + password + "\"";

        } else if (mScanResult.capabilities.contains("WEP")) {
            conf.wepKeys[0] = "\"" + password + "\"";
            conf.wepTxKeyIndex = 0;
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        } else {
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        wifi.addNetwork(conf);


        wifi.disconnect();
        wifi.enableNetwork(conf.networkId, true);
        wifi.reconnect();


    }
}
