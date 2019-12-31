package es.uji.ei1048.service.openWeatherMap;

/**
 * Las distintas manera que tiene OpenWeatherMap para identificar un lugar
 */
public enum OpenWeatherMapTypeId {ID, COORDENATES, NAME;

    public String[] getName() {
        switch (this) {
            case ID:
                return new String[]{"id"};
            case NAME:
                return new String[]{"q"};
            case COORDENATES:
                return new String[]{"lat", "lon"};
            default:
                return null;
        }
    }
}
