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
    
    public void eliminarParque(String id) throws SQLException{
        String sql = "DELETE FROM Parques WHERE ID_Parque = ?";
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, id);
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
    
    //Obtiene un único parque
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
    
}
