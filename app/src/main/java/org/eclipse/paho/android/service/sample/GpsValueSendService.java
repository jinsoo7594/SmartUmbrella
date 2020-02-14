package org.eclipse.paho.android.service.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;
import static org.eclipse.paho.android.service.sample.GpsValue.latitude;
import static org.eclipse.paho.android.service.sample.GpsValue.longitude;


public class GpsValueSendService extends Service{

    GpsThread start = new GpsThread();
    Thread thread = new Thread(start);

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand()");

        thread.start();

        return START_STICKY;

    }

    @Override
    public void onDestroy() {

        Log.d(TAG, "onDestroy()");
        start.stop();

    }

    // 아래 onBind 메소드가 없으면 어떻게 될까?
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class GpsThread implements Runnable{

        private boolean stopped = false;

        public void run(){
            while( !stopped ){
                try{
                    Thread.sleep(1000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "tread ");
                GpsValueInsertDatabase(Double.toString(longitude),  Double.toString(latitude));
                Log.d("logggggg 의 값 : ", Double.toString(longitude));
                Log.d("lennnnnn 의 값 : ", Double.toString(latitude));
            }
        }
        public void stop(){
            stopped = true;
        }
    }




    public String GpsValueInsertDatabase(String value1, String value2){

        String longitude = (String)value1;
        String latitude = (String)value2;

        String serverURL ="http://jhy753.dothome.co.kr/iot/GpsInsert.php";
        String postParameters = "longitude="+longitude+"&latitude="+latitude;


        try{

            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("content-type", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d(TAG, "POST response code - " + responseStatusCode);

            InputStream inputStream;
            if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = bufferedReader.readLine()) != null){
                sb.append(line);
            }
            bufferedReader.close();

            Log.d(TAG, "InsertData: success ");
            return sb.toString();

        }catch (Exception e){

            Log.d(TAG, "InsertData: Error ", e);
            return new String("Error: " + e.getMessage());

        }
    }
}

