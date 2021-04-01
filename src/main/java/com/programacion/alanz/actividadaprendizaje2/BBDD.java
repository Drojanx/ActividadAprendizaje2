package com.programacion.alanz.actividadaprendizaje2;

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
    
    public BBDD(){
        salir = false;
        teclado = new Scanner(System.in);
        conexion = new Conexion();
        conexion.conectar();
        parqueDAO = new ParqueDAO(conexion);
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
    
    /*private void buscarParques(){
        try{
            ArrayList<Parque> parques = parqueDAO.obtenerParques();
            for (Parque parque : parques){
                System.out.println(parque);
            }
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
        
    }*/
    
    private void listarParques(){
        System.out.println("Listar por: ");
        System.out.println("    1.Ciudad");
        System.out.println("    2.CCAA");
        String opcion = teclado.nextLine();
        String sql;
        switch (opcion){
            case "1":
                sql = "SELECT parque_nombre FROM parques WHERE id_ciudad = ? ORDER BY parque_nombre";
                verParques(sql);
                break;
            case "2":
                sql ="SELECT p.parque_nombre FROM parques p INNER JOIN ciudades c ON p.id_ciudad in (SELECT c.id_ciudad from ciudades WHERE c.ccaa = ?)ORDER BY p.parque_nombre";
                verParques(sql);
                break;
            default:
                System.out.println("Opcion incorrecta");
        }
    }
    
    private void verParques(String sql){
        try{
            System.out.println("Escriba la abreviatura de la ciudad: ");
            String abreviatura = teclado.nextLine();
            ArrayList<Parque> parques = parqueDAO.obtenerParques(abreviatura, sql);
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
        System.out.println("ID");
        String id = teclado.nextLine();
        System.out.println("ID Ciudad");
        String idCiudad = teclado.nextLine();
        System.out.println("Nombre");
        String nombre = teclado.nextLine();
        //TODO Solicitar resto de campos
        Parque parque = new Parque();
        parque.setParqueId(id);
        parque.setParqueCiudadId(idCiudad);
        parque.setParqueNombre(nombre);
        
        try{
        parqueDAO.registrarParque(parque);
        System.out.println("El coche se ha registrado correctamente");
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
