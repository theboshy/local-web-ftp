package opd.server.localftp.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author pdgomezl
 */
@Named(value = "sessionManager")
@RequestScoped
public class SessionManager {

    public String getServerUser() {
        return System.getProperty("user.name");
    }
}
