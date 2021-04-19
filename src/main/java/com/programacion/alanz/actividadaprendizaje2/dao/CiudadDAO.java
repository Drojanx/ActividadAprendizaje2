package com.programacion.alanz.actividadaprendizaje2.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author droja
 */
public class CiudadDAO {
    
    private Conexion conexion;
    
    public CiudadDAO(Conexion conexion){
        this.conexion = conexion;
    }
    
    public boolean existeCiudadCcaa(String ciudadCcaa, String opcion) throws SQLException{ /*METER EN FICHERO CiudadDAO*/
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



