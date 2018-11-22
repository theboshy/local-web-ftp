package org.elsamplio.localftp.user;

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
    private boolean isLogged;
    //private String ruta = "C";

    /**
     * Creates a new instance of UrlController
     */
    public UrlManager() {
    }

//    @PostConstruct
//    public void init() {
//        if(!isIsLogged()){
//            try {
//                FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
//            } catch (IOException ex) {
//                Logger.getLogger(UrlManager.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        setIsLogged(Boolean.FALSE);
//    }

    public String getCarpeta() {
        return carpeta;
    }

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

    public boolean isIsLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
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
