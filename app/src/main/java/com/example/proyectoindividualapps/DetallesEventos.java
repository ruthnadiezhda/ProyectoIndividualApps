package com.example.proyectoindividualapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        firebaseAuth = FirebaseAuth.getInstance();

        Intent eve = getIntent();
        pref = eve.getIntExtra("Preferencial",0);
        tit= eve.getStringExtra("Titulo");


        final DatabaseReference eventosRef = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Eventos");

        titulo =findViewById(R.id.tituloEvento);
        fecha= findViewById(R.id.fechaEvento);
        texto = findViewById(R.id.textoEvento);
        introduccion = findViewById(R.id.introduccionEvento);
        reservar = findViewById(R.id.reservarEvento);

        firebaseAuth = FirebaseAuth.getInstance();

        eventosRef.addValueEventListener(new ValueEventListener() {
            String tituloE;
            String introE;
            String textoE;
            String fechaE;
            Integer prefeE;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot keyId : dataSnapshot.getChildren()) {
                    if (keyId.getKey().equals(tit)) {
                        tituloE = keyId.getKey().toString();
                        introE = keyId.child("Introduccion").getValue(String.class);
                        textoE = keyId.child("Texto").getValue(String.class);
                        fechaE = keyId.child("Fecha").getValue(String.class);
                        prefeE = keyId.child("Preferenciales").getValue(Integer.class);
                        break;
                    }
                }

                titulo.setText(tituloE);
                fecha.setText(fechaE);
                introduccion.setText(introE);
                texto.setText(textoE);

                reservar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desplegarAviso();
                        Integer preferencialActual = prefeE-1;
                        eventosRef.child(tituloE).child("Preferenciales").setValue(preferencialActual);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void desplegarAviso(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Esta reservando cupo en evento");
        alertDialog.setMessage("Esta reservando un cupo para el evento seleccionado. Para confirmar asistencia debe enviar un correo a la dirección indicada en el enlace principal de la noticia hasta 48h antes");
        alertDialog.setPositiveButton("Reservar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetallesEventos.this, "No se olvide de confirmar asistencia", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.setNegativeButton("Regresar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetallesEventos.this, "Avise a sus compañer@s del evento!", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();
    }
}