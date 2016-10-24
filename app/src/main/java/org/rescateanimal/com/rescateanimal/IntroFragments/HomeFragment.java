package org.rescateanimal.com.rescateanimal.IntroFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rescateanimal.com.rescateanimal.Commons.fragmentResponse;
import org.rescateanimal.com.rescateanimal.R;

public class HomeFragment extends fragmentResponse {


    //Controls
    Toolbar toolbar;
    RecyclerView drawerList;
    RecyclerView.Adapter drawerAdapter;
    RecyclerView.LayoutManager drawerLayoutManager;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(view == null)
        {
            view = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

            initInstance();
            assign();
        }
        return view;
    }

    private void initInstance(){
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);

        drawerList = (RecyclerView)view.findViewById(R.id.rclr_home_drawerlateral);

        drawerLayoutManager = new LinearLayoutManager(context);

        drawerAdapter = new drawerAdapter();
    }

    private void assign(){

        frgCallback.setToolbar(toolbar);

        drawerList.setLayoutManager(drawerLayoutManager);
        drawerList.setAdapter(drawerAdapter);
    }

}
