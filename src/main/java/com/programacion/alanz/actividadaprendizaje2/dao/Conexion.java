package com.programacion.alanz.actividadaprendizaje2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author droja
 */
public class Conexion {
    
    private Connection connection;
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL_CONEXION = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USUARIO = "Parques";
    private static final String CONTRASENA = "1234";
    
    public Connection getConexion(){
        return connection;
    }
    
    public void conectar(){
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL_CONEXION, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
    
    public void desconectar(){
        try{
            connection.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
}
