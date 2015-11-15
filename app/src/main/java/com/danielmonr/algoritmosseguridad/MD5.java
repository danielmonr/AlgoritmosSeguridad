package com.danielmonr.algoritmosseguridad;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MD5 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_md5, menu);
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

    // BUFFER MD
    private final int I_A = 0x67452301;
    private final int I_B = (int) 0xEFCDAB89L;
    private final int I_C = (int) 0x98BADCFEL;
    private final int I_D = (int) 0x10325476;
    private int l_bits_entrada;

    public void algoritmo(){
        String entrada = "Este es un ejemplo";
        l_bits_entrada = entrada.length() * 8;

    }


}
