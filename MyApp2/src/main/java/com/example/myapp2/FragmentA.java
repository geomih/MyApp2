package com.example.myapp2;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentA extends Fragment {
    private ListView l;
    private String[] rows;
    private ArrayAdapter<String> adapter;
    DatabaseAdapter database;

    /**
     * Inflates the view with the fragment_a.xml
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_a,container,false);
    }

    /**
     * Proceeds to display the content
     * of the database right after the main
     * activity has been created
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        database = new DatabaseAdapter(getActivity().getApplicationContext());

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long)stat.getBlockSize()*(long)stat.getAvailableBlocks();
        long megAvailable = bytesAvailable / (1024*1024);

        StatFs stat2 = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = stat2.getBlockSize();
        long availableBlocks = stat2.getAvailableBlocks();
        long megAvailable2 = blockSize*availableBlocks/ (1024*1024);

        database.insertData(megAvailable2,megAvailable);

        l = (ListView) getActivity().findViewById(R.id.listView);
        rows = database.getAllData().split("\n");

        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.list_item,R.id.text,rows);
        l.setAdapter(adapter);
    }
}
