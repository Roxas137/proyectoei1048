package es.uji.ei1048.gestion_DB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestionDB extends UnicastRemoteObject {
    protected GestionDB() throws RemoteException {}

    /**
     * Se realiza una conexion con la base de datos.
     * @return Devuelve la conexion establecida.
     */
    private Connection connect(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:proyectoei1048\\ei1048.db");

            return connection;
        }catch (SQLException e){
            System.out.println("There was an error connecting to Database");

            return null;
        }catch (ClassNotFoundException e){
            System.out.println("Class [org.sqlite.JDBC] not found.");

            return null;
        }
    }

    /**
     * Registra una ciudad (mediante su identificador) como lugar favorito en la bbdd
     * @param idCiudad Identificador de la ciudad que se quiere registrar.
     * @param etiqueta Etiqueta puesta al lugar favorito. Puede estar vacia.
     * @return Devuelve true en el caso de que se registre de forma correcta y false en caso contrario.
     * @throws RemoteException Si se produce algun error no previsto.
     */
    public boolean registrarLugarFavorito(Long idCiudad, String etiqueta) throws RemoteException {
        try {
            Connection connection = connect();

            String sentence = "INSERT INTO LugaresFavoritos VALUES(?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setString(1, etiqueta);
            st.setLong(4, idCiudad);

            st.executeUpdate();

            System.out.println("Lugar favorito registrado correctamente.");
            connection.close();
            return true;
        }catch (SQLException e){
            System.out.println("Ha ocurrido un error al registrar una ciudad como lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha habido algun error en la conexion con la base de datos. NullPointerException.");
            return false;
        }
    }

    /**
     * Registra unas coordenadas como lugar favorito en la bbdd
     * @param longitud Longitud de las coordenadas.
     * @param latitud Latitud de las coordenadas.
     * @param etiqueta Etiqueta puesta al lugar favorito. Puede estar vacia.
     * @return Devuelve true en el caso de que se registre de forma correcta y false en caso contrario.
     * @throws RemoteException Si se produce algun error no previsto.
     */
    public boolean registrarLugarFavorito(double longitud, double latitud, String etiqueta) throws  RemoteException {

        try {
            Connection connection = connect();

            String sentence = "INSERT INTO LugaresFavoritos VALUES(?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setString(1, etiqueta);
            st.setDouble(2, latitud);
            st.setDouble(3, longitud);

            st.executeUpdate();

            System.out.println("Lugar favorito registrado correctamente.");
            connection.close();
            return true;
        }catch (SQLException e){
            System.out.println("Ha ocurrido un error al registrar unas coordenadas como lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha habido algun error en la conexion con la base de datos. NullPointerException.");
            return false;
        }
    }
}
