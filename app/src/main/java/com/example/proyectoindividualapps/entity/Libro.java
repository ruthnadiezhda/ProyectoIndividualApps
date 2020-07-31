package com.example.proyectoindividualapps.entity;

import android.view.View;

public class Libro {

    private String area;
    private String nombre;
    private String autor;
    private Integer disponibles;
    private Integer prestados;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

    public Integer getPrestados() {
        return prestados;
    }

    public void setPrestados(Integer prestados) {
        this.prestados = prestados;
    }

    public void botonReservar(Libro librox) {

        Integer prestadosactual = librox.getPrestados()+1;
        Integer restantes = librox.getDisponibles()-1;
        librox.setDisponibles(restantes);
        librox.setPrestados(prestadosactual);
    }
}
