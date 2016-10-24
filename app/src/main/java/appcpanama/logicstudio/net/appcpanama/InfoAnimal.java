package appcpanama.logicstudio.net.appcpanama;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import appcpanama.logicstudio.net.appcpanama.Adapters.infoAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.SimpleDividerItemDecoration;
import appcpanama.logicstudio.net.appcpanama.JavaBeans.infoAnimalBeans;

public class InfoAnimal extends AppCompatActivity {

    //Variable
    List<infoAnimalBeans> list;

    //Controls
    Toolbar toolbar;
    RecyclerView rclrList;
    RecyclerView.Adapter adapter;
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

        fakeData();

        adapter = new infoAnimalAdapter(list);

        layoutManager = new LinearLayoutManager(getApplicationContext());
    }

    private void assign() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rclrList.setLayoutManager(layoutManager);
        rclrList.addItemDecoration(new SimpleDividerItemDecoration(this));
        rclrList.setAdapter(adapter);
    }


    private void fakeData() {

        list = new ArrayList<>();

        list.add(new infoAnimalBeans("Perro"));
        list.add(new infoAnimalBeans("Gato"));
        list.add(new infoAnimalBeans("Micho"));
        list.add(new infoAnimalBeans("Camaron"));
        list.add(new infoAnimalBeans("Frijoles"));
        list.add(new infoAnimalBeans("Perro"));
        list.add(new infoAnimalBeans("Gato"));
        list.add(new infoAnimalBeans("Micho"));
        list.add(new infoAnimalBeans("Camaron"));
        list.add(new infoAnimalBeans("Frijoles"));
        list.add(new infoAnimalBeans("Gato"));
        list.add(new infoAnimalBeans("Micho"));
        list.add(new infoAnimalBeans("Camaron"));
        list.add(new infoAnimalBeans("Frijoles"));
        list.add(new infoAnimalBeans("Gato"));
        list.add(new infoAnimalBeans("Micho"));
        list.add(new infoAnimalBeans("Camaron"));
        list.add(new infoAnimalBeans("Frijoles"));

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
