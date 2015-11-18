package com.danielmonr.algoritmosseguridad;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SHA extends ActionBarActivity {
    private EditText et;
    private TextView tv;
    int contadorPaginas;
    int[] Paginas = {R.string.SHA_explicacion_1,R.string.SHA_explicacion_2,R.string.SHA_explicacion_3,R.string.SHA_explicacion_4,R.string.SHA_explicacion_5,R.string.SHA_explicacion_6 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sha);
        et = (EditText)findViewById(R.id.et_Solucion_3DES);
        tv = (TextView)findViewById(R.id.tv_explicacion_3DES);
        tv.setMovementMethod(new ScrollingMovementMethod());
        contadorPaginas = 0;
        setPagina();
    }

    public void Anterior(View view){
        contadorPaginas = (contadorPaginas >0)? contadorPaginas-1:0;
        setPagina();
    }
    public void Siguiente(View view){
        contadorPaginas = (contadorPaginas < 5)? contadorPaginas+1:5;
        setPagina();
    }
    private void setPagina(){
        tv.setText(Paginas[contadorPaginas]);
    }
}
