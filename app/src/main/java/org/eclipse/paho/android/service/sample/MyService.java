package org.eclipse.paho.android.service.sample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;

import java.util.ArrayList;

/**
 * Created by song on 2017. 8. 10..
 */

public class MyService extends Service {

    NotificationManager Notifi_M;
    ServiceThread thread;
    Notification Notifi ;
    WifiApManager wifiApManager;

    boolean preFlag = false;
    boolean justOne = true;

    //ClientScanResult isReachableFlag = new ClientScanResult();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        wifiApManager = new WifiApManager(this);

        Notifi_M = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
        return START_STICKY;
    }

    //서비스가 종료될 때 할 작업

    public void onDestroy() {
        thread.stopForever();
        thread = null;//쓰레기 값을 만들어서 빠르게 회수하라고 null을 넣어줌.
    }

    class myServiceHandler extends Handler {

        @Override
        public void handleMessage(android.os.Message msg) {
            scan();
        }
    };

    public void message1() {

        NotificationManager notificationManager = (NotificationManager) MyService.this.getSystemService(MyService.this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(MyService.this.getApplicationContext(), MainActivity.class);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(MyService.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.bg).setTicker("Connected with Smart-Umbrella").setWhen(System.currentTimeMillis()).setNumber(1).setContentTitle("Smart-Umbrella").setContentText("우산과 연결되었습니다").setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);

        notificationManager.notify(1, builder.build()); // Notification send
    }

    public void message2() {

        NotificationManager notificationManager = (NotificationManager) MyService.this.getSystemService(MyService.this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(MyService.this.getApplicationContext(), MainActivity.class);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(MyService.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.bg).setTicker("Disconnected with Smart-Umbrella").setWhen(System.currentTimeMillis()).setNumber(1).setContentTitle("Smart-Umbrella").setContentText("우산과 연결이 끊겼습니다").setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);
        notificationManager.notify(1, builder.build()); // Notification send

    }

    public void scan()  {
        wifiApManager.getClientList(false, new FinishScanListener() {

            @Override
            public void onFinishScan(final ArrayList<ClientScanResult> clients) {

                for (ClientScanResult clientScanResult : clients) {


                    if (clientScanResult.isReachable() == true && justOne == true) {


                        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE );
                        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG" );
                        wakeLock.acquire(3000);

                        message1();
                        //얘도 3초마다 반복되어서 이걸 해결해야함 + 디자인요소
                        preFlag = true;
                        justOne = false;
                    }

                    if (preFlag == true && clientScanResult.isReachable() == false) {


                        message2();
                        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE );
                        PowerManager.WakeLock wakeLock = pm.newWakeLock( PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG" );
                        wakeLock.acquire(3000);

                        preFlag = false;
                        justOne = true;
                    }
                }
            }
        });

    }


}
