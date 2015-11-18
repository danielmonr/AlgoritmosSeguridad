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
        et = (EditText)findViewById(R.id.et_Solucion_SHA);
        tv = (TextView)findViewById(R.id.tv_explicacion_SHA);
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

    public void Generar(View view){
        String s = et.getText().toString();
        String mensaje = encriptar(s);
        et.setText(mensaje);
    }

    public static String encriptar(String s) {
        //Arreglo de bytes
        byte[] block = new byte[128];
        //el metodo format regresa un arreglo de bites ya con el tamaÃ±o de 1024 bits
        byte[] cadena = format(s.getBytes());
        //primeros 32 bits de la parte decimal de las raÃ­ces cÃºbicas de los 64 primeros nÃºmeros primos del intervalo [2,311]
        long[] K = {
                0x428A2F98D728AE22L, 0x7137449123EF65CDL, 0xB5C0FBCFEC4D3B2FL,
                0xE9B5DBA58189DBBCL, 0x3956C25BF348B538L, 0x59F111F1B605D019L,
                0x923F82A4AF194F9BL, 0xAB1C5ED5DA6D8118L, 0xD807AA98A3030242L,
                0x12835B0145706FBEL, 0x243185BE4EE4B28CL, 0x550C7DC3D5FFB4E2L,
                0x72BE5D74F27B896FL, 0x80DEB1FE3B1696B1L, 0x9BDC06A725C71235L,
                0xC19BF174CF692694L, 0xE49B69C19EF14AD2L, 0xEFBE4786384F25E3L,
                0x0FC19DC68B8CD5B5L, 0x240CA1CC77AC9C65L, 0x2DE92C6F592B0275L,
                0x4A7484AA6EA6E483L, 0x5CB0A9DCBD41FBD4L, 0x76F988DA831153B5L,
                0x983E5152EE66DFABL, 0xA831C66D2DB43210L, 0xB00327C898FB213FL,
                0xBF597FC7BEEF0EE4L, 0xC6E00BF33DA88FC2L, 0xD5A79147930AA725L,
                0x06CA6351E003826FL, 0x142929670A0E6E70L, 0x27B70A8546D22FFCL,
                0x2E1B21385C26C926L, 0x4D2C6DFC5AC42AEDL, 0x53380D139D95B3DFL,
                0x650A73548BAF63DEL, 0x766A0ABB3C77B2A8L, 0x81C2C92E47EDAEE6L,
                0x92722C851482353BL, 0xA2BFE8A14CF10364L, 0xA81A664BBC423001L,
                0xC24B8B70D0F89791L, 0xC76C51A30654BE30L, 0xD192E819D6EF5218L,
                0xD69906245565A910L, 0xF40E35855771202AL, 0x106AA07032BBD1B8L,
                0x19A4C116B8D2D0C8L, 0x1E376C085141AB53L, 0x2748774CDF8EEB99L,
                0x34B0BCB5E19B48A8L, 0x391C0CB3C5C95A63L, 0x4ED8AA4AE3418ACBL,
                0x5B9CCA4F7763E373L, 0x682E6FF3D6B2B8A3L, 0x748F82EE5DEFB2FCL,
                0x78A5636F43172F60L, 0x84C87814A1F0AB72L, 0x8CC702081A6439ECL,
                0x90BEFFFA23631E28L, 0xA4506CEBDE82BDE9L, 0xBEF9A3F7B2C67915L,
                0xC67178F2E372532BL, 0xCA273ECEEA26619CL, 0xD186B8C721C0C207L,
                0xEADA7DD6CDE0EB1EL, 0xF57D4F7FEE6ED178L, 0x06F067AA72176FBAL,
                0x0A637DC5A2C898A6L, 0x113F9804BEF90DAEL, 0x1B710B35131C471BL,
                0x28DB77F523047D84L, 0x32CAAB7B40C72493L, 0x3C9EBE0A15C9BEBCL,
                0x431D67C49C100D4CL, 0x4CC5D4BECB3E42B6L, 0x597F299CFC657E2AL,
                0x5FCB6FAB3AD6FAECL, 0x6C44198C4A475817L
        };
        //primeros 32 bits de la parte decimal de las raÃ­ces cuadradas de los primeros nÃºmeros primos del intervalo [2,19]
        long[] H = {
                0x6A09E667F3BCC908L, 0xBB67AE8584CAA73BL,
                0x3C6EF372FE94F82BL, 0xA54FF53A5F1D36F1L,
                0x510E527FADE682D1L, 0x9B05688C2B3E6C1FL,
                0x1F83D9ABFB41BD6BL, 0x5BE0CD19137E2179L
        };

        for (int i = 0; i < cadena.length / 128; i++) {
            long[] w = new long[80];
            long a = H[0], b = H[1], c = H[2], d = H[3], e = H[4], f = H[5], g = H[6], h = H[7], T1, T2;

            System.arraycopy(cadena, 128 * i, block, 0, 128);
            for (int j = 0; j < 16; j++) {
                w[j] = 0;
                for (int k = 0; k < 8; k++) {
                    w[j] |= ((block[j * 8 + k] & 0x00000000000000FFL) << (56 - k * 8));
                }
            }
            for(int j = 0;j<128;j++)
                System.out.print(block[j]+" ");
            System.out.println();
            for(int j = 0;j<80;j++)
                System.out.print(w[j]+" ");
            System.out.println();
            for (int j = 16; j < 80; j++) {
                w[j] = Sigma1(w[j-2]) + w[j-7] + Sigma0(w[j-15]) + w[j-16];
            }

            for (int j = 0; j < 80; j++) {
                T1 = h + Ch(e, f, g) + Sum1(e) + w[j] + K[j];
                T2 = Sum0(a) + Maj(a, b, c);
                h = g;
                g = f;
                f = e;
                e = d + T1;
                d = c;
                c = b;
                b = a;
                a = T1 + T2;
            }

            H[0] += a;
            H[1] += b;
            H[2] += c;
            H[3] += d;
            H[4] += e;
            H[5] += f;
            H[6] += g;
            H[7] += h;
        }
        String res = "";
        for (int i = 0; i < 8; i++) {
            res+=bytesToHex(longToBytes(H[i]));
        }

        return res;
    }


    private static long Sigma0(long l) {
        return Long.rotateRight(l, 1) ^ Long.rotateRight(l, 8) ^ (l >>> 7);
    }

    private static long Sigma1(long l) {
        return Long.rotateRight(l, 19) ^ Long.rotateRight(l, 61) ^ (l >>> 6);
    }

    private static long Sum0(long a) {
        return Long.rotateRight(a, 28) ^ Long.rotateRight(a, 34) ^ Long.rotateRight(a, 39);
    }

    private static long Sum1(long e) {
        return Long.rotateRight(e, 14) ^ Long.rotateRight(e, 18) ^ Long.rotateRight(e, 41);
    }

    private static long Ch(long e, long f, long g) {
        return (e & f) ^ ((~e) & g);
    }

    private static long Maj(long a, long b, long c) {
        return (a & b) ^ (a & c) ^ (b & c);
    }

    private static byte[] format(byte[] data){
        int largoCadena = data.length;
        int largoFinal = largoCadena % 128;
        int largoTot = 0;
        largoTot = 128 - largoFinal;
        byte[] camino = new byte[largoTot];
        camino[0] = (byte)0x80;
        long lengthInBits = largoCadena * 8;
        for (int i = 0; i < 8; i++) {
            camino[camino.length - 1 - i] = (byte) ((lengthInBits >> (8 * i)));
        }

        byte[] output = new byte[largoCadena + largoTot];
        System.arraycopy(data, 0, output, 0, largoCadena);
        System.arraycopy(camino, 0, output, largoCadena, camino.length);
        return output;
    }

    public static byte[] longToBytes(long l) {
        byte[] b = new byte[8];
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((l >>> (56 - 8 * i)) & 0xffL);
        }
        return b;
    }

    public static String bytesToHex(byte[] b) {
        String s = "";
        for (int i = 0; i < b.length; i++) {
            s += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return s;
    }


}
