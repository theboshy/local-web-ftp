/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opd.server.localftp.controller;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
