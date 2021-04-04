package com.programacion.alanz.actividadaprendizaje2.dao;

import com.programacion.alanz.actividadaprendizaje2.domain.Parque;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author droja
 */
public class ParqueDAO {
    
    private Conexion conexion;
    
    public ParqueDAO(Conexion conexion){
        this.conexion = conexion;
    }
    
    public boolean existeParque(String nombre) throws SQLException{
        //Contamos cuantas veces aparece el parqueCiudadId introducido para ver si aparece en la tabla.
        String ctaSql = "SELECT COUNT(*) FROM parques WHERE parque_nombre = ?";
        
        int existe;
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(ctaSql);
        sentencia.setString(1, nombre);
        sentencia.executeUpdate();
        ResultSet resultado = sentencia.executeQuery();
        resultado.next();
        //Almacenamos el conteo en "existe", si es mayor que cero es que aparece en la tabla,
        //si no es que no aparece y por tanto, sacamos mensaje indicandolo.
        existe = resultado.getInt(1);
        return existe > 0;
    }
    
    public void registrarParque(Parque parque) throws SQLException {
            String sql = "INSERT INTO Parques (ID_Parque, ID_Ciudad, Parque_Nombre, Extension_m2) VALUES (?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
            
            sentencia = conexion.getConexion().prepareStatement(sql);
            sentencia.setString(1, parque.getParqueId());
            sentencia.setString(2, parque.getParqueCiudadId());
            sentencia.setString(3, parque.getParqueNombre());
            sentencia.setString(4, parque.getParqueExtension());
            sentencia.executeUpdate();
            System.out.println("El parque se ha creado correctamente.");
        
    }
    
    //Usa los datos de parqueAux para modificar los de parqueReal
    public void modificarParque(Parque parqueReal, Parque parqueAux) throws SQLException{
        String sql = "UPDATE Parques SET parque_nombre = ?, id_ciudad = ?, extension_m2 = ? WHERE parque_nombre = ?";
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, parqueAux.getParqueNombre());
        sentencia.setString(2, parqueAux.getParqueCiudadId());
        sentencia.setString(3, parqueAux.getParqueExtension());
        sentencia.setString(4, parqueReal.getParqueNombre());
        sentencia.executeUpdate();
    }
    
    public void eliminarParque(String busqueda, String sql) throws SQLException{
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, busqueda);
        sentencia.executeUpdate();
    }
    
    //Obtiene todos los parques
    public ArrayList<Parque> obtenerParques() throws SQLException{
        String sql = "SELECT * FROM Parques";
        ArrayList<Parque> parques = new ArrayList<>();
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()){
            Parque parque = new Parque();
            parque.setParqueId(resultado.getString(1));
            parque.setParqueCiudadId(resultado.getString(2));
            parque.setParqueNombre(resultado.getString(3));
            parque.setParqueExtension(resultado.getString(4));
            sentencia.executeUpdate();
            
            parques.add(parque);
        }
        return parques;
    }
    
    //Obtiene parques según la cadena de busqueda que reciba
    public ArrayList<Parque> obtenerParques(String cadenaBusqueda, String sql) throws SQLException{
        ArrayList<Parque> parques = new ArrayList<>();
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, cadenaBusqueda);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()){
            Parque parque = new Parque();
            parque.setParqueNombre(resultado.getString(1));
            
            parques.add(parque);
        }
        return parques;
    }
    
    //Obtiene un único parque según su nombre exacto
    public Parque obtenerParque(String parqueNombre) throws SQLException{
        String sql = "SELECT * FROM parques WHERE parque_nombre = ?";
        Parque parque = new Parque();
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, parqueNombre);
        ResultSet resultado = sentencia.executeQuery();
        resultado.next();
        parque.setParqueId(resultado.getString(1));
        parque.setParqueCiudadId(resultado.getString(2));
        parque.setParqueNombre(resultado.getString(3));
        parque.setParqueExtension(resultado.getString(4));
        
        return parque;
    }
    
    //Devolver el número de parques de una determinada ciudad que tengan una extensión individual mayor que la que desee el usuario.
    public int numParquesExMayor(String ciudad, int extension) throws SQLException{
        String sql = "SELECT NVL(MAX(COUNT(*)),0) FROM parques WHERE id_ciudad = ? AND extension_m2 > ? GROUP BY ID_CIUDAD";
        int cuenta;
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, ciudad);
        sentencia.setInt(2, extension);
        sentencia.executeUpdate();
        ResultSet resultado = sentencia.executeQuery();
        resultado.next();
        //Almacenamos el conteo en "existe", si es mayor que cero es que aparece en la tabla,
        //si no es que no aparece y por tanto, sacamos mensaje indicandolo.
        cuenta = resultado.getInt(1);
        return cuenta;
    }
    
    public ArrayList<Parque> ciudadesExtTotal(int exTotal) throws SQLException{
        String sql = "SELECT ID_CIUDAD, SUM(EXTENSION_M2) FROM PARQUES GROUP BY ID_CIUDAD HAVING SUM(EXTENSION_M2)> ?" ;
        ArrayList<Parque> parques = new ArrayList<>();
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setInt(1, exTotal);
        ResultSet resultado = sentencia.executeQuery();
        while (resultado.next()){
            Parque parque = new Parque();
            parque.setParqueCiudadId(resultado.getString(1));
            parque.setParqueExtension(resultado.getString(2));
            parques.add(parque);
        }
        return parques;
    }
    
}
