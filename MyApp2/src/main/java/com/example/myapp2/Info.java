package com.example.myapp2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.text.DecimalFormat;

import static android.os.SystemClock.uptimeMillis;

public class Info extends Activity {
    private Context context;
    private float internalSize,externalSize;
    private String kernel,model;
    private int android;
    private static long uptime;
    private NetworkInfo.State wifi1;
    private StatFs internal,external;
    private String internalSize2,externalSize2;


    /**
     * Constructor initializes variables
     * @param context
     */
    public Info(Context context){
        this.context = context;
        kernel = System.getProperty("os.version");
        model = Build.MODEL;
        android = Build.VERSION.SDK_INT;
        uptime = uptimeMillis();

        internal = new StatFs(Environment.getDataDirectory().getPath());
        internalSize = (long) internal.getBlockSize()*(long)internal.getAvailableBlocks();
        internalSize2 = (new DecimalFormat("##.##").format(internalSize/(1024*1024)));
        external = new StatFs(Environment.getExternalStorageDirectory().getPath());
        externalSize = (long)external.getBlockSize()*(long)external.getAvailableBlocks();
        externalSize2 = (new DecimalFormat("##.##").format(externalSize/(1024*1024)));
    }


    /**
     * Returns Wifi State
     * @return
     */
    public String getWifiState(){
        String mo;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            mo="is Connected";
        }
        else{
            mo="not Connected";
        }
        return mo;
    }


    /**
     * Returns 3G connection state
     * @return
     */
    public String get3GState(){
        String mo;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo threeG = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (threeG.isConnected()) {
            mo="is Connected";
        }
        else{
            mo="not Connected";
        }
        return mo;

    }


    /**
     * Returns battery level
     * @return
     */
    public float getBatteryLevel() {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        if(level == -1 || scale == -1) {
            return 50.0f;
        }

        return ((float)level / (float)scale) * 100.0f;
    }


    /**
     * Returns phone up time
     * @return
     */
    public String makeTime(){
        int hours=0;
        int minutes=0;
        int seconds=0;
        int temp=(int)(uptime/1000);

        hours= (int) (temp/3600);
        temp=temp-(hours*3600);
        minutes=(int)(temp/60);
        seconds=temp-(minutes*60);

        StringBuilder time=new StringBuilder();
        time.append(hours+": " +minutes+": "+seconds);
        return time.toString();
    }


    /**
     * Returns all the info
     * @return
     */
    public String display(){
        StringBuilder sb=new StringBuilder();
        sb.append("Android version: "+android+"\n"+
                "Kernel version: "+kernel+"\n"+
                "Model: "+model+"\n"+
                "Available internal storage: "+internalSize2+"MB\n"+
                "Available external storage: "+externalSize2+"MB\n"+
                "Battery level: "+getBatteryLevel()+"%\n"+
                "Uptime: "+makeTime()+"\n"+
                "Wifi: "+getWifiState()+"\n"+
                "3G: "+get3GState());
        String output;
        output=sb.toString();
        return output;
    }
}
