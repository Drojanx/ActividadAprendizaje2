package com.programacion.alanz.actividadaprendizaje2.domain;

/**
 *
 * @author droja
 */
public class Parque {
    
    private String parqueId;
    private String parqueCiudadId;
    private String parqueNombre;
    private String parqueExtension;

    public Parque() {
    }

    public String getParqueId() {
        return parqueId;
    }

    public void setParqueId(String parqueId) {
        this.parqueId = parqueId;
    }

    public String getParqueCiudadId() {
        return parqueCiudadId;
    }

    public void setParqueCiudadId(String parqueCiudadId) {
        this.parqueCiudadId = parqueCiudadId;
    }

    public String getParqueNombre() {
        return parqueNombre;
    }

    public void setParqueNombre(String parqueNombre) {
        this.parqueNombre = parqueNombre;
    }

    public String getParqueExtension() {
        return parqueExtension;
    }

    public void setParqueExtension(String parqueExtension) {
        this.parqueExtension = parqueExtension;
    }

    @Override
    public String toString() {
        return "Parque{" + "idParque=" + parqueId + ", idCiudad=" + parqueCiudadId + ", nombre=" + parqueNombre + ", extension=" + parqueExtension + '}';
    }
    


    
    

   
    
}
