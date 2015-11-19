package com.danielmonr.algoritmosseguridad;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;

public class RSA extends ActionBarActivity {
    private String Rsa_ejemplos = "";
    private EditText et;
    private TextView tv;
    int contadorPaginas;
    int[] Paginas = {R.string.RSA_explicacion_1, R.string.RSA_explicacion_2, R.string.RSA_explicacion_3, R.string.RSA_explicacion_4, R.string.RSA_explicacion_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);
        tv = (TextView)findViewById(R.id.tv_explicacion_RSA);
        et = (EditText)findViewById(R.id.et_Solucion_RSA);
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
        if(contadorPaginas == 5)
            tv.setText(Rsa_ejemplos);
        else
            setPagina();
    }
    private void setPagina(){
        tv.setText(Paginas[contadorPaginas]);
    }

    public void Generar(View view){
        Rsa_ejemplos = "";
        String ejemplo = et.getText().toString();
        String[] encriptado = new String [ejemplo.length()];
        String encrypt = "";
        String desencriptado = "";
        int p,q,n,phi,e,d;
        p = 61;
        q = 53;
        n = p*q;
        phi = (p-1)*(q-1);
        e = 17;
        d = 2753;
        Rsa_ejemplos += "p = " + Integer.toString(p) + "\nq = " + Integer.toString(q) + "\nn = " + Integer.toString(n) + "\nphi = " + Integer.toString(phi) + "\ne = " +Integer.toString(e) + "\nd = " +Integer.toString(d);
        for(int i = 0;i<ejemplo.length();i++){
            //System.out.println((int)ejemplo.charAt(i)+"^"+e+"mod"+n+"="+(int)Math.pow((int)ejemplo.charAt(i),e) % n);
            Rsa_ejemplos += "\n" +((int)ejemplo.charAt(i)+"^"+e+"mod"+n+"="+(int)Math.pow((int)ejemplo.charAt(i),e) % n);
            BigInteger be = new BigInteger(String.valueOf(e));
            BigInteger bn = new BigInteger(String.valueOf(n));
            BigInteger bm = new BigInteger(String.valueOf((int)ejemplo.charAt(i)));
            bm = bm.modPow(be, bn);
            encriptado[i] = bm.toString();
            //System.out.print((int)ejemplo.charAt(i)+ " ");
            encrypt += encriptado[i] + '-';
        }
        et.setText(encrypt);
        if(contadorPaginas == 5)
            tv.setText(Rsa_ejemplos);
    }

}
