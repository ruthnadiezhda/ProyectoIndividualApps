package com.example.proyectoindividualapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

public class DetallesNoticias extends AppCompatActivity {

    private String foto;
    private String tit;
    private TextView titulo;
    private TextView autor;
    private TextView texto;
    private TextView introduccion;
    private ImageView imagen;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_noticias);

        Intent noti = getIntent();
        foto = noti.getStringExtra("Foto");
        tit = noti.getStringExtra("Titulo");


        DatabaseReference noticiasRef = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Noticias");
        storageReference = FirebaseStorage.getInstance().getReference();

        titulo =findViewById(R.id.tituloNoticia);
        autor= findViewById(R.id.autorNoticia);
        texto = findViewById(R.id.textoNoticia);
        introduccion = findViewById(R.id.introduccionNoticia);
        imagen = findViewById(R.id.fotoNoticia);

        firebaseAuth = FirebaseAuth.getInstance();

        noticiasRef.addValueEventListener(new ValueEventListener() {
            String tituloN;
            String autorN;
            String introN;
            String textoN;
            String fotoN;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (keyId.getKey().equals(tit)) {
                        tituloN = keyId.getKey().toString();
                        introN = keyId.child("Introduccion").getValue(String.class);
                        textoN = keyId.child("Texto").getValue(String.class);
                        autorN = keyId.child("Autor").getValue(String.class);
                        fotoN = keyId.child("Foto").getValue(String.class);
                        break;
                    }
                }

                    titulo.setText(tituloN);
                    autor.setText(autorN);
                    introduccion.setText(introN);
                    texto.setText(textoN);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        StorageReference fotoReference =storageReference.child("fotosNoticias/"+ foto);
        Glide.with(this).load(fotoReference).into(imagen);

    }
}