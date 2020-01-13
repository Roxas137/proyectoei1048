package es.uji.ei1048.facade;

import es.uji.ei1048.object.CondicionesMeteorologicas;
import es.uji.ei1048.object.Coordenadas;
import es.uji.ei1048.exceptions.InvalidCityException;
import es.uji.ei1048.exceptions.InvalidCoordenatesException;
import es.uji.ei1048.object.LugarFavorito;

import java.util.List;

public interface IWeatherAppFacade {
    CondicionesMeteorologicas getCondicionesActuales(String ciudad) throws InvalidCityException;
    CondicionesMeteorologicas getCondicionesActuales(Coordenadas coord) throws InvalidCoordenatesException;
    List<CondicionesMeteorologicas> getPrediccion(String ciudad) throws InvalidCityException;
    List<CondicionesMeteorologicas> getPrediccion(Coordenadas coord) throws InvalidCoordenatesException;
    List<LugarFavorito> getLugaresFavoritos();
    boolean registraLugarFavorito(String city, Coordenadas coordenadas, String etiqueta) throws  InvalidCityException, InvalidCoordenatesException;
    boolean eliminaLugarFavorito(LugarFavorito lugarFavorito);

}
