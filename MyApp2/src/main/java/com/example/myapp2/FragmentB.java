package com.example.myapp2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class FragmentB extends Fragment {
    private Processes processes;
    private ListView l;
    private String[] rows;
    private ArrayAdapter<String> adapter;

    /**
     * Inflates the view with the fragment_b.xml
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_b,container,false);
    }


    /**
     * Prints a list of the currently running
     * processes after the main activity has been created
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        processes = new Processes(getActivity().getApplicationContext());

        l = (ListView) getActivity().findViewById(R.id.listViewB);
        rows = processes.getList().split("\t");

        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.list_itemb,R.id.textb,rows);
        l.setAdapter(adapter);
    }
}
