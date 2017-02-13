package opd.server.localftp.process;

import opd.server.localftp.user.UrlManager;
import opd.server.localftp.util.extra.ZipController;
import opd.server.localftp.util.JsfUtil;
import opd.server.localftp.pojo.FileTempPOJO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author pdgomezl
 */
@Named(value = "uploaderManager")
@RequestScoped
public class Uploader implements Serializable {

    @Inject
    private UrlManager urlController;
    @Inject
    private ErrorFiles archivosErroneosController;
    //private final int unitKiloByteInByte = 1024;
    //private final int unitMegaByteInKiloByte = 1048576;
    private UploadedFile file;
    private String urlToMove;
    // private FileTemp archivo = null;
    private List<FileTempPOJO> items;
    private String findBySomething;
    private static final String PRINCIPAL_FOLDER = System.getProperty("user.home");

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
     * Recibe un objeto generico de tipo FileUploadEvent el cual se transformara
     * a uno de tipo File para obtener sus atributos y crear este en un
     * directorio por defecto c:/audios
     *
     * @param event objeto generico subido al aplicativo
     * @throws IOException en caso de que ocurra un error al intentar encontrar
     * el archivo,crearlo,moverlo
     * @throws SQLException en caso de que ocurra un error interno en la base de
     * datos como puede ser un dato que supera el limite establecido
     */
    public void upload(FileUploadEvent event) throws IOException, SQLException {
        //String ruta = urlController.getRuta();
        //archivo = new FileTemp();
        try {
            if (event.getFile() != null) {
                file = event.getFile();
                try (FileOutputStream archivoOutput = new FileOutputStream(file.getFileName())) {
                    archivoOutput.write(file.getContents());
                    archivoOutput.flush();
                    archivoOutput.close();
                    archivoOutput.getChannel().close();
                }

                File archivoTemp = new File(file.getFileName());

                Path path = Paths.get(PRINCIPAL_FOLDER + urlController.getCarpeta());
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                urlToMove = PRINCIPAL_FOLDER + urlController.getCarpeta() + "\\" + archivoTemp.getName();

                archivoTemp = new File(urlToMove);
                if (archivoTemp.exists()) {

                    archivosErroneosController.getNombresArchivosErroneos().add(file);
                    archivosErroneosController.setSelected(PRINCIPAL_FOLDER + urlController.getCarpeta() + "\\");
                    JsfUtil.addWarningMessage("El archivo " + file.getFileName() + " ya existe");

                    //archivoTemp.delete();
                    //archivoIVRDTO.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
                } else {
                    /*
                    archivo.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
                    archivo.setSize(informaticSize(file.getSize()));
                    archivo.setAnexo(url + "\\" + file.getFileName());
                    archivo.setNombreArchivo(extracOnlyName(file.getFileName()));
                    archivo.setTipoArchivo(extractExtension(file.getFileName()));
                     */
                    archivoTemp = new File(file.getFileName());
                    archivoTemp.renameTo(new File(urlToMove));
                    //guardar archivo en bd - ejbControlArchivoIvr.insert(archivoIVRDTO);
                    JsfUtil.addSuccessMessage("Archivo subido y archivado ");
                }
            }
        } catch (OutOfMemoryError | ArrayIndexOutOfBoundsException | IllegalAccessError | InvalidPathException e) {
            JsfUtil.addErrorMessage("Ocurrio un error :/ !");
            JsfUtil.addErrorMessage("Asegurese de que la ruta no contenga caracteres especiales!");
            System.out.println(e.getMessage());
        }
        file = null;
        items = null;
    }

    /**
     * Transforma una url que contenga back slash separator de tipo "\" a simple
     * slash separator "/"
     *
     * @param url url "malformada" para transformar a una de tipo simple slash
     * separator"/"
     * @return url de tipo String de forma simple slash separator
     */
    private String urlToNormiesEyes(String url) {
        String finalUrl = "";
        for (char value : url.toCharArray()) {
            if (value == '\\') {
                finalUrl += "/";
            } else {
                finalUrl += value;
            }
        }
        return finalUrl + file.getFileName();
    }

