package appcpanama.logicstudio.net.appcpanama.JavaBeans;

/**
 * Created by LogicStudio on 24/10/16.
 */

public class CondicionAnimalBeans {

    String nombre;
    Integer codigoCondicion;

    public void setCodigoCondicion(Integer codigoCondicion) {
        this.codigoCondicion = codigoCondicion;
    }

    public Integer getCodigoCondicion() {
        return codigoCondicion;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public CondicionAnimalBeans(String nombre, Integer codigoCondicion)
    {
        this.nombre = nombre;
        this.codigoCondicion = codigoCondicion;

    }

}
