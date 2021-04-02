package com.programacion.alanz.actividadaprendizaje2;

import com.programacion.alanz.actividadaprendizaje2.dao.CiudadDAO;
import com.programacion.alanz.actividadaprendizaje2.dao.Conexion;
import com.programacion.alanz.actividadaprendizaje2.dao.ParqueDAO;
import com.programacion.alanz.actividadaprendizaje2.domain.Parque;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author droja
 */
public class BBDD {
    
    private Conexion conexion;
    private boolean salir;
    private Scanner teclado;
    private ParqueDAO parqueDAO;
    private CiudadDAO ciudadDAO;
    
    public BBDD(){
        salir = false;
        teclado = new Scanner(System.in);
        conexion = new Conexion();
        conexion.conectar();
        parqueDAO = new ParqueDAO(conexion);
        ciudadDAO = new CiudadDAO(conexion);
    }
    
    public void ejecutar(){ /*Menú de consola*/
        teclado = new Scanner(System.in);
        do {
            System.out.println("Aplicación ParKGestor");
            System.out.println("1. Buscar parques");
            System.out.println("2. Registrar parque");
            System.out.println("3. Modificar parque");
            System.out.println("4. Eliminar parque");
            System.out.println("x. Salir");
            System.out.println("Selecciona: ");
            String opcion = teclado.nextLine();
            
            switch(opcion){
                case "1":
                    listarParques();
                    break;
                case "2":
                    registrarParque();
                    break;
                case "3":
                    modificarParque();
                    break;
                case "4":
                    eliminarParque();
                    break;
                case "x":
                    salir();
                    break;
                default:
                    System.out.println("Opcion incorrecta");
            }
                 
        }while(!salir);
    }
    
    private void salir(){
        salir = true;
    }
    
    //Preguntará una ciudad/ccaa (dependiendo del valor de "opcion") hasta que reciba una correcta, la cuál devovlera como String.
    private String existeCiudadCcaa(String ciudadCcaa, String opcion){ 
        try{
            ciudadDAO.existeCiudadCcaa(ciudadCcaa, opcion);
            while(!ciudadDAO.existeCiudadCcaa(ciudadCcaa, opcion)){
                System.out.println("La ciudad/ccaa introducida no aparece en la base de datos. Por favor, introudce otra: ");
                ciudadCcaa = teclado.nextLine().toUpperCase();
                ciudadDAO.existeCiudadCcaa(ciudadCcaa, opcion);
            }
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }  
        return ciudadCcaa;
    }
    
    private void listarParques(){
        System.out.println("Listar por: ");
        System.out.println("    1.Ciudad");
        System.out.println("    2.CCAA");
        String opcion = teclado.nextLine();
        String sql;
        //Si opcion es 1 veremos por ciudades, si es 2 por CCAA
        switch (opcion){
            case "1":
                sql = "SELECT parque_nombre FROM parques WHERE id_ciudad = ? ORDER BY parque_nombre";
                //Guardamos el valor de opcion para verParques()
                verParques(sql, opcion);
                break;
            case "2":
                sql ="SELECT p.parque_nombre FROM parques p INNER JOIN ciudades c ON p.id_ciudad in (SELECT c.id_ciudad from ciudades WHERE c.ccaa = ?)ORDER BY p.parque_nombre";
                //Guardamos el valor de opcion para verParques()
                verParques(sql, opcion);
                break;
            default:
                System.out.println("Opcion incorrecta");
        }
    }
    
    //Dependiendo de si la opcion es 1 o 2, verá si la ciudad o la ccaa existe y, en caso positivo, los mostrará.
    private void verParques(String sql, String opcion){
        try{
            if(!opcion.equals("1")){
                System.out.println("Escriba la CCAA: ");
            } else {
                System.out.println("Escriba la abreviatura: ");
            }
            String abreviatura = teclado.nextLine().toUpperCase();
            String idCiudadCcaa = existeCiudadCcaa(abreviatura, opcion);
            ArrayList<Parque> parques = parqueDAO.obtenerParques(idCiudadCcaa, sql);
            for (Parque parque : parques){
                System.out.print("       ");
                System.out.println(parque.getParqueNombre());
            }
            System.out.println("");
        }
        catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
    }
    
   
    private void registrarParque(){
        //Al registrar sólo sobre ciudades, la opcion de existeCiudadCcaa será siempre 1, para ciudad.
        final String opcion = "1"; 
        
        System.out.println("¿En qué ciudad quieres registar el parque?(ID): ");
        String ciudadExiste = teclado.nextLine().toUpperCase();
        String idCiudad = existeCiudadCcaa(ciudadExiste, opcion);
        System.out.println("ID del parque: ");
        String id = teclado.nextLine();
        System.out.println("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.println("Extension: ");
        String extension = teclado.nextLine();
        //TODO Solicitar resto de campos
        Parque parque = new Parque();
        parque.setParqueId(id);
        parque.setParqueCiudadId(idCiudad);
        parque.setParqueNombre(nombre);
        parque.setParqueExtension(extension);
        try{
        parqueDAO.registrarParque(parque);
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
    }
    
    private void registrarParqueCiudad(){
        System.out.println("En qué ciudad quieres registrar el nuevo parque?: ");
        String ciudad = teclado.nextLine();
    }
    
    private void modificarParque(){
        
    }
    
    private void eliminarParque(){
        System.out.println("ID?: ");
        String id = teclado.nextLine();
        
        try{
            parqueDAO.eliminarParque(id);
            System.out.println("El parque se ha eliminado correctamente");    
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }        
    }
}
