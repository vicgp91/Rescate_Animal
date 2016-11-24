package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import appcpanama.logicstudio.net.appcpanama.Adapters.infoAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.Commons.SimpleDividerItemDecoration;
import appcpanama.logicstudio.net.appcpanama.JavaBeans.infoAnimalBeans;

public class InfoAnimal extends AppCompatActivity {

    //Variable


    //Controls
    Toolbar toolbar;
    RecyclerView rclrList;
    infoAnimalAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_animal);

        initInstance();
        assign();
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        rclrList = (RecyclerView) findViewById(R.id.rclr_infoanimal_list);
        //fakeData();
        SPControl sp = new SPControl(InfoAnimal.this);
        adapter = new infoAnimalAdapter(sp.fakeData());

        layoutManager = new LinearLayoutManager(getApplicationContext());
    }

    private void assign() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rclrList.setLayoutManager(layoutManager);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        rclrList.addItemDecoration(new SimpleDividerItemDecoration(this, (int)metrics.density * 90));
        rclrList.setAdapter(adapter);

        adapter.setCallback(new infoAnimalAdapter.AnimalInterface() {
            @Override
            public void clickEvent(int position) {
                Intent i = new Intent(InfoAnimal.this, AnimalDetails.class);
                i.putExtra("animalSelected", String.valueOf(position));
                startActivity(i);
            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
