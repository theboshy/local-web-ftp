package org.elsamplio.localftp.process;

import org.elsamplio.localftp.util.JsfUtil;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.NotFoundException;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author pdgomezl
 */
@Named(value = "errorFiles")
@SessionScoped
public class ErrorFiles implements Serializable {

    private List<UploadedFile> nombresArchivosErroneos = new ArrayList<>();
    private String ruta;

    /**
     * Creates a new instance of ArchivosErroneosontroller
     */
    public ErrorFiles() {
    }

    public List<UploadedFile> getNombresArchivosErroneos() {
        return nombresArchivosErroneos;
    }

    public void setNombresArchivosErroneos(List<UploadedFile> nombresArchivosErroneos) {
        this.nombresArchivosErroneos = nombresArchivosErroneos;
    }

    public String getSelected() {
        return ruta;
    }

    public void setSelected(String ruta) {
        this.ruta = ruta;
    }

    public void reemplazar(UploadedFile up) throws IOException, Exception {
        File file = new File(ruta + up.getFileName());
        //  FileTemp archivoIVRDTO;
        try {
            // archivoIVRDTO = new FileTemp();
            if (file.exists()) {
                file.delete();
                // up.write(up.getFileName());
                file = new File(up.getFileName());
                file.renameTo(new File(ruta + up.getFileName()));
            }
            /*
            archivoIVRDTO.setSize(cargueController.informaticSize(up.getSize()));
            archivoIVRDTO.setAnexo(ruta + up.getFileName());
            archivoIVRDTO.setNombreArchivo(cargueController.extracOnlyName(up.getFileName()));
            archivoIVRDTO.setTipoArchivo(cargueController.extractExtension(up.getFileName()));
            archivoIVRDTO.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
             */
            //ejbControlArchivoIvr.insert(archivoIVRDTO);

            for (UploadedFile nombresArchivosErroneo : nombresArchivosErroneos) {
                if (nombresArchivosErroneo.equals(up)) {
                    nombresArchivosErroneos.remove(nombresArchivosErroneo);
                    break;
                }
            }
            JsfUtil.addSuccessMessage("Se remplazo correctamente el archivo " + up.getFileName());
        } catch (NotFoundException e) {
            JsfUtil.addWarningMessage("No se reconoce el archivo : " + up.getFileName());
        }
        /*int size = nombresArchivosErroneos.size();
        for (int i = 1; i <= size; i++) {
            if (nombresArchivosErroneos.get(i - 1).getFileName().equals(up.getFileName())) {
                nombresArchivosErroneos.remove(nombresArchivosErroneos.get(i - 1));
            }
        }*/
    }

}
