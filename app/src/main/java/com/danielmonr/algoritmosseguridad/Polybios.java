package com.danielmonr.algoritmosseguridad;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Polybios extends ActionBarActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polybios);
        et = (EditText)findViewById(R.id.et_Solucion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_polybios, menu);
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

    public void Generar(View view){
       et.setText(algoritmo());
    }

    public String algoritmo() {
        // Inicializacion de matriz de igualdades
        char[][] matriz_igualdades = new char[15][15];
        int col = 0;
        int fil = 0;
        int i;
        // Valores aceptados: numeros, mayusculas y minusculas
        String ejemplo = "Esto es un ejemplo";
        String S = "";
        char temp;
        for (i = 0; i < ejemplo.length(); ++i) {
            temp = ejemplo.charAt(i);
            col = ((int) temp - 32) % 15;
            fil = ((int) temp - 32) / 15;
            col = col + 65;
            fil = fil + 65;
            temp = (char)col;
            S+= temp;
            temp = (char)fil;
            S += temp;
            //S += (char)col + (char)fil;
            Log.d("I_TAG", Character.toString(temp));
        }
        return S;
    }
}
