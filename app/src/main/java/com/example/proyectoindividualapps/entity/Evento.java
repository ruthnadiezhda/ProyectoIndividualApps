package com.example.proyectoindividualapps.entity;

public class Evento {
    private String introduccion;
    private String texto;
    private Integer preferencial;
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIntroduccion() {
        return introduccion;
    }

    public void setIntroduccion(String introduccion) {
        this.introduccion = introduccion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getPreferencial() {
        return preferencial;
    }

    public void setPreferencial(Integer preferencial) {
        this.preferencial = preferencial;
    }
}
