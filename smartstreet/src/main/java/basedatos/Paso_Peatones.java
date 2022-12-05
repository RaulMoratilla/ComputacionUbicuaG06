package basedatos;

public class Paso_Peatones {
    private int _id, _longitud, _idZonaCalle, _codigoCiudad;
    private String _nombreCalle;

    //ctor.
    public Paso_Peatones(int p_id, int p_altura, String p_nombreCalle, int p_idZonaCalle, int p_codigoCiudad) {
        this._id = p_id;
        this._longitud = p_altura;
        this._nombreCalle = p_nombreCalle;
        this._idZonaCalle = p_idZonaCalle;
        this._codigoCiudad = p_codigoCiudad;
    }

    //getters and setters
    public int getId() {
        return _id;
    }

    public void setId(int p_id) {
        this._id = p_id;
    }

    public int getAltura() {
        return _longitud;
    }

    public void setAltura(int p_altura) {
        this._longitud = p_altura;
    }

    public String getNombreCalle() {
        return _nombreCalle;
    }

    public void setNombreCalle(String p_nombreCalle) {
        this._nombreCalle = p_nombreCalle;
    }

    public int getIdZonaCalle() {
        return _idZonaCalle;
    }

    public void setIdZonaCalle(int p_idZonaCalle) {
        this._idZonaCalle = p_idZonaCalle;
    }

    public int getCodigoCiudad() {
        return _codigoCiudad;
    }

    public void setCodigoCiudad(int p_codigoCiudad) {
        this._codigoCiudad = p_codigoCiudad;
    }

    @Override
    public String toString() {
        return "Paso_Peatones{" +
                "_id=" + _id +
                ", _longitud=" + _longitud +
                ", _nombreCalle='" + _nombreCalle + '\'' +
                ", _idZonaCalle=" + _idZonaCalle +
                ", _codigoCiudad=" + _codigoCiudad +
                '}';
    }
}
