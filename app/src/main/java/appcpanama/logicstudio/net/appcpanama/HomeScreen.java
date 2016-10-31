package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import appcpanama.logicstudio.net.appcpanama.Adapters.drawerAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.MaterialDialogClass;

public class HomeScreen extends AppCompatActivity {


    //Controls
    Toolbar toolbar;
    Button btnReportar;
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

        btnReportar = (Button) findViewById(R.id.btn_home_reportar);

        drawerList = (RecyclerView) findViewById(R.id.rclr_home_drawerlateral);

        drawerLayout = (DrawerLayout) findViewById(R.id.dlay_home);

        drawerLayoutManager = new LinearLayoutManager(getApplicationContext());

        drawerAdapter = new drawerAdapter(HomeScreen.this);
    }

    private void assign() {

        setSupportActionBar(toolbar);

        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, ReportarActivity.class));

            }
        });

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


    public void clickDrawer(int position) {

        Intent drawerPosition = null;

        switch (position) {

            case 1:


                drawerPosition = new Intent(HomeScreen.this, ReportarActivity.class);
                break;

            case 2:
                drawerPosition = new Intent(HomeScreen.this, PerfilScreen.class);
                break;

            case 3:
                drawerPosition = new Intent(HomeScreen.this, InfoAnimal.class);
                break;


            case 4:
                String phone = getResources().getString(R.string.numberContact);
                drawerPosition = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));

                break;


            case 5:

                break;
        }

        startActivity(drawerPosition);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkDrawerOpen();
            }
        }, 300);

    }

    private boolean checkDrawerOpen() {
        if (drawerLayout.isDrawerOpen(drawerList)) {
            drawerLayout.closeDrawer(drawerList);

            return true;
        } else
            return false;
    }

    @Override
    public void onBackPressed() {

        if (!checkDrawerOpen())
            super.onBackPressed();
    }
}
