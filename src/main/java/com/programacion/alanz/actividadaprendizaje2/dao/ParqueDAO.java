package com.programacion.alanz.actividadaprendizaje2.dao;

import com.programacion.alanz.actividadaprendizaje2.domain.Parque;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParqueDAO {
    
    private Conexion conexion;
    
    public ParqueDAO(Conexion conexion){
        this.conexion = conexion;
    }
    
    /**
     * Buscará en la base de datos un parque cuyo nombre coincida con el texto
     * indicado en la variable "nombre" y determinará si existe o no
     * @param nombre almacena el nombre de un parque
     * @return devuelve un balor booleano indicando si el parque buscado existe
     *         o no
     * @throws SQLException 
     */
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
    
    /**
     * Recibe como parámetro una variable de tipo Parque, el cuál registrará
     * en la base de datos
     * @param parque almacena un parque
     * @throws SQLException 
     */
    public void registrarParque(Parque parque) throws SQLException {
            String sql = "INSERT INTO Parques (ID_Parque, ID_Ciudad, Parque_Nombre, Extension_m2) VALUES (?, ?, ?, ?)";
            PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
            
            /*sentencia = conexion.getConexion().prepareStatement(sql);*/
            sentencia.setString(1, parque.getParqueId());
            sentencia.setString(2, parque.getParqueCiudadId());
            sentencia.setString(3, parque.getParqueNombre());
            sentencia.setString(4, parque.getParqueExtension());
            sentencia.executeUpdate();
            System.out.println("El parque se ha creado correctamente.");
        
    }
    
    /**
     * Busca un parque con el nombre almacenado en el parque parqueReal, y modifica
     * sus datos sustituyendolos por los datos almacenados en el parque parqueAux
     * @param parqueReal almacena un parque ya existente en la base de datos
     * @param parqueAux almacena los nuevos datos que va a recibir parqueReal
     */
    public void modificarParque(Parque parqueReal, Parque parqueAux) throws SQLException{
        String sql = "UPDATE Parques SET parque_nombre = ?, id_ciudad = ?, extension_m2 = ? WHERE parque_nombre = ?";
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, parqueAux.getParqueNombre());
        sentencia.setString(2, parqueAux.getParqueCiudadId());
        sentencia.setString(3, parqueAux.getParqueExtension());
        sentencia.setString(4, parqueReal.getParqueNombre());
        sentencia.executeUpdate();
    }
    
    /**
     * Dependiendo de los valores de las variables busqueda y sql, borrará uno o varios parques.
     * Busqueda puede ser la abreviatura de una ciudad, una comunidad autónoma o el nombre
     * de un parque, mientras que sql será una sentencia sql distinta dependiendo de si se va 
     * a buscar los parques por ciudad, por comunidad o por nombre.
     * @param busqueda almacena una abreviatura de una ciudad, el nombre de una comunidad
     *                 autonoma o el nombre de un parque
     * @param sql almacena una sentencia sql a completar con la variable busqueda
     */
    public void eliminarParque(String busqueda, String sql) throws SQLException{
        
        PreparedStatement sentencia = conexion.getConexion().prepareStatement(sql);
        sentencia.setString(1, busqueda);
        sentencia.executeUpdate();
    }
    
    /**
     * Devuelve un ArrayList de parques que contiene TODOS los parques almacenados
     * en la base de datos
     * @return devuelve un ArrayList con todos los parques
     */
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
    
    /**
     * Devuelve un ArrayList de parques que contiene los parques que resulten de realizar 
     * la sentencia almacenada en sql completada por la variable cadenaBusqueda
     * @param cadenaBusqueda puede ser una abreviatura de una ciudad, una comunidad autónoma
     *                       o una cadena de texto que debe aparecer en el nombre de algún parque
     * @param sql sentencia sql que realizará la búsqueda dependiendo del valor de cadenaBusqueda
     * @return devuelve el ArraList de parques resultante de la búsqueda realizada
     */
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
    
    /**
     * Busca un parque según su nombre exacto y lo devuelve
     * @param parqueNombre almacena el nombre de un parque
     * @return devuelve el parque resultante de la búsqueda
     */
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
    
    /**
     * Devuelve el número de parques de una determinada ciudad que tengan una extensión 
     * individual mayor que la que se indique en la variable extension
     * @param ciudad almacena la abreviatura de una ciudad
     * @param extension almacena el valor entero de una extensión
     */
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
    
    /**
     * Devuelve un ArrayList de parques axuiliares en los que sólo se almacena 
     * la ciudad y la suma total de las extensiones de los parques de dicha ciudad, 
     * siempre y cuando esta suma de extensiones sea mayor que el valor de exTotal
     * @param exTotal almacena el valor mínimo de suma total de extensiones de 
     *                los parques de una ciudad
     */
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
