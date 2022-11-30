package basedatos;

public class Farola {
    private int _id, _altura, _idZonaCalle;
    private String _nombreCalle;

    //ctor.
    public Farola(int p_id, int p_altura, String p_nombreCalle, int p_idZonaCalle) {
        this._id = p_id;
        this._altura = p_altura;
        this._nombreCalle = p_nombreCalle;
        this._idZonaCalle = p_idZonaCalle;
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

    @Override
    public String toString() {
        return "Farola " + _id + ": {altura=" + _altura + ", nombreCalle=" + _nombreCalle + ", idZonaCalle=" + _idZonaCalle + "}";
    }
}
