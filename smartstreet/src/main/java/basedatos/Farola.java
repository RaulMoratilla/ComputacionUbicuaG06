package basedatos;

public class Farola {
    private int _id, _altura, _idZonaCalle, _codigoCiudad;
    private String _nombreCalle;

    //ctor.
    public Farola(int p_id, int p_altura, String p_nombreCalle, int p_idZonaCalle, int p_codigoCiudad) {
        this._id = p_id;
        this._altura = p_altura;
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
        return _altura;
    }

    public void setAltura(int p_altura) {
        this._altura = p_altura;
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
        return "Farola{" +
                "_id=" + _id +
                ", _altura=" + _altura +
                ", _idZonaCalle=" + _idZonaCalle +
                ", _codigoCiudad=" + _codigoCiudad +
                ", _nombreCalle='" + _nombreCalle + '\'' +
                '}';
    }
}
