package appcpanama.logicstudio.net.appcpanama.JavaBeans;

/**
 * Created by LogicStudio on 24/10/16.
 */

public class infoAnimalBeans {

    String nombre;
    Integer img;


    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public infoAnimalBeans(String nombre, Integer img)
    {
        this.nombre = nombre;

        this.img=img;
    }

    public String getNombre() {
        return nombre;
    }


}
