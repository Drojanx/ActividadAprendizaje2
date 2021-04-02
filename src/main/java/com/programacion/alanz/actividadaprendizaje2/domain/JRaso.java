package com.programacion.alanz.actividadaprendizaje2.domain;

/**
 *
 * @author droja
 */
public class JRaso extends Jardinero {

    String jRasoJefeId;

    public JRaso(String jRasoJefeId, String jardineroId, String jardineroCuadrillaId, String jardineroNombre, String jardineroApellidos, String jardineroNumTfno, double jardineroSalario, String jardineroFechaContr) {
        super(jardineroId, jardineroCuadrillaId, jardineroNombre, jardineroApellidos, jardineroNumTfno, jardineroSalario, jardineroFechaContr);
        this.jRasoJefeId = jRasoJefeId;
    }

    
    
    

}
