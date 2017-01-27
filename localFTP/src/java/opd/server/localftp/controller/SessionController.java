package opd.server.localftp.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author pdgomezl
 */
@Named(value = "sessionController")
@RequestScoped
public class SessionController {

    private String serverUser;

    public String getServerUser() {
        return System.getProperty("user.name");
    }
}
