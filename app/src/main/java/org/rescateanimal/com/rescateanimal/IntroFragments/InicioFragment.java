package org.rescateanimal.com.rescateanimal.IntroFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rescateanimal.com.rescateanimal.Commons.fragmentResponse;
import org.rescateanimal.com.rescateanimal.R;


public class InicioFragment extends fragmentResponse {


    public InicioFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null)
        {
            view = (ViewGroup)inflater.inflate(R.layout.inicio, container, false);

            initInstance();
            assign();
        }
        return view;
    }

    private void initInstance(){

    }

    private void assign(){

    }

}
