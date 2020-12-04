package com.example.recuprimerparciallabov;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity   implements AdapterView.OnItemSelectedListener {
    EditText etModelo;
    EditText etMarca;
    List<String> etAnios = new ArrayList<>();
    Bundle bundle;
    AutoModel auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Editar auto");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        bundle = i.getExtras();
        auto = (AutoModel) bundle.getSerializable("AutoModel");

        etModelo = (EditText)super.findViewById(R.id.etModelo);
        etMarca = (EditText)super.findViewById(R.id.etMarca);
        //etAnio = (EditText)super.findViewById(R.id.etAnio);

        etMarca.setText(auto.getMake());
        etModelo.setText(auto.getModel());
        //etAnio.setText((String) auto.getYear());

        Button btnEditar = (Button)super.findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auto.setMake(etMarca.getText().toString());
                auto.setModel(etModelo.getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("position", bundle.getInt("position"));
                returnIntent.putExtra("AutoModel", auto);

                setResult(DetailActivity.RESULT_OK,returnIntent);
                finish();
            }
        });
        setActionBar();

        configSpinner();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if(menuItem.getItemId() == android.R.id.home){
            Intent mainActivity = new Intent(this, MainActivity.class);
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivity);
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void configSpinner() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);

        for (int i = 2000; i < y + 1; i++) {
            this.etAnios.add(Integer.toString(i));
        }

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, etAnios);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        String anio = Integer.toString(auto.getYear());
        spin.setSelection(etAnios.indexOf(anio));
    }

    private void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.titulo));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        auto.setYear(Integer.parseInt(etAnios.get(position)));
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}