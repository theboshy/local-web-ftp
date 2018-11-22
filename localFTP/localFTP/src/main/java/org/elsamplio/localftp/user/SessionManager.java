package org.elsamplio.localftp.user;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author pdgomezl
 */
@Named(value = "sessionManager")
@RequestScoped
public class SessionManager {

    private String usuario;
    private String contrasena;

    @Inject
    private UrlManager urlManager;

    public String getServerUser() {
        return System.getProperty("user.name");
    }

    public void validarIngreso() {
        if (!urlManager.isIsLogged()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UrlManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String login() {
        if (usuario.equals("80148500") && contrasena.equals("Sergio25")) {
            urlManager.setIsLogged(true);
            return "index?faces-redirect=true";
        } else {
            return null;
        }
    }

    public String logout() {
        urlManager.setIsLogged(false);
        return "login?faces-redirect=true";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
