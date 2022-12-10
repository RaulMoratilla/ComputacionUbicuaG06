package basedatos;

public class Zona {
    private int _id, _codigoCiudad;
    private String _nombre;

    //ctor.
    public Zona(int p_codigoCiudad, int p_id, String p_nombre) {
        this._id = p_id;
        this._nombre = p_nombre;
        this._codigoCiudad = p_codigoCiudad;
    }

    //getters and setters
    public int getId() {
        return _id;
    }

    public void setId(int p_id) {
        this._id = p_id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String p_nombre) {
        this._nombre = p_nombre;
    }

    public int getCodigoCiudad() {
        return _codigoCiudad;
    }

    public void setCodigoCiudad(int p_codigoCiudad) {
        this._codigoCiudad = p_codigoCiudad;
    }

    @Override
    public String toString() {
        return "Zona " + _id + ": {nombre=" + _nombre + ", codigoCiudad=" + _codigoCiudad + "}";
    }
}
