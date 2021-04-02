package com.programacion.alanz.actividadaprendizaje2.domain;

/**
 *
 * @author droja
 */
public class Actuacion {
    String actuacionId;
    String actuacionParqueId;
    String actuacionMomento;
    String actuacionDescripcion;
    double actuacionDuracionH;

    public Actuacion(String actuacionId, String actuacionParqueId, String actuacionMomento, String actuacionDescripcion, double actuacionDuracionH) {
        this.actuacionId = actuacionId;
        this.actuacionParqueId = actuacionParqueId;
        this.actuacionMomento = actuacionMomento;
        this.actuacionDescripcion = actuacionDescripcion;
        this.actuacionDuracionH = actuacionDuracionH;
    }

    public String getActuacionId() {
        return actuacionId;
    }

    public void setActuacionId(String actuacionId) {
        this.actuacionId = actuacionId;
    }

    public String getActuacionParqueId() {
        return actuacionParqueId;
    }

    public void setActuacionParqueId(String actuacionParqueId) {
        this.actuacionParqueId = actuacionParqueId;
    }

    public String getActuacionMomento() {
        return actuacionMomento;
    }

    public void setActuacionMomento(String actuacionMomento) {
        this.actuacionMomento = actuacionMomento;
    }

    public String getActuacionDescripcion() {
        return actuacionDescripcion;
    }

    public void setActuacionDescripcion(String actuacionDescripcion) {
        this.actuacionDescripcion = actuacionDescripcion;
    }

    public double getActuacionDuracionH() {
        return actuacionDuracionH;
    }

    public void setActuacionDuracionH(double actuacionDuracionH) {
        this.actuacionDuracionH = actuacionDuracionH;
    }
    
}
