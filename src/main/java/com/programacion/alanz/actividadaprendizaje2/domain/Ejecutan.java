package com.programacion.alanz.actividadaprendizaje2.domain;

/**
 *
 * @author droja
 */
public class Ejecutan {
    String ejecutanActuacionId;
    String ejecutanCuadrillaId;
    String ejecutanFecha;
    double ejecutanHoras;

    public Ejecutan(String ejecutanActuacionId, String ejecutanCuadrillaId, String ejecutanFecha, double ejecutanHoras) {
        this.ejecutanActuacionId = ejecutanActuacionId;
        this.ejecutanCuadrillaId = ejecutanCuadrillaId;
        this.ejecutanFecha = ejecutanFecha;
        this.ejecutanHoras = ejecutanHoras;
    }

    public String getEjecutanActuacionId() {
        return ejecutanActuacionId;
    }

    public void setEjecutanActuacionId(String ejecutanActuacionId) {
        this.ejecutanActuacionId = ejecutanActuacionId;
    }

    public String getEjecutanCuadrillaId() {
        return ejecutanCuadrillaId;
    }

    public void setEjecutanCuadrillaId(String ejecutanCuadrillaId) {
        this.ejecutanCuadrillaId = ejecutanCuadrillaId;
    }

    public String getEjecutanFecha() {
        return ejecutanFecha;
    }

    public void setEjecutanFecha(String ejecutanFecha) {
        this.ejecutanFecha = ejecutanFecha;
    }

    public double getEjecutanHoras() {
        return ejecutanHoras;
    }

    public void setEjecutanHoras(double ejecutanHoras) {
        this.ejecutanHoras = ejecutanHoras;
    }

    @Override
    public String toString() {
        return "Ejecutan{" + "ejecutanActuacionId=" + ejecutanActuacionId + ", ejecutanCuadrillaId=" + ejecutanCuadrillaId + ", ejecutanFecha=" + ejecutanFecha + ", ejecutanHoras=" + ejecutanHoras + '}';
    }
    
}
