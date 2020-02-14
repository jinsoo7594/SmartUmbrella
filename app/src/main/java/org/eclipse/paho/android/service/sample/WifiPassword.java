package org.eclipse.paho.android.service.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;





public class WifiPassword extends AppCompatActivity {
    //private WifiScanAdapter adapter;
    private WifiManager wifiManager;
    private List<ScanResult> scanDatas; // ScanResult List
    private List<WifiData> wifiList;
    private ListView listView;
    InputMethodManager imm;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                scanDatas = wifiManager.getScanResults();
                Toast.makeText(getApplicationContext(), scanDatas.get(0).SSID, Toast.LENGTH_SHORT).show();

                wifiList = new ArrayList<>();
                for (ScanResult select : scanDatas) {
                    String BBSID = select.BSSID;
                    String SSID = select.SSID;
                    WifiData wifiData = new WifiData(BBSID, SSID, false);
                    wifiList.add(wifiData);
                }

                // 어댑터뷰(리스트 뷰)
                listView = (ListView) findViewById(R.id.listView);
                // 어댑터
                ArrayAdapter adapter = new WifiAdapter(getApplicationContext(), R.layout.item_layout, wifiList); // 안드로이드에서 기본적으로 제공되는 레이아웃
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getApplicationContext(), wifiList.get(i).getSSID(), Toast.LENGTH_SHORT).show();



                        final LinearLayout linearLayout = (LinearLayout)View.inflate(WifiPassword.this, R.layout.custom, null);

                        new AlertDialog.Builder(WifiPassword.this)
                                .setTitle(wifiList.get(i).getSSID())
                                //  .setIcon(R.drawable.)
                                .setView(linearLayout)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        EditText inputpassword = (EditText)linearLayout.findViewById(R.id.password);
                                        // EditText number = (EditText)linearLayout.findViewById(R.id.number);
                                        imm.hideSoftInputFromWindow(inputpassword.getWindowToken(), 0);
                                        // imm.hideSoftInputFromWindow(number.getWindowToken(), 0);

                                        Toast.makeText(WifiPassword.this, inputpassword.getText() , Toast.LENGTH_SHORT).show();

                                    }
                                }).show();

                    }
                });
                // listview 갱신
                adapter.notifyDataSetChanged();

            } else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_password);


        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        listView = (ListView) findViewById(R.id.listView);



        //--------------------
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);



        //----------------------------

    }



    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(receiver, intentFilter);
        wifiManager.startScan();

        listView.setFocusable(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void showWifiList(View v) {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }
}