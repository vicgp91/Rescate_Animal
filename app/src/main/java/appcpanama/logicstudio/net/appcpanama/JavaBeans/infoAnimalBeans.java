package appcpanama.logicstudio.net.appcpanama.JavaBeans;

/**
 * Created by LogicStudio on 24/10/16.
 */

public class infoAnimalBeans {

    String nombre;

    Integer img;
    Integer imgMenu;
    String nombreCientifico;
    String rutaHtml;

    public String getRutaHtml() {
        return rutaHtml;
    }

    public void setRutaHtml(String rutaHtml) {
        this.rutaHtml = rutaHtml;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public Integer getImgMenu() {
        return imgMenu;
    }

    public void setImgMenu(Integer imgMenu) {
        this.imgMenu = imgMenu;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public infoAnimalBeans(String nombre, Integer img, Integer imgMenu, String nombreCientifico, String rutaHtml)
    {
        this.nombre = nombre;
        this.img=img;
        this.imgMenu = imgMenu;
        this.nombreCientifico=nombreCientifico;
        this.rutaHtml = rutaHtml;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

}
