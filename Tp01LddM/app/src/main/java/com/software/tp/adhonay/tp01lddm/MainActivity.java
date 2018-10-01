package com.software.tp.adhonay.tp01lddm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.opengl.GLSurfaceView;

public class MainActivity extends AppCompatActivity
{


    private EditText edtNome;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickConfirmar(View v)
    {
        Intent intent = new Intent();
        intent.setClass(this, TextActivity.class);
        Bundle bundle = new Bundle();

         /*
         * EditText
         */
        edtNome    = (EditText)findViewById(R.id.edNome);
        edtPhone   = (EditText)findViewById(R.id.edPhone);
        edtEmail   = (EditText)findViewById(R.id.edEMail);
        edtAddress = (EditText)findViewById(R.id.edAddress);



        //String with message value
        String nome    =  edtNome.getText().toString();
        String phone   =  edtPhone.getText().toString();
        String email   =  edtEmail.getText().toString();
        String address =  edtAddress.getText().toString();


        /*
         * Chama um DialogALert caso algum campo nao tenha
         * sido preenchido corretamente.
         */
        if( nome.matches("") || phone.matches("") || email.matches("") || address.matches("") || !veryField(email) ||
                !minLength(email, 7) || !minLength(phone, 11)    )
        {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
            alertdialog.setMessage("Preencha todos os campos corretamente").create();
            alertdialog.show();
        }
        else
        {
            bundle.putString("EXTRA_NAME",  nome);
            bundle.putString("EXTRA_PHONE", phone);
            bundle.putString("EXTRA_EMAIL", email);
            bundle.putString("EXTRA_ADDRESS", address);
            intent.putExtras(bundle);
            startActivity(intent);
        }


    }


    public void onClickCancelar(View v)
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Cancelar");
        alertDialog.setMessage("Deseja sair?");
        alertDialog.create();

        alertDialog.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.setPositiveButton("Nao", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

    /**
     * Campo de e-mail
     * @param email
     * @return
     */
    public boolean pressingPattern(String email)
    {
        boolean resp = false;
        if(email.contentEquals("@"))
            resp = true;
        return resp;

    }


    /**
     * Redirecionamento para site
     * @param v
     */
    public void onClickImage(View v)
    {
        ImageView imageView = (ImageView)findViewById(R.id.imgHead);
        Intent intent  = new Intent();

        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://www.pucminas.br"));
    }

    /**
     * Tamanho minino para campos
     * @param texto
     * @param x
     * @return true se os campos estiverem incorretos.
     */
    public boolean minLength( String texto, int x )
    {
        boolean resp = false;
        if(texto.length() >= x)
        {
            resp = true;
        }
        return resp;
    }

    public static boolean veryField(String string)
    {
        boolean resp = false;
        int x = 0;
        int tamanho = string.length();
        while(x<tamanho)
        {
            if( string.charAt(x) == '@')
            {
                resp = true;
                break;
            }
            x++;
        }
        return resp;
    }


}
