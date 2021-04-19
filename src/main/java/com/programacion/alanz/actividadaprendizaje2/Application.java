package com.programacion.alanz.actividadaprendizaje2;

/**
 * @author Alejandro Lanz Echarte
 */
public class Application {

    /**
     * Método que ejecuta todo el programa
     * @param args
     */
    public static void main(String[] args){
        BBDD bbdd = new BBDD();
        bbdd.ejecutar();
    }

}
