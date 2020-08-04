package com.example.proyectoindividualapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class DetallesEventos extends AppCompatActivity {

    private String tit;
    private Integer pref;

    private TextView titulo;
    private TextView fecha;
    private TextView texto;
    private TextView introduccion;
    public Button reservar;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_eventos);

        Intent eve = getIntent();
        pref = eve.getIntExtra("Preferencial",0);
        tit= eve.getStringExtra("Titulo");


        DatabaseReference eventosRef = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Eventos");

        titulo =findViewById(R.id.tituloEvento);
        fecha= findViewById(R.id.fechaEvento);
        texto = findViewById(R.id.textoEvento);
        introduccion = findViewById(R.id.introduccionEvento);

        firebaseAuth = FirebaseAuth.getInstance();

        eventosRef.addValueEventListener(new ValueEventListener() {
            String tituloE;
            String introE;
            String textoE;
            String fechaE;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (keyId.getKey().equals(tit)) {
                        tituloE = keyId.getKey().toString();
                        introE = keyId.child("Introduccion").getValue(String.class);
                        textoE = keyId.child("Texto").getValue(String.class);
                        fechaE = keyId.child("Fecha").getValue(String.class);
                        break;
                    }
                }

                titulo.setText(tituloE);
                fecha.setText(fechaE);
                introduccion.setText(introE);
                texto.setText(textoE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}