package es.uji.ei1048.gestionDB;

import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.object.LugarFavorito;
import jdk.nashorn.internal.objects.NativeUint8Array;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GestionDB extends UnicastRemoteObject {
    public GestionDB() throws RemoteException {}

    /**
     * Se realiza una conexion con la base de datos.
     * @return Devuelve la conexion establecida.
     */
    private Connection connect(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:ei1048.db");

            return connection;
        }catch (SQLException e){
            System.out.println("There was an error connecting to Database");
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            System.out.println("Class [org.sqlite.JDBC] not found.");

            return null;
        }
    }


    /**
     * Permite registrar unas coordenadas en la base de datos bajo una etiqueta.
     * @param longitud Longitud de las coordenadas a registrar.
     * @param latitud Latitud de las coordenadas a registrar.
     * @param etiqueta Etiqueta asociada a las coordenadas.
     * @return Devuelve true en el caso de que se registren de forma correcta y false en caso contrario.
     */
    public boolean registrarCoordenadas(double longitud, double latitud, String etiqueta){
        try{
            Connection connection = connect();
            String sentence = "INSERT INTO Coordenadas(longitud, latitud, etiqueta) VALUES(?, ?, ?)";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, longitud);
            st.setDouble(2, latitud);
            st.setString(3, etiqueta);

            st.executeUpdate();
            connection.close();
            System.out.println("Coordenadas registradas correctamente en la base de datos.");
            return true;
        }catch (SQLException e){
            System.out.println("Las coordenadas otorgadas ya existian en la base de datos.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha habido un error al conectarse con la base de datos.");
            return false;
        }
    }

    /**
     * Permite eliminar unas coordenadas de la base de datos.
     * @param longitud Longitud de las coordenadas a borrar.
     * @param latitud Latitud de las coordenadas a borrar.
     * @return Devuelve true en el caso de que se borren de forma correcta y false en caso contrario.
     */
    public boolean eliminarCoordenadas(double longitud, double latitud){
        try {
            Connection connection = connect();
            String sentence = "DELETE FROM Coordenadas WHERE longitud = ? AND latitud = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, longitud);
            st.setDouble(2, latitud);

            st.executeUpdate();
            connection.close();
            System.out.println("Las coordenadas han sido borradas con exito.");
            return true;
        } catch (SQLException e) {
            System.out.println("Las coordenadas indicadas no coinciden con ningunas coordenadas registradas.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error al conectarse con la base de datos.");
            return false;
        }
    }

    /**
     * Permite modificar unas coordenadas guardadas en la base de datos.
     * @param antiguaLongitud Longitud de las coordenadas a modificar.
     * @param antiguaLatitud Latitud de las coordenadas a modificar.
     * @param nuevaLongitud Nueva longitud de las coordenadas.
     * @param nuevaLatitud Nueva latitud de las coordenadas.
     * @param nuevaEtiqueta Nueva etiqueta de las coordenadas.
     * @return Devuelve true en el caso de que se modifiquen de forma correcta y false en caso contrario.
     */
    public boolean modificarCoordenadas(double antiguaLongitud, double antiguaLatitud, double nuevaLongitud, double nuevaLatitud, String nuevaEtiqueta){
        try{
            Connection connection = connect();
            String sentence = "UPDATE Coordenadas SET longitud = ?, latitud = ?, etiqueta = ? WHERE longitud = ? AND latitud = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, nuevaLongitud);
            st.setDouble(2, nuevaLatitud);
            st.setString(3, nuevaEtiqueta);
            st.setDouble(4, antiguaLongitud);
            st.setDouble(5, antiguaLatitud);

            st.executeUpdate();
            connection.close();
            System.out.println("Lugar favorito modificado correctamente.");
            return true;
        }catch (SQLException e){
            System.out.println("Las coordenadas otorgadas no corresponden con ningun lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error en la conexion con la base de datos.");
            return false;
        }
    }

    /**
     * Obtiene un listado con todas las coordenadas que esten registradas en la base de datos.
     * @return Listado de las coordenadas registradas bajo una etiqueta.
     */
    public List<Coordenadas> getCoordenadasRegistradas(){
        try{
            Connection connection = connect();
            String sentence = "SELECT * FROM Coordenadas";
            PreparedStatement st = connection.prepareStatement(sentence);

            ResultSet rs = st.executeQuery();
            Coordenadas coordenadas = new Coordenadas();
            List<Coordenadas> result = new ArrayList<>();

            while(rs.next()){
                coordenadas.setLongitud(rs.getDouble("longitud"));
                coordenadas.setLatitud(rs.getDouble("latitud"));
                coordenadas.setEtiqueta("etiqueta");
                result.add(coordenadas);
            }

            rs.close();
            connection.close();
            System.out.println("Coordenadas obtenidas correctamente de la base de datos.");
            return result;
        }catch (SQLException e){
            System.out.println("No existe ninguna coordenada.");
            return new ArrayList<>();
        }catch (NullPointerException e){
            System.out.println("Ha habido un error al conectarse con la base de datos.");
            return new ArrayList<>();
        }
    }

    /**
     * Registra una ciudad (mediante su identificador) como lugar favorito en la bbdd
     * @param idCiudad Identificador de la ciudad que se quiere registrar.
     * @param etiqueta Etiqueta puesta al lugar favorito. Puede estar vacia.
     * @return Devuelve true en el caso de que se registre de forma correcta y false en caso contrario.
     */
    public boolean registrarLugarFavorito(Long idCiudad, String etiqueta){
        try {
            Connection connection = connect();

            String sentence = "INSERT INTO LugaresFavoritos(etiqueta, latitud, longitud, idCiudad) VALUES(?, ?, ?, ?)";
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
     */
    public boolean registrarLugarFavorito(double longitud, double latitud, String etiqueta){

        try {
            Connection connection = connect();

            String sentence = "INSERT INTO LugaresFavoritos(etiqueta, latitud, longitud, idCiudad) VALUES(?, ?, ?, ?)";
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
            e.printStackTrace();
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha habido algun error en la conexion con la base de datos.");
            return false;
        }
    }

    /**
     * Permite eliminar un lugar favorito de la base de datos.
     * @param longitud Longitud del lugar favorito a borrar.
     * @param latitud Latitud del lugar favorito a borrar.
     * @return True en caso de que se haya completado correctamente el borrado, false en caso contrario.
     */
    public boolean eliminaLugarFavorito(double longitud, double latitud){
        try {
            Connection connection = connect();
            String sentence = "DELETE FROM LugaresFavoritos WHERE longitud = ? AND latitud = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, longitud);
            st.setDouble(2, latitud);

            st.executeUpdate();
            connection.close();
            System.out.println("El lugar favorito ha sido borrado con exito.");
            return true;
        } catch (SQLException e) {
            System.out.println("Las coordenadas indicadas no coinciden con ningun lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error al conectarse con la base de datos.");
            return false;
        }
    }

    /**
     * Permite eliminar un lugar favorito de la base de datos.
     * @param idCiudad Identificador de la ciudad a borrar.
     * @return True en caso de que se haya completado correctamente el borrado, false en caso contrario.
     */
    public boolean eliminaLugarFavorito(long idCiudad){
        try{
            Connection connection = connect();
            String sentence = "DELETE FROM LugaresFavoritos WHERE idCiudad = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setLong(1, idCiudad);

            st.executeUpdate();
            connection.close();
            System.out.println("El lugar favorito ha sido borrado con exito.");
            return true;
        }catch(SQLException e){
            System.out.println("EL identificador de la ciudad no corresponde con ningun lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error al conectarse con la base de datos.");
            return false;
        }
    }

    /**
     * Permite modificar el lugar favorito.
     * @param antiguaLongitud Longitud del lugar favorito a modificar.
     * @param antiguaLatitud Latitud del lugar favorito a modificar.
     * @param nuevaLongitud Longitud del nuevo lugar favorito.
     * @param nuevaLatitud Latitud del nuevo lugar favorito.
     * @param nuevaEtiqueta Nueva etiqueta del lugar favorito.
     * @return True en caso de que se haya completado correctamente la modificacion, false en caso contrario.
     */
    public boolean modificarLugarFavorito(double antiguaLongitud, double antiguaLatitud, double nuevaLongitud, double nuevaLatitud, String nuevaEtiqueta){
        try{
            Connection connection = connect();
            String sentence = "UPDATE LugarFavorito SET longitud = ?, latitud = ?, etiqueta = ? WHERE longitud = ? AND latitud = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, nuevaLongitud);
            st.setDouble(2, nuevaLatitud);
            st.setString(3, nuevaEtiqueta);
            st.setDouble(4, antiguaLongitud);
            st.setDouble(5, antiguaLatitud);

            st.executeUpdate();
            connection.close();
            System.out.println("Lugar favorito modificado correctamente.");
            return true;
        }catch (SQLException e){
            System.out.println("Las coordenadas otorgadas no corresponden con ningun lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error en la conexion con la base de datos.");
            return false;
        }
    }

    /**
     * Permite modificar el lugar favorito.
     * @param antiguoIdCiudad Identificador de la ciudad a modificar.
     * @param nuevoIdCiudad Identificador de la nueva ciudad.
     * @param nuevaEtiqueta Nueva etiqueta del lugar favorito.
     * @return True en caso de que se haya completado correctamente la modificacion, false en caso contrario.
     */
    public boolean modificarLugarFavorito(long antiguoIdCiudad, long nuevoIdCiudad, String nuevaEtiqueta){
        try{
            Connection connection = connect();
            String sentence = "UPDATE LugarFavorito SET idCiudad = ?, etiqueta = ? WHERE idCiudad = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setLong(1, nuevoIdCiudad);
            st.setString(2, nuevaEtiqueta);
            st.setDouble(3, antiguoIdCiudad);


            st.executeUpdate();
            connection.close();
            System.out.println("Lugar favorito modificado correctamente.");
            return true;
        }catch (SQLException e){
            System.out.println("Las coordenadas otorgadas no corresponden con ningun lugar favorito.");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error en la conexion con la base de datos.");
            return false;
        }
    }

    /**
     * Obtiene un listado de los lugares favoritos registrados en la base de datos.
     * @return El listado de lugares favoritos.
     */
    public List<LugarFavorito> getLugaresFavoritos() {
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
            rs.close();

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
     */
    public boolean registrarCondicionesMeteorologicas(CondicionesMeteorologicas condiciones, Coordenadas coordenadas, int tipoPeticion){
        try{
            Connection connection = connect();
            String sentence = "INSERT INTO CondicionesMeteorologicas(" +
                    "longitud, latitud, idCiudad, temperaturaActual, temperaturaMin, temperaturaMax, estadoClima, velViento" +
                    ", dirViento, presion, humedad, fechaCondiciones, fechaPeticion, tipoPeticion)" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            st.setLong(13, condiciones.getFechaCondiciones().getTimeInMillis());
            st.setLong(14, condiciones.getFechaPeticion().getTimeInMillis());
            st.setInt(15, tipoPeticion);

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
    public boolean registrarCondicionesMeteorologicas(CondicionesMeteorologicas condiciones, Long idCiudad, int tipoPeticion){
        try{
            Connection connection = connect();
            String sentence = "INSERT INTO CondicionesMeteorologicas(" +
                    "longitud, latitud, idCiudad, temperaturaActual, temperaturaMin, temperaturaMax, estadoClima, velViento" +
                    ", dirViento, presion, humedad, fechaCondiciones, fechaPeticion, tipoPeticion)" +
                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            st.setLong(13, condiciones.getFechaCondiciones().getTimeInMillis());
            st.setLong(14, condiciones.getFechaPeticion().getTimeInMillis());
            st.setInt(15, tipoPeticion);

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
            Connection connection = connect();
            String sentence = "SELECT * FROM CondicionesMeteorlogicas WHERE longitud = ? and latitud = ? and fechaCondiciones = ? and tipoPeticion = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, coordenadas.getLongitud());
            st.setDouble(2, coordenadas.getLatitud());
            st.setLong(3, fechaCondicion.getTimeInMillis());
            st.setInt(4, tipoPeticion);

            ResultSet rs = st.executeQuery();
            CondicionesMeteorologicas condicionesMeteorologicas = toCondicionMeteorologica(rs);

            System.out.println("Condiciones meteorologicas obtenidas correctamente.");
            connection.close();
            rs.close();

            return condicionesMeteorologicas;
        }catch (SQLException e){
            System.out.println("Ha habido algun error al obtener las condiciones meteorologicas de la base de datos.");
            return null;
        }catch (NullPointerException e){
            System.out.println("He habido algun error al conectarse a la base de datos.");
            return null;
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
    public CondicionesMeteorologicas getCondicionesMeteorologicas(Calendar fechaCondicion, long idCiudad, int tipoPeticion){
        try{
            Connection connection = connect();
            String sentence = "SELECT * FROM CondicionesMeteorlogicas WHERE idCiudad = ? and fechaCondiciones = ? and tipoPeticion = ?";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setLong(1, idCiudad);
            st.setLong(3, fechaCondicion.getTimeInMillis());
            st.setInt(3, tipoPeticion);

            ResultSet rs = st.executeQuery();
            CondicionesMeteorologicas condicionesMeteorologicas = toCondicionMeteorologica(rs);

            System.out.println("Condiciones meteorologicas obtenidas correctamente.");
            connection.close();
            rs.close();

            return condicionesMeteorologicas;
        }catch (SQLException e){
            System.out.println("Ha habido algun error al obtener las condiciones meteorologicas de la base de datos.");
            return null;
        }catch (NullPointerException e){
            System.out.println("He habido algun error al conectarse a la base de datos.");
            return null;
        }
    }

    /**
     * Permite obtener una prediccion de la base de datos.
     *
     * @param coordenadas Coordenadas de la localizacion deseada.
     * @return Listado con las condiciones meteorologicas correspondientes.
     */
    public List<CondicionesMeteorologicas> getPrediccion(Coordenadas coordenadas){
        try{
            List<CondicionesMeteorologicas> prediccion = new ArrayList<>();
            Connection connection = connect();
            String sentence = "SELECT * FROM CondicionesMeteorologicas WHERE latitud = ? AND longitud = ? AND tipoPeticion = ? ORDER BY fechaPeticion DESC LIMIT 3";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, coordenadas.getLatitud());
            st.setDouble(2, coordenadas.getLongitud());
            st.setInt(3, 0);

            ResultSet rs = st.executeQuery();
            CondicionesMeteorologicas condicionesMeteorologicas;

            while (rs.next()) {
                condicionesMeteorologicas = toCondicionMeteorologica(rs);
                prediccion.add(condicionesMeteorologicas);
            }

            connection.close();
            rs.close();
            return prediccion;
        }catch (SQLException e){
            System.out.println("Ha habido un error al obtener la prediccion de la base de datos.");
            return new ArrayList<>();
        }catch (NullPointerException e){
            System.out.println("Ha habido un error al conectarse a la base de datos.");
            return new ArrayList<>();
        }
    }

    /**
     * Permite obtener una prediccion meteorologica de una ciudad de la base de datos.
     *
     * @param idCiudad Ciudad deseada.
     * @return Listado con la prediccion.
     */
    public List<CondicionesMeteorologicas> getPrediccion(long idCiudad){
        try{
            List<CondicionesMeteorologicas> prediccion = new ArrayList<>();
            Connection connection = connect();
            String sentence = "SELECT * FROM CondicionesMeteorologicas WHERE idCiudad = ? AND tipoPeticion = ? ORDER BY fechaPeticion DESC LIMIT 3";
            PreparedStatement st = connection.prepareStatement(sentence);

            st.setDouble(1, idCiudad);
            st.setInt(2, 0);

            ResultSet rs = st.executeQuery();
            CondicionesMeteorologicas condicionesMeteorologicas;

            while (rs.next()) {
                condicionesMeteorologicas = toCondicionMeteorologica(rs);
                prediccion.add(condicionesMeteorologicas);
            }

            connection.close();
            rs.close();
            return prediccion;
        }catch (SQLException e){
            System.out.println("Ha habido un error al obtener la prediccion de la base de datos.");
            return new ArrayList<>();
        }catch (NullPointerException e){
            System.out.println("Ha habido un error al conectarse a la base de datos.");
            return new ArrayList<>();
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
            st.setLong(3, fechaCondicion.getTimeInMillis());
            st.setInt(4, tipoPeticion);

            ResultSet rs = st.executeQuery();
            int exists = rs.getInt("condicion");

            rs.close();
            connection.close();

            return exists > 0;
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
            st.setLong(3, fechaCondicion.getTimeInMillis());
            st.setInt(3, tipoPeticion);

            ResultSet rs = st.executeQuery();
            int exists = rs.getInt("condicion");

            rs.close();
            connection.close();

            return exists > 0;
        }catch (SQLException e){
            System.out.println("Ha habdio algun error al obtener los datos de la base de datos");
            return false;
        }catch (NullPointerException e){
            System.out.println("Ha ocurrido un error al conectarse a la base de datos.");
            return false;
        }
    }

    /**
     * Permite obtener transformar la informacion de la base de datos en condiciones meteorologicas.
     * @param rs ResultSet necesario para obtener los datos de la base de datos.
     * @return La condicion meteorologica.
     * @throws SQLException En el caso de que haya algun error con la base de datos.
     */
    private CondicionesMeteorologicas toCondicionMeteorologica(ResultSet rs) throws SQLException {
        Calendar calendar = Calendar.getInstance();
        CondicionesMeteorologicas condicionesMeteorologicas = new CondicionesMeteorologicas();

        condicionesMeteorologicas.setVelViento(rs.getDouble("velViento"));
        condicionesMeteorologicas.setTemperaturaMin(rs.getDouble("temperaturaMin"));
        condicionesMeteorologicas.setTemperaturaMax(rs.getDouble("temperaturaMax"));
        condicionesMeteorologicas.setTemperaturaActual(rs.getDouble("temperaturaActual"));
        condicionesMeteorologicas.setSensacionTermica(rs.getDouble("sensacionTermica"));
        condicionesMeteorologicas.setPresion(rs.getDouble("presion"));
        condicionesMeteorologicas.setHumedad(rs.getDouble("humedad"));
        condicionesMeteorologicas.setEstadoClima(rs.getString("estadoClima"));
        condicionesMeteorologicas.setDirViento(rs.getDouble("dirViento"));
        calendar.setTimeInMillis(rs.getLong("fechaCondiciones"));
        condicionesMeteorologicas.setFechaCondiciones(calendar);
        calendar.setTimeInMillis(rs.getLong("fechaPeticion"));
        condicionesMeteorologicas.setFechaPeticion(calendar);
        return condicionesMeteorologicas;
    }

}