package org.eclipse.paho.android.service.sample;

import android.os.Handler;
import android.os.Message;

/**
 * Created by song on 2017. 8. 21..
 */

public class ServiceThread extends Thread{

    Handler handler;
    boolean isRun = true;
    //------------------------------



    //--------------------------------------

    public ServiceThread(Handler handler){
        this.handler = handler;
    }


    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {

        //반복적으로 수행할 작업을 한다.
        while (isRun) {

            // ((Main)Main.mContext).scan();

            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
            mHandler.sendMessage(Message.obtain(handler, 1));


            try {
                Thread.sleep(2000); //2초씩 쉰다.

            } catch (Exception e) {
            }
        }
    }

    public Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    };
}

