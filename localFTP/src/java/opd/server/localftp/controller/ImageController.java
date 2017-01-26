/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opd.server.localftp.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author pdgomezl
 */
@Named(value = "imageController")
@RequestScoped
public class ImageController implements Serializable {

    private String ruta;

    public String getRuta() throws IOException {
        /*
        obtener archivo interno dentro de la carpeta web if/classes
        InputStream f = Thread.currentThread().getContextClassLoader().getResourceAsStream("contac/audios/presentacion/8.png");
         */
        Random rnd = new Random();
        int numeroImage = (int) (rnd.nextDouble() * 9 + 1);
        return "url(resources/images/backgrounds/" + numeroImage + ".jpg)";
    }

}
