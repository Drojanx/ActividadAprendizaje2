package com.programacion.alanz.actividadaprendizaje2;

import com.programacion.alanz.actividadaprendizaje2.dao.CiudadDAO;
import com.programacion.alanz.actividadaprendizaje2.dao.Conexion;
import com.programacion.alanz.actividadaprendizaje2.dao.ParqueDAO;
import com.programacion.alanz.actividadaprendizaje2.domain.Parque;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Alejandro Lanz
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
    
    /**
     * Menú de consola
     **/
    public void ejecutar(){ 
        do {
            System.out.println("Aplicación ParKGestor");
            System.out.println("1. Buscar parques");
            System.out.println("2. Registrar parque");
            System.out.println("3. Modificar parque");
            System.out.println("4. Eliminar parque");
            System.out.println("5. Nº de parques de una ciudad según extension");
            System.out.println("6. Listar ciudades según la suma total de la extension de sus parques");
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
                    opcionEliminarParque();
                    break;
                case "5":
                    numParquesExMayor();
                    break;
                case "6":
                    ciudadesExtTotal();
                    break;
                case "X":
                case "x":
                    salir();
                    break;
                default:
                    System.out.println("Opcion incorrecta");
            }
                 
        }while(!salir);
    }//Cierre método
    
    /**
     * Cerrar menú de consola
     */
    private void salir(){
        salir = true;
    }//Cierre metodo
    
    /**
     * Llama a los métodos de ciudadDAO.existeParque y parqueDAO.existeParque para determinar 
     * si la CIUDAD, CCAA o PARQUE que se le de existe en la base de datos. Si no existe, 
     * pedirá que introduzcamos otro dato hasta la base de datos. Si no existe, que demos 
     * uno que sí exista, el cuál devolvera como String.
     * 
     * @param ciudadCcaaParque String con la CIUDAD, CCAA o PARQUE que se quiere buscar
     * @param opcion String que determina si la variable que llega en ciudadCcaaParque es
     *               CIUDAD (opcion "1"), CCAA (opcion "2") o PARQUE (opcion "3")
     * @return Devuelve como String la CIUDAD, CCAA o PARQUE que le demos si este existe.
     **/
    private String existeCiudadCcaaParque(String ciudadCcaaParque, String opcion){ 
        try{
            if (!opcion.equals("3")){
                ciudadDAO.existeCiudadCcaa(ciudadCcaaParque, opcion);
                while(!ciudadDAO.existeCiudadCcaa(ciudadCcaaParque, opcion)){
                    System.out.println("La ciudad/ccaa introducida no aparece en la base de datos. Por favor, introudce otra: ");
                    ciudadCcaaParque = teclado.nextLine().toUpperCase();
                    ciudadDAO.existeCiudadCcaa(ciudadCcaaParque, opcion);
                }
            } else{
                parqueDAO.existeParque(ciudadCcaaParque);
                while(!parqueDAO.existeParque(ciudadCcaaParque)){
                    System.out.println("El Parque no aparece en la base de datos. Por favor, introudce otro: ");
                    ciudadCcaaParque = teclado.nextLine().toUpperCase();
                    parqueDAO.existeParque(ciudadCcaaParque);
                }
            }
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }  
        return ciudadCcaaParque;
    }//Cierre metodo
    
    /**
     *Abre un menú para decidir el criterio de búsqueda 
     */
    private void listarParques(){
        System.out.println("Listar por: ");
        System.out.println("    1.Ciudad");
        System.out.println("    2.CCAA");
        System.out.println("    3.Palabras contenidas en su nombre");
        String opcion = teclado.nextLine();
        String sql;
        //Si opcion es 1 veremos los parques por ciudades, si es 2 por CCAA y si es 3, por palabras de su nombre.
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
            case "3":
                sql ="SELECT parque_nombre FROM parques WHERE parque_nombre like ?";
                //Guardamos el valor de opcion para verParques()
                verParques(sql, opcion);
                break;
            default:
                System.out.println("Opcion incorrecta");
        }
    }
    
    /**
     * Gracias al método de parqueDAO.obtenerParques, mostrará por pantalla 
     * los parques que se obtengan según las opciones de búsqueda
     * 
     * @param sql String que almacena la sentencia SQL de búsqueda, variará dependiendo 
     *            de la opción de búsqueda
     * @param opcion String que almacena la opción de búsqueda, "1" para buscar por ciudad,
     *               "2" por CCAA y "3" por palabras en el nombre del parque.
     */
    private void verParques(String sql, String opcion){
        try{
            if(!opcion.equals("3")){
                if(!opcion.equals("1")){
                    System.out.println("Escriba la CCAA: ");            
                } else{
                    System.out.println("Escriba la abreviatura: ");
                } 
                String busqueda = teclado.nextLine().toUpperCase();
                String idCiudadCcaa = existeCiudadCcaaParque(busqueda, opcion);
                ArrayList<Parque> parques = parqueDAO.obtenerParques(idCiudadCcaa, sql);
                for (Parque parque : parques){
                    System.out.print("       ");
                    System.out.println(parque.getParqueNombre());
                }
                System.out.println("");
            } else{
                System.out.println("Escriba la cadena de búsqueda: ");
                String busqueda = "%" + teclado.nextLine().toUpperCase() + "%";
                ArrayList<Parque> parques = parqueDAO.obtenerParques(busqueda, sql);
                for (Parque parque : parques){
                    System.out.print("       ");
                    System.out.println(parque.getParqueNombre());
                }
                System.out.println("");
            }
        }
        catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
    }//Cierre metodo    
   
    /**
     * Solicita información sobre el parque que se quiere registrar, para incluirlo en 
     * la base de datos llamando al método de parqueDAO.registrarParque
     */
    private void registrarParque(){
        //La usaremos para verificar que la ciudad facilitada existe.
        final String opcion = "1"; 
        
        System.out.println("¿En qué ciudad quieres registar el parque?(ID): ");
        String ciudadExiste = teclado.nextLine().toUpperCase();
        String idCiudad = existeCiudadCcaaParque(ciudadExiste, opcion);
        int id = 0;
        while (id <= 0){
            try{
                System.out.println("ID del parque(numero entero mayor que 0): ");
                id = parseInt(teclado.nextLine());                
            } catch(NumberFormatException ex){
                System.out.println("Debe introducir un numero."); 
            }
        }
        System.out.println("Nombre: ");
        String nombre = teclado.nextLine().toUpperCase();
        System.out.println("Extension: ");
        String extension = teclado.nextLine();
        //TODO Solicitar resto de campos
        Parque parque = new Parque();
        parque.setParqueId(Integer.toString(id));
        parque.setParqueCiudadId(idCiudad);
        parque.setParqueNombre(nombre);
        parque.setParqueExtension(extension);
        try{
        parqueDAO.registrarParque(parque);
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
    }//Cierre metodo
    
    /**
     * Solicitará datos para buscar el parque a modificar, lo mostrará por pantalla, solicitará
     * los nuevos datos a asignar a este parque y los usará para llamar al método de parqueDAO.modificarParque
     */
    private void modificarParque(){
        //La usaremos para verificar que el parque facilitado existe.
        final String opcion = "3";
        
        Parque parqueAux = new Parque(); //Guardaremos en este parque los datos nuevos
        try{
            System.out.println("Escriba el nombre del parque: ");
            String nombre = teclado.nextLine().toUpperCase();
            String parqueNombre = existeCiudadCcaaParque(nombre, opcion);
            Parque parqueReal = parqueDAO.obtenerParque(parqueNombre);
            System.out.print("Nombre:       ");
            System.out.println(parqueReal.getParqueNombre());
            System.out.print("Ciudad:       ");
            System.out.println(parqueReal.getParqueCiudadId());
            System.out.print("Extension:    ");
            System.out.println(parqueReal.getParqueExtension());
            System.out.println("--INTRODUCE NUEVOS DATOS--");
            System.out.println("Nuevo Nombre:       ");
            parqueAux.setParqueNombre(teclado.nextLine().toUpperCase());
            System.out.println("Nueva Ciudad:       ");
            parqueAux.setParqueCiudadId(teclado.nextLine().toUpperCase());
            System.out.println("Nueva Extension:    ");
            parqueAux.setParqueExtension(teclado.nextLine().toUpperCase());
            parqueDAO.modificarParque(parqueReal, parqueAux);
            
            parqueReal = parqueDAO.obtenerParque(parqueAux.getParqueNombre());
            System.out.println("--DATOS ACTUALIZADOS--");
            System.out.print("Nombre:       ");
            System.out.println(parqueReal.getParqueNombre());
            System.out.print("Ciudad:       ");
            System.out.println(parqueReal.getParqueCiudadId());
            System.out.print("Extension:    ");
            System.out.println(parqueReal.getParqueExtension());
            System.out.println("");
        }
        catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
    }
    
    /**
     * Solicita la ciudad y la extensión individual mínima para mostrar los parques
     * con estas características. Esto lo hará pasandole la información solicitada al 
     * metodo parqueDAO.numParquesExMayor
     */    
    private void numParquesExMayor(){
        //La usaremos para verificar que la ciudad facilitada existe.
        final String opcion = "1";
        int extension = 0;
        
        System.out.println("Indique la ciudad: ");
        String ciudadExiste = teclado.nextLine().toUpperCase();
        String ciudad = existeCiudadCcaaParque(ciudadExiste, opcion);
        while (extension<=0){
            try{
                System.out.println("Por favor, introduzca un valor entero mayor que 0 para indicar la extensión individual mínima: ");
                extension = parseInt(teclado.nextLine());   
            } catch(NumberFormatException ex){
                System.out.println("Debe introducir un numero.");  
            }
        }  
        try{
          System.out.println("Hay "+parqueDAO.numParquesExMayor(ciudad, extension)+" parques en " + ciudad + " cuya extensión individual es mayor que " + extension + " m2");  
          System.out.println(""); 
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        }
        
    }
    
    /**
     * Abre un menú para decidir el criterio de borrado. Solicita información según el criterio
     * y se la pasa al metodo eliminarParques
     */
    private void opcionEliminarParque(){
        String sql;
        String busqueda;
        System.out.println("Borrar por: ");
        System.out.println("    1.Ciudad (borrará todos los parques de dicha ciudad)");
        System.out.println("    2.CCAA (borrará todos los parques de dicha CCAA)");
        System.out.println("    3.Nombre del parque");

        String opcion = teclado.nextLine();
        switch (opcion){
            case "1":
                System.out.println("Indique abreviatura de ciudad: ");
                busqueda = teclado.nextLine().toUpperCase();
                existeCiudadCcaaParque(busqueda, opcion);
                sql = "DELETE FROM Parques WHERE ID_Ciudad = ?";
                eliminarParques(busqueda, sql);
                break;
            case "2":
                System.out.println("Indique la CCAA: ");
                busqueda = teclado.nextLine().toUpperCase();
                existeCiudadCcaaParque(busqueda, opcion);
                sql = "DELETE FROM Parques p WHERE p.ID_Ciudad IN (SELECT c.id_ciudad FROM ciudades c WHERE c.ccaa = ?)";
                eliminarParques(busqueda, sql);
                break;
            case "3":
                System.out.println("Indique el nombre del parque: ");
                busqueda = teclado.nextLine().toUpperCase();
                existeCiudadCcaaParque(busqueda, opcion);
                sql = "DELETE FROM Parques WHERE PARQUE_NOMBRE = ?";
                eliminarParques(busqueda, sql);
                break;
            
            default:
                System.out.println("Opcion incorrecta");
        }        
               
    }//Cierre metodo
    
    /**
     * Pasa los variables que recibe al metodo parqueDAO.eliminarParque para que borre
     * los parques en cuestion
     * @param busqueda String que se usará en la sentencia SQL para borrar los parques deseados
     * @param sql String que contiene la sentencia SQL de borrado elegida
     */
    private void eliminarParques(String busqueda, String sql){
        try{
            parqueDAO.eliminarParque(busqueda, sql);
            System.out.println("Proceso de borrado finalizado correctamente");    
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        } 
    }//Cierre metodo
    
    private void ciudadesExtTotal(){
        int extTotal=0;
        while(extTotal<=0)
            try{
                System.out.println("Por favor, introduzca un valor entero mayor que 0 para indicar la suma total de extensión mínima: ");
                extTotal = parseInt(teclado.nextLine());
            } catch(NumberFormatException ex){
                System.out.println("Debe introducir un numero.");
            }
        while (extTotal<=0){
            System.out.println("Por favor, introduzca un valor entero mayor que 0");
            extTotal = parseInt(teclado.nextLine());
        }
        System.out.print("CIUDAD      ");
        System.out.println("SUMA TOTAL EXTENSION");
        try{
            ArrayList<Parque> parques = parqueDAO.ciudadesExtTotal(extTotal);
            for (Parque parque : parques){
                    System.out.print(parque.getParqueCiudadId()+"         ");
                    System.out.println(parque.getParqueExtension());
            }
            System.out.println("");
        } catch (SQLException sqle){
            System.out.println("Se ha producido un error. Inténtelo de nuevo");
            sqle.printStackTrace();
        } 
    }
}
