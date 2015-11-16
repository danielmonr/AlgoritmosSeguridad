package com.danielmonr.algoritmosseguridad;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

    public void Generar(View view){
        TextView S = (TextView)findViewById(R.id.tv_Solucion);
        S.setText(algoritmo());
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
    private String ejemplo = "Daniel";

    //Tabla que se llena con el seno, y su inicializacion
    private static int[] T = new int [64];
    static{
        for(int i = 0; i < 64;++i){
            // T[i] = 4294967296 * abs(seno (i+1)
            T[i] = (int)(long)((1L<<32)* Math.abs(Math.sin(i+1)));
        }
    }
    public String algoritmo() {
        byte[] entrada = ejemplo.getBytes();
        l_bytes_entrada = entrada.length;
        l_bytes = l_bytes_entrada + 8;
        int numBlocks = (l_bytes >>> 6) + 1; //numero de bloques de 512 bits (64 bytes, 16 palabras de 32 bits)
        int total_l = numBlocks << 6;
        byte[] bytes_colchon = new byte[total_l-l_bytes_entrada];
        bytes_colchon[0] = (byte)0x80; // bit de extencion más 7 zeros
        long l_bits = (long)l_bytes_entrada;
        l_bits= l_bits * 8;
        // Ir acomodando los bytes de la longitud del mensaje al final del buffer
        for (int i = 0; i < 8; i++){
            bytes_colchon[bytes_colchon.length - 8 +i] = (byte)l_bytes_entrada;
            // recorrer de 8 bits (1 byte el numero, ya que se leen los últimos 8 bits al hacer el cast)
            l_bytes_entrada >>>=8;
        }

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
                if(j < l_bytes_entrada){
                    //palabras[j/4] <<= 8;
                    palabras[j/4] = (palabras[j/4] >>>8) | (int)entrada[index];
                    index++;
                }
                else{
                    //palabras[j/4] <<=8;
                    palabras[j/4] = (palabras[j/4] >>>8)| (int)bytes_colchon[index_colchon];
                    index_colchon++;
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
            // Una vez acabados los ciclos se suman las originales con las nuevas
            a += O_A;
            b += O_B;
            c += O_C;
            d += O_D;
        }
        // Se genera el hash MD5 con los buffers resultantes
        int cont = 0;
        char[] MD5 = new char[16];
        for (int i = 0; i < 4; ++i){
            int numero = (i == 0)? a : ((i == 1)? b : ((i == 2)?c : d));
            for (int j = 0; j < 4; ++j){
                MD5[cont] = (char)numero;
                cont++;
                numero >>>=8;
            }
        }
        String MD5S = "";
        for(int i = 0; i < 16; ++i){
            MD5S = MD5S + MD5[i];
        }
        return MD5S;
    }

    private int funcion_F(int X, int Y, int Z){
        int palabra;
        palabra = (X&Y)|(~X&Z);
        return palabra;
    }
    private int funcion_G(int X, int Y, int Z){
        int palabra;
        palabra = (X&Z)|(Y&~Z);
        return palabra;
    }
    private int funcion_H(int X, int Y, int Z){
        int palabra;
        palabra = X^Y^Z;
        return palabra;
    }
    private int funcion_I(int X, int Y, int Z){
        int palabra;
        palabra = Y^(X | ~Z);
        return palabra;
    }


}
