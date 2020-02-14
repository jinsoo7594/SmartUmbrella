package org.eclipse.paho.android.service.sample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jinsoo Song on 2017-06-23.
 */


//  날씨 갱신이 앱을 껏다 키면 된다





public class MainActivity extends AppCompatActivity {

    ArrayList<reviews> reviewList;
    reviewsadapter adapter;

    //String lat= "37";
    //String lon= "127";

    String lat = Double.toString(GpsValue.latitude);
    String lon = Double.toString(GpsValue.longitude);




    // gps가 현재 어떻게 사용되고 있고, 어떻게 끌어다 쓸지 생각해봐야함

    TextView textView1;
    WifiApManager wifiApManager;
    boolean preFlag;

    //ClientScanResult isReachableFlag = new ClientScanResult();
    LocationListener locationListener;
    LocationManager locationManager;
    LocationManager lm;

    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);



        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //textView1 = (TextView) findViewById(R.id.textView1);
        wifiApManager = new WifiApManager(this);
        //scan();


        try{
                // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                        100, // 통지사이의 최소 시간간격 (miliSecond)
                        1, // 통지사이의 최소 변경거리 (m)
                        mLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                        100, // 통지사이의 최소 시간간격 (miliSecond)
                        1, // 통지사이의 최소 변경거리 (m)
                        mLocationListener);

        }catch(SecurityException ex){

        }

        Log.d("log 의 값 : ", lat);
        Log.d("len 의 값 : ", lon);

        reviewList = new ArrayList<reviews>();
        //new JSONAsyncTask().execute("http://www.i2ce.in/reviews/1/1");

        String url = "http://apis.skplanetx.com/weather/forecast/3hours?version=1";
        String key = "c3108cd6-062c-32ee-b5e0-64712fdc3240";
        //"0416be7c-5761-3931-8698-e95845d8f850";


        Log.d("test",lat);
        Log.d("test", lon);
        //new JSONAsyncTask().execute("http://apis.skplanetx.com/weather/forecast/3hours?version=1&lat=37&lon=127&appKey=c3108cd6-062c-32ee-b5e0-64712fdc3240");
        new JSONAsyncTask().execute(url + "&lat=" + lat + "&lon=" + lon + "&appKey=" + key);


        if(lat =="0.0") {
            new Intro().start();
        }


        final ListView listview = (ListView) findViewById(R.id.list_item);
        adapter = new reviewsadapter(getApplicationContext(), R.layout.row, reviewList);

        listview.setAdapter(adapter);


    }
    class Intro extends Thread {
        public void run () {
            SystemClock.sleep( 3000 );
            Intent intent = new Intent( getBaseContext(), MainActivity.class );
            startActivity( intent );
            finish();
        }
    }




    private final LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            GpsValue.longitude = location.getLongitude(); //경도  double
            GpsValue.latitude = location.getLatitude();   //위도

            /*
            int lat1 = (int) location.getLatitude();
            int lon1 = (int) location.getLongitude();
            lat = String.valueOf(lat1);
            lon = String.valueOf(lon1);
               */

            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.

        }

        public void onProviderDisabled(String provider) {

            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {

            Log.d("test", "onProviderEnabled, provider:" + provider);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };


    //--------------------------

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//for displaying progress bar
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {
//establishing http connection
                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();
                // if connected then access data
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    // JSONArray jarray = jsono.getJSONArray("reviews");
                    JSONObject jsonin = jsono.getJSONObject("weather");
                    JSONArray jarray = jsonin.getJSONArray("forecast3hours");

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);
                        reviews rev = new reviews();
                        //getting json object values from json array

                        rev.setTitle(object.getString("timeRelease"));

                        // 기상상태 코드
                        JSONObject numstar = object.optJSONObject("sky");
                        String sta1 = numstar.getString("code1hour");
                        rev.setStars1(sta1);

                        String sta2 = numstar.getString("code2hour");
                        rev.setStars2(sta2);

                        String sta3 = numstar.getString("code3hour");
                        rev.setStars3(sta3);


                        // 기상상태 한글
                        String sky1 = numstar.getString("name1hour");
                        rev.setSky1(sky1);

                        String sky2 = numstar.getString("name2hour");
                        rev.setSky2(sky2);

                        String sky3 = numstar.getString("name3hour");
                        rev.setSky3(sky3);


                        // 온도
                        JSONObject numtemp = object.getJSONObject("temperature");
                        String temp1 = numtemp.getString("temp1hour");
                        rev.setTemp1(temp1);

                        String temp2 = numtemp.getString("temp2hour");
                        rev.setTemp2(temp2);

                        String temp3 = numtemp.getString("temp3hour");
                        rev.setTemp3(temp3);


                        // 지역정보 한글
                        JSONObject numcity = object.optJSONObject("grid");
                        String city1 = numcity.getString("city")+numcity.getString("county")+ numcity.getString("village");
                        rev.setComment(city1);

                        //adding data to the arraylist
                        reviewList.add(rev);

                    }
                    return true;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            // if data dint fetch from the url
            dialog.cancel();
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                                                            // 한글화 해줄지..?
        }
    }

    //----------------

    // client.isReachable() 의 값을 항상 백그라운드에서 확인을 해서
    // openAP 를 누르면 그 시점부터 sleep(3000)간격으로 scan()실행

    public void scan() {

        wifiApManager.getClientList(false, new FinishScanListener() {

            @Override
            public void onFinishScan(final ArrayList<ClientScanResult> clients) {

                textView1.setText("WifiApState: " + wifiApManager.getWifiApState() + "\n\n");
                textView1.append("Clients: \n");
                for (ClientScanResult clientScanResult : clients) {
                    textView1.append("####################\n");
                    textView1.append("IpAddr: " + clientScanResult.getIpAddr() + "\n");
                    textView1.append("Device: " + clientScanResult.getDevice() + "\n");
                    textView1.append("HWAddr: " + clientScanResult.getHWAddr() + "\n");
                    textView1.append("isReachable: " + clientScanResult.isReachable() + "\n");

                    if (clientScanResult.isReachable() == true)
                        preFlag = true;

                    if (preFlag == true && clientScanResult.isReachable() == false) {
                        //message();
                        preFlag = false;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        // 메뉴버튼이 처음 눌러졌을 때 실행되는 콜백메서드
        // 메뉴버튼을 눌렀을 때 보여줄 menu 에 대해서 정의
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("test", "onOptionsItemSelected - 메뉴항목을 클릭했을 때 호출됨");

        int id = item.getItemId();
        Intent intent;

        switch(id) {

            case R.id.HotSpotOn:
                wifiApManager.setWifiApEnabled(null, true);
                intent = new Intent(this, MyService.class);
                startService(intent);
                return true;

            case R.id.HotSpotOff:
                wifiApManager.setWifiApEnabled(null, false);
                intent = new Intent(this, MyService.class);
                stopService(intent);
                return true;

            case R.id.MissingSpot:

                intent = new Intent(this, MissingSpot.class);
                startActivity(intent);
                break;

            case R.id.Navigation:

                intent = new Intent(this, Navigation.class);
                startActivity(intent);
                break;


            case R.id.LocationTrackingStart:

                startService(new Intent(this, GpsValueSendService.class));

                return true;

            case R.id.LocationTrackingStop:

                stopService( new Intent(this, GpsValueSendService.class));

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
