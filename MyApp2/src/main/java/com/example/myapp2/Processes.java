package com.example.myapp2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import java.util.List;


public class Processes extends Activity {
    private Context context;
    private List<ActivityManager.RunningAppProcessInfo> list;
    private ActivityManager manager;


    /**
     * Constructor initializes the variables
     * @param con
     */
    public Processes(Context con){
        context = con;
        manager = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE);
        list = manager.getRunningAppProcesses();
    }


    /**
     * Returns the running processes
     * @return
     */
    public String getList(){
        StringBuilder sb=new StringBuilder();
        String output=null;
        if(list != null){
            for(int i=0;i<list.size();++i){
                int[] pids;
                pids = new int[]{list.get(i).pid};
                sb.append("pid: "+list.get(i).pid+"\n"
                        +"name: "+list.get(i).processName+"\n"
                        +"memory: "+manager.getProcessMemoryInfo(pids)[0].getTotalPss()/1024+"MB"+ "\t");
            }
            output = sb.toString();
        }
        return output;
    }
}

