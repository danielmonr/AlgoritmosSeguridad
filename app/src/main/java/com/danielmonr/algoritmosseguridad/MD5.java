package com.danielmonr.algoritmosseguridad;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.nio.charset.StandardCharsets;

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
    private int l_bytes_entrada;
    private int l_bytes;
    private String ejemplo = "Este es un ejemplo";

    public void algoritmo(View view) {
        byte[] entrada = ejemplo.getBytes();
        l_bytes_entrada = entrada.length;
        l_bytes = l_bytes_entrada + 8;
        int numBlocks = (l_bytes >>> 6) + 1; //numero de bloques de 512 bits (64 bytes)
        int total_l = numBlocks << 6;
        byte[] bytes_colchon = new byte[total_l-l_bytes_entrada];
        Log.d("NUMBLOCKS", Integer.toString(numBlocks));
        Log.d("NUM_BYTES_Entrada", Integer.toString(l_bytes_entrada));
        Log.d("NUM_BYTES_TOTAL", Integer.toString(total_l));
        bytes_colchon[0] = (byte)0x80; // bit de extencion más 7 zeros
        long l_bits = (long)l_bytes_entrada;
        Log.d("L_BITS_ANTES", Long.toString(l_bits));
        l_bits= l_bits << 3;
        Log.d("L_BITS_Despues", Long.toString(l_bits));
    }


}
