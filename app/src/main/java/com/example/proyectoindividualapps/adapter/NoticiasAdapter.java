package com.example.proyectoindividualapps.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoindividualapps.DetallesNoticias;
import com.example.proyectoindividualapps.R;
import com.example.proyectoindividualapps.entity.Noticias;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {
    private ArrayList<Noticias> listaNoticias;
    private static Context context;


    public NoticiasAdapter(ArrayList<Noticias> noticiasData, Context context){
        this.listaNoticias = noticiasData;
        this.context = context;

    }

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder{

        public TextView titulo;
        public Button verMas;
        public Noticias noticias;


        public NoticiasViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            this.titulo=itemView.findViewById(R.id.tituloNoticiaRV);
            this.verMas = itemView.findViewById(R.id.detallesNoticiasRV);
        }


        public void asignarDatos(Noticias noti) {
            titulo.setText(noti.getTitulo());
        }

        public void botonDetalles(final Noticias noti) {


            try{
                verMas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, DetallesNoticias.class);
                        intent.putExtra("Foto", noti.getFotos());
                        intent.putExtra("Titulo",noti.getTitulo());

                        context.startActivity(intent);
                    }
                });
            }catch (NullPointerException exception){
                    Log.d("No salio", "Sigue intentando");
            }
        }

    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.noticias_rv, parent, false);


        NoticiasViewHolder noticiasViewHolder = new NoticiasViewHolder(itemView,context);
        return noticiasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position) {

        holder.asignarDatos(listaNoticias.get(position));
        holder.botonDetalles(listaNoticias.get(position));
    }


    @Override
    public int getItemCount() {
        if (listaNoticias != null){
            return listaNoticias.size();
        } else {
            return 0;
        }
    }
}
