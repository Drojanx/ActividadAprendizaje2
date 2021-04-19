package com.programacion.alanz.actividadaprendizaje2.domain;

public class Cuadrilla {
    String cuadrillaId;
    String cuadrillaJefeId;

    public Cuadrilla(String cuadrillaId, String cuadrillaJefeId) {
        this.cuadrillaId = cuadrillaId;
        this.cuadrillaJefeId = cuadrillaJefeId;
    }

    public String getCuadrillaId() {
        return cuadrillaId;
    }

    public void setCuadrillaId(String cuadrillaId) {
        this.cuadrillaId = cuadrillaId;
    }

    public String getCuadrillaJefeId() {
        return cuadrillaJefeId;
    }

    public void setCuadrillaJefeId(String cuadrillaJefeId) {
        this.cuadrillaJefeId = cuadrillaJefeId;
    }

    @Override
    public String toString() {
        return "Cuadrilla{" + "cuadrillaId=" + cuadrillaId + ", cuadrillaJefeId=" + cuadrillaJefeId + '}';
    }
    
}
