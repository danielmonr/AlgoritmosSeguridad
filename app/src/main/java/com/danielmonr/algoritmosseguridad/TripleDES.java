package com.danielmonr.algoritmosseguridad;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TripleDES extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triple_des);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_triple_de, menu);
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
    private String ejemplo = "Daniel";
    private byte[] k = {(byte)0x13,(byte)0x34,(byte)0x57,(byte)0x79,(byte)0x9b,(byte)0xbc,(byte)0xdf,(byte)0xf1};
    private byte[] kpls = new byte[7];
    private final int[] L_S = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};

    private int[] llaves = new int[32];
    private int[] C = new int [16];
    private int[] D = new int[16];

    private final int[] PC_1 = {
            57,49,41,33,25,17, 9,
             1,58,50,42,34,26,18,
            10, 2,59,51,43,35,27,
            19,11, 3,60,52,44,36,
            63,55,47,39,31,23,15,
             7,62,54,46,38,30,22,
            14, 6,61,53,45,37,29,
            21,13, 5,28,20,12, 4
    };
    private final int[] PC_2 = {
            14,17,11,24, 1, 5,
             3,28,15, 6,21,10,
            23,19,12, 4,26, 8,
            16, 7,27,20,13, 2,
            41,52,31,37,47,55,
            30,40,51,54,33,48,
            44,49,39,56,34,53,
            46,42,50,36,29,32
    };
    private final int[] IP = {
            58,50,42,34,26,18,10,2,
            60,52,44,36,28,20,12,4,
            62,54,46,38,30,22,14,6,
            64,56,48,40,32,24,16,8,
            57,49,41,33,25,17,9,1,
            59,51,43,35,27,19,11,3,
            61,53,45,37,29,21,13,5,
            63,55,47,39,31,23,15,7
    };
    public void algoritmo(View view){
       /* String S = ejemplo;
        while(S.length() % 8 != 0){
            S = S+ ((char)0);
        }
        int l_mnsj = S.length();
        int n_bloques = (l_mnsj / 8)+1;
        for (int i = 0; i < n_bloques; ++i){
            byte[] bi = new byte[4];
            byte[] bd = new byte[4];
            for (int j = 0; j < 4; ++j){
                bi[j] = (byte) S.charAt(i+j);
                bd[j] = (byte) S.charAt(i+4+j);
            }

        }
        */

        generarLlaves();
        String llave = "";
        StringBuilder sb = new StringBuilder(k.length*2);
        for(byte b:k){
            sb.append(String.format("%02x", b & 0xff));
        }
        llave = sb.toString();
        Log.d("Llave", llave);
        StringBuilder sb2 = new StringBuilder();
        for(byte b:kpls){
            sb2.append(String.format("%8s", Integer.toBinaryString(b & 0xff)).replace(' ', '0'));
        }

        String llave2 = sb2.toString();
        Log.d("NUEVA LLAVE", llave2);
    }

    private void generarLlaves(){
        int n_byte, n_bit;
        byte bit = 1;
        for(int i = 0; i < 56;++i){
            n_byte = PC_1[i] / 8;
            n_bit =  8 - (PC_1[i] % 8);
            kpls[i>>>3] = (byte)((((k[n_byte] & 0xff) >>> n_bit) & bit) | ((kpls[i>>>3] & 0xff) << 1));
        }
        int c0 = kpls[0] & 0xff;
        c0 <<= 8;
        c0 = c0 | (kpls[1] & 0xff);
        c0 <<= 8;
        c0 = c0 | (kpls[2] & 0xff);
        c0 <<= 8;
        c0 = c0 | (kpls[3] & 0xff);
        c0 >>>= 4;

        int d0 = kpls[3] & 0x0f;
        d0 <<= 8;
        d0 = d0 | (kpls[4] & 0xff);
        d0 <<= 8;
        d0 = d0 | (kpls[5] & 0xff);
        d0 <<= 8;
        d0 = d0 | (kpls[6] & 0xff);
        Log.d("C0", Integer.toBinaryString(c0).replace(' ', '0'));
        Log.d("D0", Integer.toBinaryString(d0).replace(' ', '0'));
        int mascara = 0x0fffffff;
        int temp;
        int ctemp = c0;
        int dtemp = d0;
        for (int i = 0; i < 16; ++i){
            for (int j = 0; j < L_S[i]; ++j){
                temp = ctemp >>> 27;
                C[i] = ctemp << 1;
                C[i] = (C[i] + temp) & mascara;
                temp = dtemp >>> 27;
                D[i] = dtemp << 1;
                D[i] = (D[i] + temp) & mascara;
                ctemp = C[i];
                dtemp = D[i];
            }
        }
        Log.d("C1", Integer.toBinaryString(C[0]));
    }

    public void Feistel(byte[] b){

    }

    public void funcion(){

    }

    private void PI(){

    }
    private void PF(){

    }
}
