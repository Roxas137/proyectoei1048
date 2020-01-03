package es.uji.ei1048.gestionDB;

import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.object.LugarFavorito;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
            st.setDouble(2, -500);
            st.setDouble(3, -500);
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
            st.setLong(4, -500);

            st.executeUpdate();

            System.out.println("Lugar favorito registrado correctamente.");
            connection.close();
            return true;
        }catch (SQLException e){
            System.out.println("Ha ocurrido un error al registrar unas coordenadas como lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha habido algun error en la conexion con la base de datos.");
            return false;
        }
    }

    /**
     * Obtiene un listado de los lugares favoritos registrados en la base de datos.
     * @return El listado de lugares favoritos.
     * @throws RemoteException En el caso de que algo vaya mal.
     */
    public List<LugarFavorito> getLugaresFavoritos() throws RemoteException {
        try{
            List<LugarFavorito> result = new ArrayList<>();
            Connection connection = connect();
            String sentence = "SELECT etiqueta, latitud, longitud, idCiudad FROM LugarFavorito";
            PreparedStatement st = connection.prepareStatement(sentence);

            ResultSet rs = st.executeQuery();

            LugarFavorito lugarFavorito = new LugarFavorito();
            while (rs.next()){
                long idCiudad = rs.getLong("idCiudad");
                double longitud = rs.getDouble("longitud");
                double latitud = rs.getDouble("latitud");
                String etiqueta = rs.getString("etiqueta");

                lugarFavorito.setIdCiudad(idCiudad);
                lugarFavorito.setEtiqueta(etiqueta);
                lugarFavorito.setLatitud(latitud);
                lugarFavorito.setLongitud(longitud);

                result.add(lugarFavorito);
            }

            System.out.println("Lugares favoritos obtenidos correctamente.");
            connection.close();
            return result;
        }catch (SQLException e){
            System.out.println("Ha habido un error al obtener los lugares favoritos.");
            return new ArrayList<>();
        }catch (NullPointerException e){
            System.out.println("Ha habido algun error en la conexion con la base de datos.");
            return new ArrayList<>();
        }
    }

    /**
     * Permite registrar unas condiciones meteorologicas en la base de datos que seran consultadas con posteridad.
     * @param condiciones Condiciones meteorologicas que se quieren registrar.
     * @param coordenadas Coordenadas de las condiciones meteorologicas a registrar.
     * @return Devuelve true si se registra correctamente y false en caso contrario.
     * @throws RemoteException Se lanza esta excepcion en el caso de que exista algun error.
     */
    public boolean registrarCondicionesMeteorologicas(CondicionesMeteorologicas condiciones, Coordenadas coordenadas) throws RemoteException{
        try{
            Connection connection = connect();
            String sentence = "INSERT INTO CondicionesMeteorologicas VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, coordenadas.getLongitud());
            st.setDouble(2, coordenadas.getLatitud());
            st.setLong(3, -500);
            st.setDouble(4, condiciones.getTemperaturaActual());
            st.setDouble(5,condiciones.getSensacionTermica());
            st.setDouble(6, condiciones.getTemperaturaMin());
            st.setDouble(7, condiciones.getTemperaturaMax());
            st.setString(8, condiciones.getEstadoClima());
            st.setDouble(9, condiciones.getVelViento());
            st.setDouble(10, condiciones.getDirViento());
            st.setDouble(11, condiciones.getPresion());
            st.setDouble(12, condiciones.getHumedad());
            st.setDate(13, new Date(condiciones.getFechaCondiciones().getTime().getTime()));
            st.setDate(14, new Date(condiciones.getFechaPeticion().getTime().getTime()));

            st.executeUpdate();

            System.out.println("Condiciones meteorologicas registradas correctamente.");
            connection.close();
            return true;
        }catch (SQLException e){
            System.out.println("Ha ocurrido un error al registrar unas condiciones meteorologicas en la base de datos.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha habido algun error en la conexion con la base de datos.");
            return false;
        }
    }

    /**
     * Permite registrar unas condiciones meteorologicas en la base de datos que seran consultadas con posteridad.
     * @param condiciones Condiciones meteorologicas que se quieren registrar.
     * @param idCiudad Identificador de la ciudad de las condiciones meteorologicas a registrar.
     * @return Devuelve true si se registra correctamente y false en caso contrario.
     */
    public boolean registrarCondicionesMeteorologicas(CondicionesMeteorologicas condiciones, Long idCiudad){
        try{
            Connection connection = connect();
            String sentence = "INSERT INTO CondicionesMeteorologicas VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, -500);
            st.setDouble(2, -500);
            st.setLong(3, idCiudad);
            st.setDouble(4, condiciones.getTemperaturaActual());
            st.setDouble(5,condiciones.getSensacionTermica());
            st.setDouble(6, condiciones.getTemperaturaMin());
            st.setDouble(7, condiciones.getTemperaturaMax());
            st.setString(8, condiciones.getEstadoClima());
            st.setDouble(9, condiciones.getVelViento());
            st.setDouble(10, condiciones.getDirViento());
            st.setDouble(11, condiciones.getPresion());
            st.setDouble(12, condiciones.getHumedad());
            st.setDate(13, new Date(condiciones.getFechaCondiciones().getTime().getTime()));
            st.setDate(14, new Date(condiciones.getFechaPeticion().getTime().getTime()));

            st.executeUpdate();

            System.out.println("Condiciones meteorologicas registradas correctamente.");
            connection.close();
            return true;
        }catch (SQLException e){
            System.out.println("Ha ocurrido un error al registrar unas condiciones meteorologicas en la base de datos.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha habido algun error en la conexion con la base de datos.");
            return false;
        }
    }

    /**
     * Obtener las condicciones meteorologicas a partir de unos datos y coordenadas concretas.
     *
     * @param fechaCondicion Fecha de las condiciones deseadas.
     * @param coordenadas Localizacion de las condiciones.
     * @param tipoPeticion Tipo de peticion [1 - current; 0 - forecast]
     * @return Las condiciones meteorologicas deseadas.
     */
    public CondicionesMeteorologicas getCondicionesMeteorologicas(Calendar fechaCondicion, Coordenadas coordenadas, int tipoPeticion){
        try{
            CondicionesMeteorologicas condicionesMeteorologicas = new CondicionesMeteorologicas();
            Connection connection = connect();
            String sentence = "SELECT * FROM CondicionesMeteorlogicas WHERE longitud = ? and latitud = ? and fechaCondiciones = ? and tipoPeticion = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, coordenadas.getLongitud());
            st.setDouble(2, coordenadas.getLatitud());
            st.setDate(3, new Date(fechaCondicion.getTime().getTime()));
            st.setInt(4, tipoPeticion);

            ResultSet rs = st.executeQuery();
            Calendar calendar = Calendar.getInstance();

            condicionesMeteorologicas.setVelViento(rs.getDouble("velViento"));
            condicionesMeteorologicas.setTemperaturaMin(rs.getDouble("temperaturaMin"));
            condicionesMeteorologicas.setTemperaturaMax(rs.getDouble("temperaturaMax"));
            condicionesMeteorologicas.setTemperaturaActual(rs.getDouble("temperaturaActual"));
            condicionesMeteorologicas.setSensacionTermica(rs.getDouble("sensacionTermica"));
            condicionesMeteorologicas.setPresion(rs.getDouble("presion"));
            condicionesMeteorologicas.setHumedad(rs.getDouble("humedad"));
            condicionesMeteorologicas.setEstadoClima(rs.getString("estadoClima"));
            condicionesMeteorologicas.setDirViento(rs.getDouble("dirViento"));
            calendar.setTime(rs.getDate("fechaCondiciones"));
            condicionesMeteorologicas.setFechaCondiciones(calendar);
            calendar.setTime(rs.getDate("fechaPeticion"));
            condicionesMeteorologicas.setFechaPeticion(calendar);


            System.out.println("Condiciones meteorologicas obtenidas correctamente.");
            connection.close();
            return condicionesMeteorologicas;
        }catch (SQLException e){
            System.out.println("Ha habido algun error al obtener las condiciones meteorologicas de la base de datos.");
            return new CondicionesMeteorologicas();
        }catch (NullPointerException e){
            System.out.println("He habido algun error al conectarse a la base de datos.");
            return new CondicionesMeteorologicas();
        }
    }

    /**
     * Obtener las condiciones meteorologicas a partir de unos datos y una ciudad concreta.
     *
     * @param fechaCondicion Fecha de las condiciones deseadas.
     * @param idCiudad Identificador de la ciudad de las ondiciones.
     * @param tipoPeticion Tipo de peticion [1 - current; 0 - forecast]
     * @return Las condiciones meteorologicas deseadas.
     */
    public CondicionesMeteorologicas getCondicionesMeteorologicas(Calendar fechaCondicion, int idCiudad, int tipoPeticion){
        try{
            CondicionesMeteorologicas condicionesMeteorologicas = new CondicionesMeteorologicas();
            Connection connection = connect();
            String sentence = "SELECT * FROM CondicionesMeteorlogicas WHERE idCiudad = ? and fechaCondiciones = ? and tipoPeticion = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, idCiudad);
            st.setDate(2, new Date(fechaCondicion.getTime().getTime()));
            st.setInt(3, tipoPeticion);

            ResultSet rs = st.executeQuery();
            Calendar calendar = Calendar.getInstance();

            condicionesMeteorologicas.setVelViento(rs.getDouble("velViento"));
            condicionesMeteorologicas.setTemperaturaMin(rs.getDouble("temperaturaMin"));
            condicionesMeteorologicas.setTemperaturaMax(rs.getDouble("temperaturaMax"));
            condicionesMeteorologicas.setTemperaturaActual(rs.getDouble("temperaturaActual"));
            condicionesMeteorologicas.setSensacionTermica(rs.getDouble("sensacionTermica"));
            condicionesMeteorologicas.setPresion(rs.getDouble("presion"));
            condicionesMeteorologicas.setHumedad(rs.getDouble("humedad"));
            condicionesMeteorologicas.setEstadoClima(rs.getString("estadoClima"));
            condicionesMeteorologicas.setDirViento(rs.getDouble("dirViento"));
            calendar.setTime(rs.getDate("fechaCondiciones"));
            condicionesMeteorologicas.setFechaCondiciones(calendar);
            calendar.setTime(rs.getDate("fechaPeticion"));
            condicionesMeteorologicas.setFechaPeticion(calendar);


            System.out.println("Condiciones meteorologicas obtenidas correctamente.");
            connection.close();
            return condicionesMeteorologicas;
        }catch (SQLException e){
            System.out.println("Ha habido algun error al obtener las condiciones meteorologicas de la base de datos.");
            return new CondicionesMeteorologicas();
        }catch (NullPointerException e){
            System.out.println("He habido algun error al conectarse a la base de datos.");
            return new CondicionesMeteorologicas();
        }
    }

    /**
     * Comprueba si existen unas condiciones determinadas en la base de datos a partir de unas coordenadas.
     *
     * @param fechaCondicion Fecha de las condiciones deseadas.
     * @param coordenadas Localización de las condiciones meteorlogicas.
     * @param tipoPeticion Tipo de peticion [1 - current; 0 - forecast]
     * @return True si se encuentra alguna condicion a partir de esos datos, false en caso contrario.
     */
    public boolean checkCondiciones(Calendar fechaCondicion, Coordenadas coordenadas, int tipoPeticion){
        try{
            Connection connection = connect();
            String sentence = "SELECT COUNT(*) as condicion FROM CondicionesMeteorlogicas WHERE longitud = ? and latitud = ? and fechaCondiciones = ? and tipoPeticion = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, coordenadas.getLongitud());
            st.setDouble(2, coordenadas.getLatitud());
            st.setDate(3, new Date(fechaCondicion.getTime().getTime()));
            st.setInt(4, tipoPeticion);

            ResultSet rs = st.executeQuery();

            connection.close();
            return rs.getInt("condicion") > 0;
        }catch (SQLException e){
            System.out.println("Ha habdio algun error al obtener los datos de la base de datos");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error al conectarse a la base de datos.");
            return false;
        }
    }

    /**
     *
     * @param fechaCondicion Fecha de las condiciones deseadas.
     * @param idCiudad Identificador de la ciudad de las ondiciones.
     * @param tipoPeticion Tipo de peticion [1 - current; 0 - forecast]
     * @return True si se encuentra alguna condicion a partir de esos datos, false en caso contrario.
     */
    public boolean checkCondiciones(Calendar fechaCondicion, int idCiudad, int tipoPeticion){
        try{
            Connection connection = connect();
            String sentence = "SELECT COUNT(*) as condicion FROM CondicionesMeteorlogicas WHERE idCiudad = ? and fechaCondiciones = ? and tipoPeticion = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, idCiudad);
            st.setDate(2, new Date(fechaCondicion.getTime().getTime()));
            st.setInt(3, tipoPeticion);

            ResultSet rs = st.executeQuery();

            connection.close();
            return rs.getInt("condicion") > 0;
        }catch (SQLException e){
            System.out.println("Ha habdio algun error al obtener los datos de la base de datos");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error al conectarse a la base de datos.");
            return false;
        }
    }
}