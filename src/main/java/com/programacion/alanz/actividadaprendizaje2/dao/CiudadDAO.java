package com.programacion.alanz.actividadaprendizaje2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CiudadDAO {
    
    private Conexion conexion;
    
    public CiudadDAO(Conexion conexion){
        this.conexion = conexion;
    }
    /**
     * Buscará una ciudad o una comunidad autónoma y determinará si existe
     * @param ciudadCcaa almacena la abreviatura de una ciudad o el nombre de una   
     *                   comunidad autónoma
     * @param opcion almacena "1" si se está buscando una ciudad o "2" si se
     *               está buscando una comunidad autónoma
     * @return devuelve un valor booleano para determinar si existe o no
     */
    public boolean existeCiudadCcaa(String ciudadCcaa, String opcion) throws SQLException{
        //Contamos cuantas veces aparece el parqueCiudadId introducido para ver si aparece en la tabla.
        String ctaSql;
        if(!opcion.equals("1")){
            ctaSql = "SELECT COUNT(*) FROM ciudades WHERE ccaa = ?";
        } else {
            ctaSql = "SELECT COUNT(*) FROM ciudades WHERE id_ciudad = ?";
        }
        int existe;
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(ctaSql);
        sentencia.setString(1, ciudadCcaa);
        sentencia.executeUpdate();
        ResultSet resultado = sentencia.executeQuery();
        resultado.next();
        //Almacenamos el conteo en "existe", si es mayor que cero es que aparece en la tabla,
        //si no es que no aparece y por tanto, sacamos mensaje indicandolo.
        existe = resultado.getInt(1);
        return existe > 0;
    }
}



