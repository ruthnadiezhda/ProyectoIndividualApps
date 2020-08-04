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

import com.example.proyectoindividualapps.DetallesEventos;
import com.example.proyectoindividualapps.DetallesNoticias;
import com.example.proyectoindividualapps.R;
import com.example.proyectoindividualapps.entity.Evento;
import com.example.proyectoindividualapps.entity.Noticias;

import java.util.ArrayList;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventosViewHolder> {

    private ArrayList<Evento> listaEventos;
    private static Context context;


    public EventosAdapter(ArrayList<Evento> eventosData, Context context){
        this.listaEventos= eventosData;
        this.context = context;

    }

    public static class EventosViewHolder extends RecyclerView.ViewHolder{

        public TextView titulo;
        public TextView intro;
        public TextView fecha;
        public Button verMas;
        public Evento evento;


        public EventosViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            this.titulo=itemView.findViewById(R.id.tituloEventoRV);
            this.intro = itemView.findViewById(R.id.introduccionEventoRV);
            this.verMas = itemView.findViewById(R.id.detallesEventoRV);
            this.fecha = itemView.findViewById(R.id.fechaEventoRV);
        }


        public void asignarDatos(Evento event) {
            titulo.setText(event.getTitulo());
            intro.setText(event.getIntroduccion());
            fecha.setText(event.getFecha());
        }

        public void botonDetalles(final Evento evento) {

            try{
                verMas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, DetallesEventos.class);
                        intent.putExtra("Titulo",evento.getTitulo());
                        intent.putExtra("Preferencial",evento.getPreferencial());

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
    public EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.eventos_rv, parent, false);

        EventosViewHolder eventosViewHolder = new EventosViewHolder(itemView,context);
        return eventosViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventosViewHolder holder, int position) {
        holder.asignarDatos(listaEventos.get(position));
        holder.botonDetalles(listaEventos.get(position));
    }


    @Override
    public int getItemCount() {
        if (listaEventos != null){
            return listaEventos.size();
        } else {
            return 0;
        }
    }
}
