package appcpanama.logicstudio.net.appcpanama;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import appcpanama.logicstudio.net.appcpanama.IntroFragments.drawerAdapter;

public class HomeScreen extends AppCompatActivity {


    //Controls
    Toolbar toolbar;
    RecyclerView drawerList;
    RecyclerView.Adapter drawerAdapter;
    RecyclerView.LayoutManager drawerLayoutManager;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        initInstance();
        assign();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawerList = (RecyclerView) findViewById(R.id.rclr_home_drawerlateral);

        drawerLayout = (DrawerLayout) findViewById(R.id.dlay_home);

        drawerLayoutManager = new LinearLayoutManager(getApplicationContext());

        drawerAdapter = new drawerAdapter(HomeScreen.this);
    }

    private void assign() {

        setSupportActionBar(toolbar);

        drawerList.setLayoutManager(drawerLayoutManager);
        drawerList.setAdapter(drawerAdapter);


        drawerToggle = new ActionBarDrawerToggle(HomeScreen.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

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

}
