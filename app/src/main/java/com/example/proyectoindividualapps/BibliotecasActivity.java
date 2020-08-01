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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.proyectoindividualapps.adapter.LibrosAdapter;
import com.example.proyectoindividualapps.entity.Libro;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BibliotecasActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView comentarioReserva;
    LibrosAdapter librosAdapter;
    private Spinner spinner;
    private Spinner spinnerAtributo;
    RecyclerView recyclerView;
    private EditText buscador;
    private Button botonBuscarLibro;
    private String atributoEscogido;
    private String bibliotecaEscogida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotecas);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_bibliotecas);
        navigationView = findViewById(R.id.nav_view_bibliotecas);

        //Navigation drawer menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_biblioteca);

        String[] lista = {"CIA","Sociales","Central"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BibliotecasActivity.this,
                android.R.layout.simple_spinner_dropdown_item,lista);
        spinner = findViewById(R.id.spinnerBiblioteca);
        spinner.setAdapter(adapter);
        String[] lista2 = {"Area","Autor","Nombre"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(BibliotecasActivity.this,
                android.R.layout.simple_spinner_dropdown_item,lista2);
        spinnerAtributo = findViewById(R.id.spinnerAtributos);
        spinnerAtributo.setAdapter(adapter2);

        comentarioReserva = findViewById(R.id.comentarioReserva);
        comentarioReserva.setVisibility(View.GONE);

        buscador = findViewById(R.id.buscadorBiblioteca);
        if (buscador.getText().toString().isEmpty()) {
            buscador.setError("No deb estar vacio");
        }
        botonBuscarLibro = findViewById(R.id.buscarBoton);

        recyclerView = findViewById(R.id.recyclerViewBiblioteca);

        //Spinner escucando
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bibliotecaEscogida =spinner.getSelectedItem().toString(); //Linea para obtener el valor
                Log.d("Prueba","Escogio biblioteca" + bibliotecaEscogida);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinnerAtributo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                atributoEscogido = spinnerAtributo.getSelectedItem().toString();
                Log.d("Prueba","Escogio atributo" + atributoEscogido);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        botonBuscarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarLibros();
                Log.d("Prueba","Entra a void");
            }
        });

    }


    public void buscarLibros(){

        //Valores spinners
        bibliotecaEscogida =spinner.getSelectedItem().toString();
        atributoEscogido = spinnerAtributo.getSelectedItem().toString();


        //Obtengo las referencia a la biblioteca
        final DatabaseReference biblioteca = FirebaseDatabase.getInstance().getReference().child("Universidad");
        Log.d("Prueba","Escogio biblioteca FB "+ biblioteca);

        //Creo el arraylist de libros
        final ArrayList<Libro> listaLibrosCoincidentes = new ArrayList<>();

        Log.d("A ver: ", biblioteca.child("Bibliotecas").equalTo(bibliotecaEscogida).toString());

        //Empiezo a buscar a que biblioteca pertenece
        biblioteca.orderByChild("Bibliotecas").equalTo(bibliotecaEscogida).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotBiblioteca) {

                    Log.d("b",bibliotecaEscogida);
                        Log.d("Prueba ","Llego adentro de biblioteca");
                        //Obtengo los libros y revistas
                        final DatabaseReference librosBiblioteca = biblioteca.child("Bibliotecas").child(bibliotecaEscogida).child("Libros");

                        Log.d("Prueba ","Escogio "+ librosBiblioteca);

                        //Busco en libros
                        librosBiblioteca.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("LLego ", "Debe listar todos los libros");
                                for(DataSnapshot keyId: dataSnapshot.getChildren()){

                                    if((keyId.child(atributoEscogido).getValue().toString()).equals(buscador.getText().toString())){
                                        Libro libro = new Libro();
                                        libro.setAutor(keyId.child("Autor").getValue(String.class));
                                        libro.setPrestados(keyId.child("Prestados").getValue(Integer.class));
                                        libro.setDisponibles(keyId.child("Disponibles").getValue(Integer.class));
                                        libro.setNombre(keyId.child("Nombre").getValue(String.class));
                                        libro.setArea(keyId.child("Area").getValue(String.class));
                                        listaLibrosCoincidentes.add(libro);
                                        Log.d("Prueba","Llego a tener el libro");
                                    }
                                    Log.d("Prueba","No hay libro");
                                }
                                librosAdapter = new LibrosAdapter(listaLibrosCoincidentes,BibliotecasActivity.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(BibliotecasActivity.this));
                                recyclerView.setAdapter(librosAdapter);
                                Log.d("Prueba","Final");

                                librosAdapter.setOnItemClickListener(new LibrosAdapter.OnItemClickListener() {
                                    @Override
                                    public void reservarClick(int position) {
                                        comentarioReserva.setVisibility(View.VISIBLE);
                                        listaLibrosCoincidentes.get(position).botonReservar(listaLibrosCoincidentes.get(position));
                                        String libroAReservar = listaLibrosCoincidentes.get(position).getNombre();
                                        librosAdapter.notifyItemChanged(position);
                                        librosBiblioteca.child(libroAReservar).child("Prestados").setValue(listaLibrosCoincidentes.get(position).getPrestados());
                                        librosBiblioteca.child(libroAReservar).child("Disponibles").setValue(listaLibrosCoincidentes.get(position).getDisponibles());

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseErrorBiblioteca) {

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
                Intent intent = new Intent(BibliotecasActivity.this,NoticiasActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_home:
                Intent intent2 = new Intent(BibliotecasActivity.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(BibliotecasActivity.this, ActivityProhibida.class));
                break;
            case R.id.nav_cafeteria:
                Intent intent3 = new Intent(BibliotecasActivity.this,CafeteriasActivity.class);
                startActivity(intent3);
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}