package org.localftp.process;

import java.io.File;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.json.simple.JSONObject;
import org.localftp.intern.natives.CommandManager;
import org.localftp.util.JsfUtil;
import org.localftp.util.JsonUtil;

/**
 *
 * @author theboshy
 */
@Named(value = "deployerBean")
@SessionScoped
public class Deployer implements Serializable {

    private String deployName;
    private static final String DEPLOY_MGR = "deploy --name %s %s";
    private static final String DEPLOY_UN_MGR = "undeploy %s";
    private static final String DEPLOY_PARM_CONTEXT = "server_contex_root";
    private static final String DEPLOY_PARM_ROOT_COMMAND = "root_command";
    private static final String DEPLOY_FILE = "\\org\\localftp\\deploy_config.json";

    @PostConstruct
    public void init() {
        System.out.println("yee");
    }

    public void processDeployApp(String appRoot) {
        String serverContext;
        String serverRootCommand;
        JSONObject json;
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            URL url = classloader.getResource(DEPLOY_FILE);
            json = JsonUtil.fromJsonFile(new File(url.toURI()));
            serverContext = json.get(DEPLOY_PARM_CONTEXT).toString();
            serverRootCommand = json.get(DEPLOY_PARM_ROOT_COMMAND).toString();
            if ((!serverContext.isEmpty() && !serverRootCommand.isEmpty())
                    && Files.exists(Paths.get(serverContext + "\\" + serverRootCommand))) {
                Object buildCommand = String.format(serverContext + "\\" + serverRootCommand + " " + DEPLOY_MGR, deployName.isEmpty() ? "default_name" : deployName, "\"" + appRoot + "\"");
                Object unBuildCommand = String.format(serverContext + "\\" + serverRootCommand + " " + DEPLOY_UN_MGR, deployName.isEmpty() ? "default_name" : deployName);
                CommandManager.execCommand(unBuildCommand.toString());
                String result = CommandManager.execCommand(buildCommand.toString());
                JsfUtil.addWarningMessage(result);
            }
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getDeployName() {
        return deployName;
    }

    public void setDeployName(String deployName) {
        this.deployName = deployName;
    }

}
