package com.programacion.alanz.actividadaprendizaje2.domain;

public class Ciudad {
    
    private String ciudadId;
    private String ciudadNombre;
    private String ciudadCcaa;

    public Ciudad(String ciudadId, String ciudadNombre, String ciudadCcaa) {
        this.ciudadId = ciudadId;
        this.ciudadNombre = ciudadNombre;
        this.ciudadCcaa = ciudadCcaa;
    }

    public String getCiudadId() {
        return ciudadId;
    }

    public void setCiudadId(String ciudadId) {
        this.ciudadId = ciudadId;
    }

    public String getCiudadNombre() {
        return ciudadNombre;
    }

    public void setCiudadNombre(String ciudadNombre) {
        this.ciudadNombre = ciudadNombre;
    }

    public String getCiudadCcaa() {
        return ciudadCcaa;
    }

    public void setCiudadCcaa(String ciudadCcaa) {
        this.ciudadCcaa = ciudadCcaa;
    }

    @Override
    public String toString() {
        return "Ciudad{" + "ciudadId=" + ciudadId + ", ciudadNombre=" + ciudadNombre + ", ciudadCcaa=" + ciudadCcaa + '}';
    }
    
    
}
