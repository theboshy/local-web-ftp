package opd.server.localftp.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author pdgomezl
 */
@Named(value = "urlController")
@SessionScoped
public class UrlManager implements Serializable {

    private String carpeta = "\\Desktop\\side deck\\";
    private String userHomeDesktop = System.getProperty("user.home") + "\\Desktop\\";
    private final String home = System.getProperty("user.home");
    //private String ruta = "C";

    /**
     * Creates a new instance of UrlController
     */
    public UrlManager() {
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

    /**
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @deprecated
     */
    public List<String> getFolders(String path) throws FileNotFoundException, IOException {
        List<String> folders = null;
        File dir = new File(path);
        File[] ficheros = dir.listFiles();
        if (dir.exists()) {
            if (ficheros == null) {
                System.out.println("No hay ficheros en el directorio especificado");
            } else {
                folders = new ArrayList<>();
                for (File fichero : ficheros) {
                    //System.out.println(fichero.getName());
                    folders.add(fichero.getCanonicalPath());
                }
            }
        } else {
            System.out.println("No existe el directorio : " + dir.getPath());
        }
        return folders;
    }
}
