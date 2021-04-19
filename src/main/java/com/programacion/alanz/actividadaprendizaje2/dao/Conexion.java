package com.programacion.alanz.actividadaprendizaje2.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    private Connection connection;
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL_CONEXION = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USUARIO = "Parques";
    private static final String CONTRASENA = "1234";
    
    public Connection getConexion(){
        return connection;
    }
    
    /**
     * Método que conecta el programa con la base de datos de SQL Developer
     */
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
    
    /**
     * Método que cierra la conexión con la base de datos
     */
    public void desconectar(){
        try{
            connection.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
}
