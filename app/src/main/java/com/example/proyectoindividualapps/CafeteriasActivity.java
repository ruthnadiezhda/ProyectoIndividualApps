package com.example.proyectoindividualapps;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectoindividualapps.entity.Menu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CafeteriasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variables
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private TextView nombreAlmuerzoArtes;
    private TextView nombreCenaArtes;
    private TextView nombreAlmuerzoCentral;
    private TextView nombreCenaCentral;
    private TextView nombreAlmuerzoLetras;
    private TextView nombreCenaLetras;
    private TextView cantidadAlmuerzoArtes;
    private TextView cantidadCenaArtes;
    private TextView cantidadAlmuerzoCentral;
    private TextView cantidadCenaCentral;
    private TextView cantidadAlmuerzoLetras;
    private TextView cantidadCenaLetras;
    private Button reservarAlmuerzoArtes;
    private Button reservarCenaArtes;
    private Button reservarAlmuerzoCentral;
    private Button reservarCenaCentral;
    private Button reservarAlmuerzoLetras;
    private Button reservarCenaLetras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeterias);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_cafeterias);
        navigationView = findViewById(R.id.nav_view_cafeterias);

        //Navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_cafeteria);

        nombreAlmuerzoArtes = findViewById(R.id.nombreAlmuerzoArtes);
        nombreCenaArtes = findViewById(R.id.nombreCenaArtes);
        nombreAlmuerzoCentral = findViewById(R.id.nombreAlmuerzoCentral);
        nombreCenaCentral = findViewById(R.id.nombreCenaCentral);
        nombreAlmuerzoLetras = findViewById(R.id.nombreAlmuerzoLetras);
        nombreCenaLetras = findViewById(R.id.nombreCenaLetras);


        cantidadAlmuerzoArtes = findViewById(R.id.cantidadAlmuerzoArtes);
        cantidadCenaArtes = findViewById(R.id.cantidadCenaArtes);
        cantidadAlmuerzoCentral = findViewById(R.id.cantidadAlmuerzoCentral);
        cantidadCenaCentral = findViewById(R.id.cantidadCenaCentral);
        cantidadAlmuerzoLetras = findViewById(R.id.cantidadAlmuerzoLetras);
        cantidadCenaLetras = findViewById(R.id.cantidadCenaLetras);

        reservarAlmuerzoArtes = findViewById(R.id.reservarArtesAlmuerzo);
        reservarAlmuerzoArtes.setVisibility(View.GONE);
        reservarAlmuerzoCentral = findViewById(R.id.reservarCentralAlmuerzo);
        reservarAlmuerzoCentral.setVisibility(View.GONE);
        reservarAlmuerzoLetras = findViewById(R.id.reservarLetrasAlmuerzo);
        reservarAlmuerzoLetras.setVisibility(View.GONE);
        reservarCenaArtes = findViewById(R.id.reservarArtesCena);
        reservarCenaArtes.setVisibility(View.GONE);
        reservarCenaCentral = findViewById(R.id.reservarCentralCena);
        reservarCenaCentral.setVisibility(View.GONE);
        reservarCenaLetras = findViewById(R.id.reservarLetrasCena);
        reservarCenaLetras.setVisibility(View.GONE);

        final Calendar calendario = new GregorianCalendar();
        final Integer horaparaReservarCena = calendario.get(Calendar.HOUR_OF_DAY);
        Log.d("A ver: ", horaparaReservarCena.toString());




        //Referencia a la incidencia
        final DatabaseReference cafeteriaArtes = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Cafeteria").child("Artes");
        final DatabaseReference cafeteriaCentral = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Cafeteria").child("Central");
        final DatabaseReference cafeteriaLetras = FirebaseDatabase.getInstance().getReference().child("Universidad").child("Cafeteria").child("Letras");

        Log.d("A", cafeteriaArtes.toString());
        cafeteriaArtes.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot keyID : dataSnapshot.getChildren()) {
                    Log.d("Almuerzo: ", keyID.toString());
                    if (keyID.getKey().equals("Almuerzo") && (keyID.toString() != null)) {
                        final Menu menucitoDeAlmuerzitoA = new Menu();

                        menucitoDeAlmuerzitoA.setCantidad(keyID.child("Cantidad").getValue(Integer.class));
                        menucitoDeAlmuerzitoA.setNombre(keyID.child("Nombre").getValue(String.class));

                        nombreAlmuerzoArtes.setText(menucitoDeAlmuerzitoA.getNombre());
                        cantidadAlmuerzoArtes.setText(Integer.toString(menucitoDeAlmuerzitoA.getCantidad()));

                        if (horaparaReservarCena>=9 && horaparaReservarCena<=15) {
                            reservarAlmuerzoArtes.setVisibility(View.VISIBLE);
                            reservarAlmuerzoArtes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Integer menusActuales = menucitoDeAlmuerzitoA.getCantidad() - 1;
                                    menucitoDeAlmuerzitoA.setCantidad(menusActuales);
                                    cafeteriaArtes.child("Almuerzo").child("Cantidad").setValue(menusActuales);
                                }
                            });
                        }

                    } else if (keyID.getKey().equals("Cena") && (keyID.toString() != null)) {
                        final Menu menucitoDeCenitaA = new Menu();
                        menucitoDeCenitaA.setCantidad(keyID.child("Cantidad").getValue(Integer.class));
                        menucitoDeCenitaA.setNombre(keyID.child("Nombre").getValue(String.class));
                        nombreCenaArtes.setText(menucitoDeCenitaA.getNombre());
                        cantidadCenaArtes.setText(Integer.toString(menucitoDeCenitaA.getCantidad()));

                        if (horaparaReservarCena>=18 && horaparaReservarCena<=21) {
                            reservarCenaArtes.setVisibility(View.VISIBLE);
                            reservarCenaArtes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Integer menusActuales = menucitoDeCenitaA.getCantidad() - 1;
                                    menucitoDeCenitaA.setCantidad(menusActuales);
                                    cafeteriaArtes.child("Cena").child("Cantidad").setValue(menusActuales);
                                }
                            });

                        }
                    } else {
                        Log.d("Nada 4", "Nadita en Artes");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        cafeteriaCentral.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot keyID : dataSnapshot.getChildren()){

                    if (keyID.getKey().equals("Almuerzo") && (keyID.toString()!= null)){
                        final Menu menucitoDeAlmuerzitoC = new Menu();
                        menucitoDeAlmuerzitoC.setCantidad(keyID.child("Cantidad").getValue(Integer.class));
                        menucitoDeAlmuerzitoC.setNombre(keyID.child("Nombre").getValue(String.class));
                        nombreAlmuerzoCentral.setText(menucitoDeAlmuerzitoC.getNombre());
                        cantidadAlmuerzoCentral.setText(Integer.toString(menucitoDeAlmuerzitoC.getCantidad()));


                        if (horaparaReservarCena>=9 && horaparaReservarCena<=15) {
                            reservarAlmuerzoCentral.setVisibility(View.VISIBLE);
                            reservarAlmuerzoCentral.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Integer menusActuales = menucitoDeAlmuerzitoC.getCantidad() - 1;
                                    menucitoDeAlmuerzitoC.setCantidad(menusActuales);
                                    cafeteriaCentral.child("Almuerzo").child("Cantidad").setValue(menusActuales);
                                }
                            });
                        }

                    } else if (keyID.getKey().equals("Cena") && (keyID.toString()!= null)){
                        final Menu menucitoDeCenitaC = new Menu();
                        menucitoDeCenitaC.setCantidad(keyID.child("Cantidad").getValue(Integer.class));
                        menucitoDeCenitaC.setNombre(keyID.child("Nombre").getValue(String.class));
                        nombreCenaCentral.setText(menucitoDeCenitaC.getNombre());
                        cantidadCenaCentral.setText(Integer.toString(menucitoDeCenitaC.getCantidad()));

                        if (horaparaReservarCena>=18 && horaparaReservarCena<=21) {
                            reservarCenaCentral.setVisibility(View.VISIBLE);
                            reservarCenaCentral.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Integer menusActuales = menucitoDeCenitaC.getCantidad() - 1;
                                    menucitoDeCenitaC.setCantidad(menusActuales);
                                    cafeteriaCentral.child("Cena").child("Cantidad").setValue(menusActuales);
                                }
                            });
                        }

                    } else {
                        Log.d("Nada 5", "Nadita en Central");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cafeteriaLetras.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot keyID : dataSnapshot.getChildren()){

                    if ((keyID.getKey().equals("Almuerzo")) && (keyID.toString()!= null)){
                        final Menu menucitoDeAlmuerzitoL = new Menu();
                        menucitoDeAlmuerzitoL.setCantidad(keyID.child("Cantidad").getValue(Integer.class));
                        menucitoDeAlmuerzitoL.setNombre(keyID.child("Nombre").getValue(String.class));
                        nombreAlmuerzoLetras.setText(menucitoDeAlmuerzitoL.getNombre());
                        cantidadAlmuerzoLetras.setText(Integer.toString(menucitoDeAlmuerzitoL.getCantidad()));

                        if (horaparaReservarCena>=9 && horaparaReservarCena<=15) {
                            reservarAlmuerzoLetras.setVisibility(View.VISIBLE);
                            reservarAlmuerzoLetras.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Integer menusActuales = menucitoDeAlmuerzitoL.getCantidad() - 1;
                                    menucitoDeAlmuerzitoL.setCantidad(menusActuales);
                                    cafeteriaLetras.child("Almuerzo").child("Cantidad").setValue(menusActuales);
                                }
                            });
                        }

                    } else if (keyID.getKey().equals("Cena") && (keyID.toString()!= null)){
                        final Menu menucitoDeCenitaL = new Menu();
                        menucitoDeCenitaL.setCantidad(keyID.child("Cantidad").getValue(Integer.class));
                        menucitoDeCenitaL.setNombre(keyID.child("Nombre").getValue(String.class));
                        nombreCenaLetras.setText(menucitoDeCenitaL.getNombre());
                        cantidadCenaLetras.setText(Integer.toString(menucitoDeCenitaL.getCantidad()));

                        if (horaparaReservarCena>=18 && horaparaReservarCena<=21) {
                            reservarCenaLetras.setVisibility(View.VISIBLE);
                            reservarCenaLetras.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Integer menusActuales = menucitoDeCenitaL.getCantidad() - 1;
                                    menucitoDeCenitaL.setCantidad(menusActuales);
                                    cafeteriaLetras.child("Cena").child("Cantidad").setValue(menusActuales);
                                }
                            });
                        }

                    } else {
                        Log.d("Nada 6", "Nadita en Letras");
                    }
                }


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
            case R.id.nav_noticias:
                Intent intent = new Intent(CafeteriasActivity.this,NoticiasActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_home:
                Intent intent2 = new Intent(CafeteriasActivity.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CafeteriasActivity.this, ActivityProhibida.class));
                break;
            case R.id.nav_biblioteca:
                startActivity(new Intent(CafeteriasActivity.this, BibliotecasActivity.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }





}