    /**
     * Extrae la extencion de el nombre de un archivo como .exe/.pdf/.txt
     *
     * @param toConvert Nombre del archivo
     * @return Extencion del archivo solicitado
     */
    public String extractExtension(String toConvert) {
        String extrac = "";
        List<Character> toOrder = new ArrayList<>();
        List<Character> list = new ArrayList<>();
        for (char runner : toConvert.toCharArray()) {
            toOrder.add(runner);
        }
        for (int i = toOrder.size(); i > 0; i--) {
            if (toOrder.get(i - 1).equals('.')) {
                break;
            } else {
                extrac += toOrder.get(i - 1);
            }
        }
        for (Character character : extrac.toCharArray()) {
            list.add(character);
        }
        extrac = "";
        for (int i = list.size(); i > 0; i--) {
            extrac += list.get(i - 1);
        }

        return extrac;
    }

    /**
     * Recibe el nombre completo (cuando es obtenido directamente de un archivo
     * de tipo File) de un archivo para obtener solo su nombre
     *
     * @param toConvert Nombre del archivo
     * @return Nombre del archivo separado de su extencion
     */
    public String extracOnlyName(String toConvert) {
        String extrac = "";
        boolean found = false;
        List<Character> toOrder = new ArrayList<>();
        for (char runner : toConvert.toCharArray()) {
            toOrder.add(runner);
        }

        for (int i = toOrder.size(); i > 0; i--) {
            if (toOrder.get(i - 1).equals('.')) {
                found = true;
                toOrder.remove(i - 1);
                break;
            } else {
                toOrder.remove(i - 1);
            }
        }

        if (found) {
            for (char character : toOrder) {
                extrac += character;
            }
        }
        return extrac;
    }

