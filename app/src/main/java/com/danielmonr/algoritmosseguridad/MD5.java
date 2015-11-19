package com.danielmonr.algoritmosseguridad;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;

public class MD5 extends ActionBarActivity {
    private String MD5_ejemplos;
    private String ejemplo;
    private int[] explicaciones = {
            R.string.MD5_explicacion_1, R.string.MD5_explicacion_2, R.string.MD5_explicacion_3, R.string.MD5_explicacion_4, R.string.MD5_explicacion_5};
    private int cont;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);
        tv = (TextView)findViewById(R.id.tv_explicacion);
        tv.setMovementMethod(new ScrollingMovementMethod());
        cont = 0;
        TextView S = (TextView)findViewById(R.id.et_Solucion);
        ejemplo = S.getText().toString();
        algoritmo();
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

    public void Generar(View view){
        TextView S = (TextView)findViewById(R.id.et_Solucion);
        ejemplo = S.getText().toString();
        S.setText(algoritmo());
        if (cont == 5){
            tv.setText(MD5_ejemplos);
        }
    }

    public void Siguiente(View view){
        if (cont < 5) {
            if (cont == 4) {
                cont++;
                tv.setText(MD5_ejemplos);
            }
            else {
                tv.setText(explicaciones[++cont]);
                ((Button) findViewById(R.id.b_anterior)).setClickable(true);
            }
        }
        else
            ((Button)view).setClickable(false);
    }
    public void Anterior(View view){
        if(cont > 0) {
            tv.setText(explicaciones[--cont]);
            ((Button)findViewById(R.id.b_siguiente)).setClickable(true);
        }
        else{
            ((Button)view).setClickable(false);
        }

    }

    // BUFFER MD, palabras
    private final int I_A = 0x67452301;
    private final int I_B = (int) 0xEFCDAB89L;
    private final int I_C = (int) 0x98BADCFEL;
    private final int I_D = (int) 0x10325476;
    // Valores de s para las diferentes rondas
    private final int[] R1_v = {7,12,17,22};
    private final int[] R2_v = {5,9,14,20};
    private final int[] R3_v = {4,11,16,23};
    private final int[] R4_v = {6,10,15,21};


    private int l_bytes_entrada;
    private int l_bytes;

    //Tabla que se llena con el seno, y su inicializacion
    private static int[] T = new int [64];
    static{
        for(int i = 0; i < 64;++i){
            // T[i] = 4294967296 * abs(seno (i+1)
            T[i] = (int)(long)((1L<<32)* Math.abs(Math.sin(i+1)));
        }
    }

    private void toEjemplo(byte[] b, String s){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length;++i){
            sb.append(String.format("%02X", b[i] & 0xFF));
        }
        MD5_ejemplos += "\n" + s + sb.toString();
    }

    public String algoritmo() {
        byte[] entrada = ejemplo.getBytes();
        toEjemplo(entrada, "Entrada: ");
        l_bytes_entrada = entrada.length;
        l_bytes = l_bytes_entrada + 8;
        int numBlocks = (l_bytes >>> 6) + 1; //numero de bloques de 512 bits (64 bytes, 16 palabras de 32 bits)
        int total_l = numBlocks << 6;
        byte[] bytes_colchon = new byte[total_l-l_bytes_entrada];
        bytes_colchon[0] = (byte)0x80; // bit de extencion más 7 zeros
        long l_bits = (long)l_bytes_entrada;
        l_bits= l_bits * 8;
        Log.d("BYTES_ENTRADA", Integer.toString(l_bytes_entrada));
        // Ir acomodando los bytes de la longitud del mensaje al final del buffer
        for (int i = 0; i < 8; i++){
            bytes_colchon[bytes_colchon.length - 8 +i] = (byte)l_bits;
            // recorrer de 8 bits (1 byte el numero, ya que se leen los últimos 8 bits al hacer el cast)
            l_bits >>>=8;
        }

        toEjemplo(bytes_colchon, "Bytes de padding y longitud: ");
        // creacion de variables abcd
        int a = I_A;
        int b = I_B;
        int c = I_C;
        int d = I_D;

        for (int i = 0; i < numBlocks; ++i){

            // Dividimos el bloque de 512 bits en palabras de 16 palabras de 32 bits
            int[] palabras = new int[16];
            int index = i*64;
            int index_colchon= 0;

            for(int j = 0; j < 64; ++j){

                int indicediv4 = j /4;

                if(j < l_bytes_entrada){
                    //palabras[j/4] <<= 8;
                    palabras[indicediv4] =  ((int)entrada[index] << 24) | (palabras[indicediv4] >>>8);
                    index++;
                }
                else{
                    //palabras[j/4] <<=8;
                    palabras[indicediv4] =  ((int)bytes_colchon[index-l_bytes_entrada] << 24) | (palabras[indicediv4] >>>8);
                    index++;
                }


            }

            // Originales buffers del bloque:
            int O_A = a;
            int O_B = b;
            int O_C = c;
            int O_D = d;


            int indice_t = 0;
            int v = 0;
            for (int j = 0; j < 16; j++){
                int temp = b + Integer.rotateLeft((a+ funcion_F(b,c,d) + palabras[j] + T[indice_t]), R1_v[v]);
                v = (v >= 3) ? 0 : v+1;
                indice_t++;
                a = d;
                d = c;
                c = b;
                b = temp;
            }
            MD5_ejemplos += "\nRonda1: " + "a= " + Integer.toHexString(a) + "\nb= " + Integer.toHexString(b) + "\nc= " + Integer.toHexString(c) + "\nd= " + Integer.toHexString(d);
            v = 0;
            int indice_buffer = 1;
            for(int j = 0; j < 16; ++j){
                int temp = b + Integer.rotateLeft((a+ funcion_G(b, c, d) + palabras[indice_buffer] + T[indice_t]), R2_v[v]);
                v = (v >= 3) ? 0 : v+1;
                indice_buffer += 5;
                indice_buffer = (indice_buffer > 15)? indice_buffer-16 : indice_buffer;
                indice_t++;
                a = d;
                d = c;
                c = b;
                b = temp;
            }
            MD5_ejemplos += "\nRonda2: " + "a= " + Integer.toHexString(a) + "\nb= " + Integer.toHexString(b) + "\nc= " + Integer.toHexString(c) + "\nd= " + Integer.toHexString(d);
            indice_buffer = 5;
            for (int j = 0; j < 16; ++j){
                int temp = b + Integer.rotateLeft((a+ funcion_H(b, c, d) + palabras[indice_buffer] + T[indice_t]), R3_v[v]);
                v = (v >= 3) ? 0 : v+1;
                indice_buffer += 3;
                indice_buffer = (indice_buffer > 15)? indice_buffer-16 : indice_buffer;
                indice_t++;
                a = d;
                d = c;
                c = b;
                b = temp;
            }
            MD5_ejemplos += "\nRonda3: " + "a= " + Integer.toHexString(a) + "\nb= " + Integer.toHexString(b) + "\nc= " + Integer.toHexString(c) + "\nd= " + Integer.toHexString(d);
            indice_buffer = 0;
            for (int j = 0; j < 16; ++j){
                int temp = b + Integer.rotateLeft((a+ funcion_I(b, c, d) + palabras[indice_buffer] + T[indice_t]), R4_v[v]);
                v = (v >= 3) ? 0 : v+1;
                indice_buffer += 7;
                indice_buffer = (indice_buffer > 15)? indice_buffer-16 : indice_buffer;
                indice_t++;
                a = d;
                d = c;
                c = b;
                b = temp;
            }
            MD5_ejemplos += "\nRonda4: " + "a= " + Integer.toHexString(a) + "\nb= " + Integer.toHexString(b) + "\nc= " + Integer.toHexString(c) + "\nd= " + Integer.toHexString(d);
            // Una vez acabados los ciclos se suman las originales con las nuevas
            a += O_A;
            b += O_B;
            c += O_C;
            d += O_D;
            MD5_ejemplos += "\nFinales: " + "a= " + Integer.toHexString(a) + "\nb= " + Integer.toHexString(b) + "\nc= " + Integer.toHexString(c) + "\nd= " + Integer.toHexString(d);
            Log.d("A", Integer.toString(a));
            Log.d("B", Integer.toString(b));
            Log.d("C", Integer.toString(c));
            Log.d("D", Integer.toString(d));
        }

        // Se genera el hash MD5 con los buffers resultantes
        int cont = 0;
        byte[] MD5 = new byte[16];
        for (int i = 0; i < 4; ++i){
            int numero = (i == 0)? a : ((i == 1)? b : ((i == 2)?c : d));
            for (int j = 0; j < 4; ++j){
                MD5[cont] = (byte)numero;
                cont++;
                numero >>>=8;
            }
        }
        String MD5S = "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < MD5.length; ++i){
            sb.append(String.format("%02X", MD5[i] & 0xFF));
        }
        MD5S = sb.toString();

        return MD5S;
    }

    private int funcion_F(int X, int Y, int Z){
        int palabra;
        palabra = (X & Y) | (~X & Z);
        return palabra;
    }
    private int funcion_G(int X, int Y, int Z){
        int palabra;
        palabra = ( X & Z) | (Y & ~Z);
        return palabra;
    }
    private int funcion_H(int X, int Y, int Z){
        int palabra;
        palabra = X ^ Y ^ Z;
        return palabra;
    }
    private int funcion_I(int X, int Y, int Z){
        int palabra;
        palabra = Y ^ (X | ~Z);
        return palabra;
    }
}
