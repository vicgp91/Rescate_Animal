package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import appcpanama.logicstudio.net.appcpanama.Commons.SPControl;

public class AnimalDetails extends AppCompatActivity {

    //Controls
    CoordinatorLayout root;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    //Button btnUbicacion;
    ImageView imagenMenu;
    TextView tittuloBarra, subTituloBarra;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_details);
        imagenMenu=(ImageView)findViewById(R.id.imagen_menu);

        initInstance();
        assign();
    }

    private void initInstance() {
        root = (CoordinatorLayout) findViewById(R.id.animaldetails_root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);

        tittuloBarra = (TextView)findViewById(R.id.tittuloBarra);
        subTituloBarra=(TextView)findViewById(R.id.subTituloBarra);


        Intent intent = getIntent();

        String animalSelected = intent.getStringExtra("animalSelected");

        //btnUbicacion = (Button) findViewById(R.id.btn_animaldetails_ubicacion);
        SPControl sp = new SPControl(AnimalDetails.this);
        imagenMenu.setImageResource(sp.fakeData().get(Integer.parseInt(animalSelected)).getImgMenu());
        tittuloBarra.setText(sp.fakeData().get(Integer.parseInt(animalSelected)).getNombre());
        subTituloBarra.setText(sp.fakeData().get(Integer.parseInt(animalSelected)).getNombreCientifico());

        webView = (WebView) findViewById(R.id.webViewMasInfoAnimal);
        webView.loadUrl(sp.fakeData().get(Integer.parseInt(animalSelected)).getRutaHtml());


    }

        private void assign() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*btnUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AnimalDetails.this, MapActivity.class);
                intent.putExtra("esReporte", "noEsReporte");
                startActivity(intent);
               // startActivity(intent);
            }
        });*/
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