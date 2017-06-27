package com.smartbit8.laravelstorm.ui;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.smartbit8.laravelstorm.Icons;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileMakerDlg extends DialogWrapper{

    private JPanel mainPanel;
    private JTextField fileName;
    private ComboBox<Item> fileTypeChooser;
    private Project project;
    private Item items[] = new Item[6];

    public FileMakerDlg(@Nullable Project project) {
        super(project);
        this.project = project;
        init();
        setTitle("Choose File Type");
        setResizable(false);
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        ValidationInfo info = new ValidationInfo("Please enter a correct file name.");
        if (!fileName.getText().isEmpty() && fileName.getText().replaceAll("([a-zA-Z0-9_-])|([.])","").isEmpty())
            return null;
        else
            return info;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        items[0] = new Item("Controller", Icons.CONTROLLER,"make:controller");
        items[1] = new Item("Middleware", Icons.MIDDLEWARE,"make:middleware");
        items[2] = new Item("Model", Icons.MODEL,"make:model");
        items[3] = new Item("Model with migration", Icons.MODEL_WITH_MIGRATION,"make:model", "-m");
        items[4] = new Item("Migration", Icons.MIGRATION,"make:migration");
        items[5] = new Item("Seeder", Icons.SEEDER,"make:seeder");
        // TODO: initialize the array from xml conf file and let the user show or hide any command from the settings

        fileTypeChooser = new ComboBox<>(items);
        fileTypeChooser.setRenderer(new Renderer<>());
    }

    @Override
    protected void doOKAction() {

        Notification notification = new Notification("LaravelStorm",
                "Success", "temp content", NotificationType.INFORMATION);

        Notification errorNotification = new Notification("LaravelStorm",
                "Error", "Could not create file.", NotificationType.ERROR);

        GeneralCommandLine cmd = new GeneralCommandLine("php", "artisan");
        cmd.setWorkDirectory(project.getBasePath());

        cmd.addParameters(items[fileTypeChooser.getSelectedIndex()].getCommands());

        cmd.addParameter(fileName.getText());

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(cmd.createProcess().getInputStream()));
            String execResult = reader.readLine();
            if (execResult.isEmpty()){
                Notifications.Bus.notify(errorNotification, project);
            }
            else{
                notification.setContent(execResult);
                Notifications.Bus.notify(notification, project);
            }
        } catch (ExecutionException | IOException e) {
            e.printStackTrace();
        }

        super.doOKAction();
    }
}
