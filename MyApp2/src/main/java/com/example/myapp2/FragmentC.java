package com.example.myapp2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentC extends Fragment {

    private Info info;

    /**
     * Inflates the view with the fragment_c.xml
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_c,container,false);
    }


    /**
     * Prints the phone info on the screen
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        info = new Info(getActivity().getApplicationContext());
        TextView text = (TextView) getActivity().findViewById(R.id.textViewC);
        text.setText(info.display());
    }
}