    /**
     * Obtiene objetos los cuales contendran la informacion de los archivos
     * almacenados en la base de datos
     *
     * @return Lista de objetos con informacion de los archivos almacenados en
     * la base de datos
     * @throws SQLException en caso de que ocurra un error interno en la base de
     * datos como la insercion de un dato que sobrepasa el limite
     */
    public List<FileTempPOJO> getItems() throws SQLException {
        FileTempPOJO fileTempPOJO;
        try {
            if (items == null) {
                items = new ArrayList<>();
                if (!urlController.getCarpeta().equals(PRINCIPAL_FOLDER) || urlController.getCarpeta() != null) {
                    File dir = new File(PRINCIPAL_FOLDER + urlController.getCarpeta());
                    File[] ficheros = dir.listFiles();
                    if (dir.exists()) {
                        if (ficheros != null) {
                            for (File fichero : ficheros) {
                                fileTempPOJO = new FileTempPOJO();
                                fileTempPOJO.setNombreArchivo(extracOnlyName(fichero.getName()));
                                if (fileTempPOJO.getNombreArchivo().equals("")) {
                                    File fileTempToDelete = new File(PRINCIPAL_FOLDER + urlController.getCarpeta() + extractExtension(fichero.getName()) + ZipController.getType());
                                    if (fileTempToDelete.exists()) {
                                        fileTempToDelete.delete();
                                    }
                                    fileTempPOJO.setNombreArchivo(null);
                                }
                                fileTempPOJO.setTipoArchivo(extractExtension(fichero.getName()));
                                fileTempPOJO.setAnexo(fichero.getAbsolutePath());
                                fileTempPOJO.setSize(informaticSize(fichero.length()));
                                items.add(fileTempPOJO);
                            }
                        } else {
                            JsfUtil.addErrorMessage("No hay ficheros en el directorio especificado");
                        }
                    } else {
                        JsfUtil.addErrorMessage("No existe el directorio : " + dir.getPath());
                    }
                } else {
                    JsfUtil.addErrorMessage("Especifique una carpeta ");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    /**
     * Permite descargar un archivo almacenado en el servidor
     *
     * @param archivoPath ruta del archivo
     * @param nombre nombre del archivo
     * @param tipo tipo del archivo
     * @return archivo para descargar de tipo streamedContent
     * @throws FileNotFoundException en caso de que la rutaespecificada o el
     * nombre del archivo esten mal no se reconocera estte y saltara la
     * exception de no encontrado
     * @throws IOException en caso de que ocurra un error interno como la falta
     * de permisos al intentar mover el archivo
     */
    public StreamedContent getArchivoDownl(String archivoPath, String nombre, String tipo) throws FileNotFoundException, IOException {
        String archivoTemp = nombre + "." + tipo;
        DefaultStreamedContent defaultStreamedContent = null;
        try {
            /*
        Path path = Paths.get("C:\\audios\\ProductosM.xhtml");
        byte[] data = Files.readAllBytes(path);
        byte[] bFile = new byte[(int) file.length()];
        fileInputStream = new FileInputStream(file);
        fileInputStream.read(data);
             */
            File fileFromDirectory = new File(archivoPath);
            if (archivoTemp != null && fileFromDirectory.exists()) {
                if (archivoTemp.startsWith(".")) {
                    ZipController.folderToZip(archivoPath);
                    defaultStreamedContent = new DefaultStreamedContent(new FileInputStream(archivoPath + ZipController.getType()), "", withoutChar(archivoTemp, '.') + ZipController.getType());
                } else {
                    defaultStreamedContent = new DefaultStreamedContent(new FileInputStream(fileFromDirectory), "", archivoTemp);
                }
            } else {
                JsfUtil.addErrorMessage("Este Archivo ya no existe en la carpeta contenedora");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            JsfUtil.addWarningMessage("No se encuentra el archivo " + incompleteText(nombre, 20));
        }
        return defaultStreamedContent;
    }

    /**
     * Permite transformar un valor en bytes a su valor mas dependiente
     *
     * @param bytes valor en bytes
     * @return valor de tipo cadena en su valor dependiente (si el valor en
     * bytes sobre-pasa un KB entonces retornara MB)
     */
    public String informaticSize(long bytes) {
        double kb = bytes / 1024.0;
        double mb = bytes / 1048576.0;
        /*
        double gb = bytes / Math.pow(mb, 2);
        double tb = bytes / Math.pow(gb, 2);
         */
        DecimalFormat dec = new DecimalFormat("0.00");

        String size = "0 PTBytes";
        /*if (tb > 1) {
            size = dec.format(tb).concat(" TB");
        } else if (gb > 1) {
            size = dec.format(gb).concat(" GB");
        } else*/ if (mb > 1) {
            size = dec.format(mb).concat(" MB");
        } else if (kb > 1) {
            size = dec.format(kb).concat(" KB");
        } else {
            size = bytes + " B";
        }
        return size;
    }

    public static String incompleteText(String text, int max) {
        String incompleteText = text;
        if (text.length() > max) {
            incompleteText = "";
            short cont = 0;
            for (char runnerObject : text.toCharArray()) {
                if (cont == max) {
                    break;
                } else {
                    incompleteText += runnerObject;
                    cont++;
                }
            }
            return incompleteText + "...";
        }
        return incompleteText;
    }

    public int contCharacters(String string) {
        int cont = 0;
        for (Object object : string.toCharArray()) {
            cont++;
        }
        return cont;
    }

    public String getFindBySomething() throws SQLException {
        return findBySomething;
    }

    public void setFindBySomething(String findBySomething) throws SQLException {
        List<FileTempPOJO> listaTemp;
        try {
            this.findBySomething = findBySomething;
            listaTemp = new ArrayList<>();
            String value = findBySomething.toLowerCase();
            String nombre;
            String tipo;
            for (FileTempPOJO item : items) {
                tipo = item.getTipoArchivo().toLowerCase();
                if (item.getNombreArchivo() != null) {
                    nombre = item.getNombreArchivo().toLowerCase();
                    if (nombre.contains(value)) {
                        listaTemp.add(item);
                    }
                } else if (tipo.contains(value)) {
                    listaTemp.add(item);
                }
            }
            items = listaTemp.isEmpty() ? null : listaTemp;
            if (listaTemp.isEmpty()) {
                //  items = ejbControlArchivoIvr.archivosAlmacenados();
                JsfUtil.addWarningMessage("No hay resultados");
            }
            this.findBySomething = null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void reset() {
        this.items = null;
        JsfUtil.addSuccessMessage("Actualizado");
    }

    /**
     * Permite separar un caracter de una cadena completa
     *
     * @param value cadena inicial
     * @param separator valor a desprender de la cadena
     * @return el valor ingresado sin el valor ingresado para separar
     */
    public static String withoutChar(String value, char separator) {
        String withouFin = "";
        for (char object : value.toCharArray()) {
            if (object != separator) {
                withouFin += object;
            }
        }
        return withouFin;
    }

}
