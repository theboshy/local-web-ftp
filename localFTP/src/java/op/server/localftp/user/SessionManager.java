package op.server.localftp.user;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author pdgomezl
 */
@Named(value = "sessionManager")
@RequestScoped
public class SessionManager {

    private String serverUser;

    public String getServerUser() {
        return System.getProperty("user.name");
    }
}
