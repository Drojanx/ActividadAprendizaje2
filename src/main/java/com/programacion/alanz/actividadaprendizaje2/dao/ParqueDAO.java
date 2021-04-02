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
    
    public void verParque(Parque parque){
        
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
    
    public void modificarParque(Parque parque) throws SQLException{
        String sql = "UPDATE Parques SET ID_Ciudad = ? WHERE IC_Parque = ?";
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, parque.getParqueCiudadId());
        sentencia.setString(2, parque.getParqueId());
        sentencia.executeUpdate();
    }
    
    public void eliminarParque(String id) throws SQLException{
        String sql = "DELETE FROM Parques WHERE ID_Parque = ?";
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, id);
        sentencia.executeUpdate();
    }
    
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
    
    public ArrayList<Parque> obtenerParques(String cadenaBusqueda, String sql) throws SQLException{
        /*sql = "SELECT nombre, id_ciudad, id_parque FROM parques WHERE id_ciudad = ? ORDER BY nombre;";*/
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
    
}
