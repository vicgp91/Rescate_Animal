package appcpanama.logicstudio.net.appcpanama;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import appcpanama.logicstudio.net.appcpanama.Adapters.ListAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Adapters.infoAnimalAdapter;
import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;
import appcpanama.logicstudio.net.appcpanama.Commons.SimpleDividerItemDecoration;

public class ReportarActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rclrList;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Dialog dialog = new Dialog(this);

        Button selecAnimal = (Button) findViewById(R.id.seleccionarAnimal);
        selecAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setTitle("Selecciona un animal");
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.animal_list);
                rclrList = (RecyclerView) dialog.findViewById(R.id.rclr_infoanimal_list);
                SPControl sp = new SPControl(ReportarActivity.this);
                adapter = new ListAnimalAdapter(sp.fakeData());
                layoutManager = new LinearLayoutManager(getApplicationContext());
                rclrList.setLayoutManager(layoutManager);
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                rclrList.addItemDecoration(new SimpleDividerItemDecoration(ReportarActivity.this, (int)metrics.density * 90));
                rclrList.setAdapter(adapter);
                dialog.show();

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reportar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
