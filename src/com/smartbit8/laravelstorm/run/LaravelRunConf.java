package com.smartbit8.laravelstorm.run;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.*;
import com.intellij.execution.process.*;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.ide.browsers.*;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.util.xmlb.SkipDefaultsSerializationFilter;
import com.intellij.util.xmlb.XmlSerializer;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.config.interpreters.PhpInterpretersManagerImpl;
import com.smartbit8.laravelstorm.ui.LaravelRunConfSettingsEditor;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;


public class LaravelRunConf extends RunConfigurationBase {
    private Project project;
    private String host = "localhost";
    private int port = 8000;
    private String route = "/";
    private WebBrowser browser;
    private PhpInterpreter interpreter;



    LaravelRunConf(@NotNull Project project, @NotNull ConfigurationFactory factory, String name) {
        super(project, factory, name);
        this.project = project;

    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        super.readExternal(element);
        Settings settings = XmlSerializer.deserialize(element, Settings.class);
        this.host = settings.host;
        this.port = settings.port;
        this.route = settings.route;
        this.browser = WebBrowserManager.getInstance().findBrowserById(settings.browser);
        this.interpreter = PhpInterpretersManagerImpl.getInstance(getProject()).findInterpreter(settings.interpreterName);

    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        Settings settings = new Settings();
        settings.host = this.host;
        settings.port = this.port;
        settings.route = this.route;
        if (this.browser != null)
            settings.browser = this.browser.getId().toString();
        else
            settings.browser = "";

        if (this.interpreter != null)
            settings.interpreterName = this.interpreter.getName();
        else
            settings.interpreterName = "";

        XmlSerializer.serializeInto(settings, element, new SkipDefaultsSerializationFilter());
        super.writeExternal(element);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends RunConfiguration> getConfigurationEditor() {
        return new LaravelRunConfSettingsEditor(getProject());
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {}


    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {

        return new CommandLineState(executionEnvironment) {
            @NotNull
            @Override
            protected ProcessHandler startProcess() throws ExecutionException {
                String phpExec = (interpreter != null? interpreter.getPathToPhpExecutable():"php");

                GeneralCommandLine cmd = new GeneralCommandLine(phpExec, "artisan", "serve", "--host=" + host, "--port="+ port);
//                cmd.setWorkDirectory(project.getBasePath());


                OSProcessHandler handler = new OSProcessHandler(cmd);

                handler.addProcessListener(new ProcessAdapter() {
                    @Override
                    public void onTextAvailable(ProcessEvent event, Key outputType) {
                        String text = event.getText();
                        if (text != null){
                            if (text.startsWith("Laravel development server started:")){
                                BrowserLauncher.getInstance().browse("http://" + host + ":" + port +
                                        (route.startsWith("/") ? route : "/" + route), browser);
                                handler.removeProcessListener(this);

                            }
                        }

                    }
                });

                new LaravelRunMgr(handler, new File(getProject().getBasePath()+("/storage/logs/laravel.log")));



                return handler;
            }

        };
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public WebBrowser getBrowser() {
        return browser;
    }

    public void setBrowser(WebBrowser browser) {
        this.browser = browser;
    }

    public PhpInterpreter getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(PhpInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public static class Settings {
        public String host;
        public int port;
        public String route;
        public String browser;
        public String interpreterName;
    }

}
