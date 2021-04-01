package com.programacion.alanz.actividadaprendizaje2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author droja
 */
public class Conexion {
    
    private Connection conexion;
    private final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String URL_CONEXION = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USUARIO = "Parques";
    private final String CONTRASENA = "1234";
    
    public Conexion(){
        
    }
    
    public Connection getConexion(){
        return conexion;
    }
    
    public void conectar(){
        try{
            Class.forName(DRIVER);
            conexion = DriverManager.getConnection(URL_CONEXION, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    
    public void desconectar(){
        try{
            conexion.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
}
