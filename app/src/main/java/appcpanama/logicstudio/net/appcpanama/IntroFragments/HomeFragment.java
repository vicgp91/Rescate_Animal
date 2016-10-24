package appcpanama.logicstudio.net.appcpanama.IntroFragments;


import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import appcpanama.logicstudio.net.appcpanama.Commons.fragmentResponse;
import appcpanama.logicstudio.net.appcpanama.IntroActivity;
import appcpanama.logicstudio.net.appcpanama.R;

public class HomeFragment extends fragmentResponse {


    //Controls
    Toolbar toolbar;
    RecyclerView drawerList;
    RecyclerView.Adapter drawerAdapter;
    RecyclerView.LayoutManager drawerLayoutManager;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = (ViewGroup) inflater.inflate(R.layout.home, container, false);

            initInstance();
            assign();
        }
        return view;
    }

    private void initInstance() {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        drawerList = (RecyclerView) view.findViewById(R.id.rclr_home_drawerlateral);

        drawerLayout = (DrawerLayout) view.findViewById(R.id.dlay_home);

        drawerLayoutManager = new LinearLayoutManager(context);

        drawerAdapter = new drawerAdapter(this);
    }

    private void assign() {

        frgCallback.setToolbar(toolbar);

        drawerList.setLayoutManager(drawerLayoutManager);
        drawerList.setAdapter(drawerAdapter);


        drawerToggle = new ActionBarDrawerToggle(((IntroActivity)context), drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    public boolean isDrawerLayoutOpen(){
        return drawerLayout.isDrawerOpen(drawerList);
    }

    public void closeDrawerLayout(){
        drawerLayout.closeDrawer(drawerList);
    }




}
