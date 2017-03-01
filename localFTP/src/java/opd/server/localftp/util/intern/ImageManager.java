package opd.server.localftp.util.intern;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author pdgomezl
 */
@Named(value = "imageController")
@RequestScoped
public class ImageManager implements Serializable {

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
