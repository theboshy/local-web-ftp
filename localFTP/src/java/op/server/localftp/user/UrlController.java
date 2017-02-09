package op.server.localftp.user;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author pdgomezl
 */
@Named(value = "urlController")
@SessionScoped
public class UrlController implements Serializable {

    private String carpeta = "\\Desktop\\side deck\\";
    private String userHomeDesktop = System.getProperty("user.home") + "\\Desktop\\";
    private final String home = System.getProperty("user.home");
    //private String ruta = "C";

    /**
     * Creates a new instance of UrlController
     */
    public UrlController() {
    }

    public String getCarpeta() {
        return carpeta;
    }

    /*
    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
     */
    public String getUserHomeDesk() {
        return userHomeDesktop;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public void setUserHomeDesktop(String userHomeDesktop) {
        this.userHomeDesktop = userHomeDesktop;
    }

    public String getHome() {
        return home;
    }

}
