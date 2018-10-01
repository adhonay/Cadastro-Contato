package com.software.tp.adhonay.tp01lddm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.acl.AclNotFoundException;
import java.util.ArrayList;


public class TextActivity extends Activity
{


    private String nome;
    private String phone;
    private String email;
    private String address;
    private ArrayList<String> listContacts;

    private TextView edtNome;
    private TextView edtPhone;
    private TextView edtEmail;
    private TextView edtAddress;
    ImageView edtfoto;
   // ImageView foto;
    Button edtbtfoto;
    static final int REQUEST_IMAGE_CAPTURE = 1;
   // static  final  int CONTACT_SAVE_INTENT_REQUEST =  1 ;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        edtfoto = (ImageView) findViewById(R.id.edtfoto);
        edtbtfoto = (Button) findViewById(R.id.edtbtfoto);

        edtbtfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarintentphoto();
            }
        });

        edtNome    = (TextView) findViewById(R.id.txNome);
        edtPhone   = (TextView) findViewById(R.id.txPhone);
        edtEmail   = (TextView) findViewById(R.id.txEmail);
        edtAddress = (TextView) findViewById(R.id.txAddress);
       // edtfoto = (ImageView) findViewById(R.id.edtfoto);

        nome    = bundle.getString("EXTRA_NAME");
        phone   = bundle.getString("EXTRA_PHONE");
        email   = bundle.getString("EXTRA_EMAIL");
        address = bundle.getString("EXTRA_ADDRESS");




        edtNome.setText(nome);
        edtPhone.setText(phone);
        edtEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        edtEmail.setText(email);
        edtAddress.setText(address);

    }


    public void chamarintentphoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            edtfoto.setImageBitmap(imageBitmap);
        }
    }




    public void onClickVoltar(View v)
    {

        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent,1);

    }


    /**
     * Funcoes para tratamento de click nos
     * ImagesButtons da segunda activity
     * @param v
     */

    public void onClickContacts(View v)
    {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);



        edtEmail = (TextView) findViewById(R.id.txEmail);
        email    =  edtEmail.getText().toString();
        edtPhone = (TextView) findViewById(R.id.txPhone);
        phone    =  edtPhone.getText().toString();
        edtNome  = (TextView) findViewById(R.id.txNome);
        nome     = edtNome.getText().toString();
        edtAddress = (TextView) findViewById((R.id.txAddress));
        address = edtAddress.getText().toString();
       // edtfoto = (ImageView) findViewById(R.id.edtfoto);





          intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email)
                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)//passar email para lista de contato do telefone usando intent
                .putExtra(ContactsContract.Intents.Insert.PHONE, phone)
                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .putExtra(ContactsContract.Intents.Insert.NAME, nome)
                .putExtra(ContactsContract.Intents.Insert.PHONETIC_NAME, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .putExtra(ContactsContract.Intents.Insert.POSTAL , address)
                .putExtra(ContactsContract.Intents.Insert.POSTAL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK);
                //.putExtra(ContactsContract.CommonDataKinds.Photo.PHOTO , edtfoto.toString())
                //.putExtra(ContactsContract.CommonDataKinds.Photo.PHOTO_THUMBNAIL_URI , edtfoto.toString());




        startActivity(intent);



    }

    public void onClickMail(View v)
    {

        edtEmail = (TextView) findViewById(R.id.txAddress);
        email    = edtEmail.getText().toString();


        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Assunto");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Escreva seu e-mail");
        startActivity(Intent.createChooser(emailIntent, "Enviando e-mail..."));
    }

    public void onClickCall(View v)
    {
        // Criando a Intent
        Intent intent = new Intent();


        // Obtendo o texto do View txPhone
        edtPhone = (TextView) findViewById(R.id.txPhone);
        // Parse to String
        phone    = edtPhone.getText().toString();
        // Passando a action e a string para intent
        intent.setData(Uri.parse("tel:" + phone));
        intent.setAction("android.intent.action.DIAL");

        // Iniciando a Intet
        startActivity(intent);

    }

    public void onClickLocalize(View v)
    {
        edtAddress = (TextView) findViewById(R.id.txAddress);
        address    = edtAddress.getText().toString();

        Uri location = Uri.parse("geo:0,0?q="+ address);
        Intent intent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(intent);
    }





    public boolean verifyContacts( String name, String number)
    {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        boolean resp = false;
        while (phones.moveToNext())
        {
            String listName    = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHONETIC_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            if(listName.equals(name) || phoneNumber.equals(number))
            {
                resp = true;
                break;

            }

        }
        phones.close();

        return resp;
    }



}
