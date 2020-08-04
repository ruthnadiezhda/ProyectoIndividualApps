package com.example.proyectoindividualapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.proyectoindividualapps.adapter.EventosAdapter;
import com.example.proyectoindividualapps.adapter.NoticiasAdapter;
import com.example.proyectoindividualapps.entity.Noticias;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticiasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerViewNoticias;
    RecyclerView recyclerViewEventos;
    private String nombreNoti;
    private String autorNoti;
    private String introNoti;
    private String textoNoti;
    private String fotoNoti;
    private String tituloEve;
    private String fechaEve;
    private String introEve;
    private String textoEve;
    NoticiasAdapter noticiasAdapter;
    EventosAdapter eventosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_noticias);
        navigationView = findViewById(R.id.nav_view_noticias);

        //Navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_noticias);

        recyclerViewNoticias = findViewById(R.id.recyclerViewNoticias);
        recyclerViewNoticias();
        recyclerViewEventos = findViewById(R.id.recyclerViewEventos);
        recyclerViewEventos();


        final DatabaseReference databaseReferenceEventos = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Eventos");

        databaseReferenceEventos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void recyclerViewEventos() {
        recyclerViewNoticias.setHasFixedSize(true);
        recyclerViewNoticias.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));



    }

    private void recyclerViewNoticias() {
        recyclerViewNoticias.setHasFixedSize(true);
        recyclerViewNoticias.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        final DatabaseReference databaseReferenceNoticias = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Noticias");

        databaseReferenceNoticias.addValueEventListener(new ValueEventListener() {
            ArrayList<Noticias> noticiasArrayList =new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot keyID : dataSnapshot.getChildren()){
                    Noticias noticias = new Noticias();
                    introNoti= keyID.child("Introduccion").getValue(String.class);
                    textoNoti = keyID.child("Texto").getValue(String.class);
                    autorNoti = keyID.child("Autor").getValue(String.class);
                    fotoNoti = keyID.child("Foto").getValue(String.class);
                    nombreNoti = keyID.getKey().toString();

                    noticias.setAutor(autorNoti);
                    noticias.setIntroduccion(introNoti);
                    noticias.setTexto(textoNoti);
                    noticias.setTitulo(nombreNoti);
                    noticias.setFotos(fotoNoti);

                    noticiasArrayList.add(noticias);
                }

                noticiasAdapter =new NoticiasAdapter(noticiasArrayList,NoticiasActivity.this);
                recyclerViewNoticias.setHasFixedSize(true);
                recyclerViewNoticias.setLayoutManager(new LinearLayoutManager(NoticiasActivity.this));
                recyclerViewNoticias.setAdapter(noticiasAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_cafeteria:
                Intent intent = new Intent(NoticiasActivity.this,CafeteriasActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_home:
                Intent intent2 = new Intent(NoticiasActivity.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(NoticiasActivity.this, ActivityProhibida.class));
                break;
            case R.id.nav_biblioteca:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(NoticiasActivity.this, BibliotecasActivity.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}