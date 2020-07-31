package com.example.proyectoindividualapps.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoindividualapps.BibliotecasActivity;
import com.example.proyectoindividualapps.LoginActivity;
import com.example.proyectoindividualapps.R;
import com.example.proyectoindividualapps.entity.Libro;

import java.util.ArrayList;

public class LibrosAdapter extends RecyclerView.Adapter<LibrosAdapter.LibrosViewHolder> {


    private ArrayList<Libro> listaLibros;
    private static Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void reservarClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public LibrosAdapter(ArrayList<Libro> libroData, Context context){
        this.listaLibros = libroData;
        this.context = context;

    }

    public static class LibrosViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public Button reservar;
        public Libro libro;
        //Context context;


        public LibrosViewHolder(View itemView, Context context, final OnItemClickListener listener) {
            super(itemView);
            // this.context= context;
            this.textView1 = itemView.findViewById(R.id.nombreLibro);
            this.textView2 = itemView.findViewById(R.id.numeroDisponibles);
            this.textView3 = itemView.findViewById(R.id.areaBiblioteca);
            this.reservar =itemView.findViewById(R.id.reservarLibro);

            reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.reservarClick(position);
                        }
                    }
                }
            });
        }

        public void asignarDatos(Libro libro) {
            textView1.setText(libro.getNombre());
            textView2.setText(libro.getDisponibles());
            textView3.setText(libro.getArea());
        }

    }

    @NonNull
    @Override
    public LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.libros_rv, parent, false);
        LibrosViewHolder librosViewHolder = new LibrosViewHolder(itemView,context,mListener);
        return librosViewHolder;



    }

    @Override
    public void onBindViewHolder(@NonNull LibrosViewHolder holder, int position) {
        holder.asignarDatos(listaLibros.get(position));
    }


    @Override
    public int getItemCount() {
        if (listaLibros != null){
            return listaLibros.size();
        } else {
            return 0;
        }
    }


}
