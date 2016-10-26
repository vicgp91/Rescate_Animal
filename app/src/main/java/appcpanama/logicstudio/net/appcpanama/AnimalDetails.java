package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AnimalDetails extends AppCompatActivity {

    //Controls
    CoordinatorLayout root;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    Button btnUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_details);

        initInstance();
        assign();
    }

    private void initInstance() {
        root = (CoordinatorLayout) findViewById(R.id.animaldetails_root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        btnUbicacion = (Button) findViewById(R.id.btn_animaldetails_ubicacion);
    }

    private void assign() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnimalDetails.this, MapActivity.class));
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