package com.example.recuprimerparciallabov;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  Handler.Callback {
    public List<AutoModel> listaAutos = new ArrayList<>();
    private AutoAdapter autoAdapter ;
    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listaAutos.add(new AutoModel(1,"FORD", "Fiesta", 2000));
        this.listaAutos.add(new AutoModel(1,"FORD", "Focus", 2004));

        setRecyclerView();

        h = new Handler(this);

        HttpThread httpThread = new HttpThread(h, getString(R.string.api_url));
        httpThread.start();
        /*URL url = null;
        try {
            url = new URL(getString(R.string.api_url));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();

        }*/

    }

    private void updateList(int position, AutoModel auto){
       this.listaAutos.set(position, auto);
       this.autoAdapter.notifyDataSetChanged();
    }


    private void setRecyclerView(){
        RecyclerView rv = (RecyclerView)findViewById(R.id.recycler);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);

        autoAdapter = new AutoAdapter(this.listaAutos, this);
        rv.setAdapter(autoAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == DetailActivity.RESULT_OK){
                AutoModel auto ;
                Bundle b = data.getExtras();
                auto  = (AutoModel) b.getSerializable("AutoModel");
                updateList(b.getInt("position"), auto);
            }
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message message) {

        this.autoAdapter.notifyDataSetChanged();
        return true;
    }

}

