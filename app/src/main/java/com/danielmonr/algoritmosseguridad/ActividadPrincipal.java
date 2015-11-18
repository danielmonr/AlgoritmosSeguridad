package com.danielmonr.algoritmosseguridad;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ActividadPrincipal extends ActionBarActivity  {
    private Button b_p, b_m;

    private String[] algoritmos = {
            "Polybios", "MD5", "3DES"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, algoritmos);
        ListView lv = (ListView)findViewById(R.id.lv_algoritmos);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        poly();
                        break;
                    case 1:
                        md5();
                        break;
                    case 2:
                        Tdes();
                        break;
                    default:
                        md5();
                        break;
                }
            }
        });
    }

    private void poly(){
        Intent intent = new Intent(this, Polybios.class);
        startActivity(intent);
    }
    private void md5(){
        Intent intent = new Intent(this, MD5.class);
        startActivity(intent);
    }
    private void Tdes(){
        Intent intent = new Intent(this, TripleDES.class);
        startActivity(intent);
    }
}

