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
    private JCheckBox additionCommandCheckBox;
    private Project project;
    private String artisanCommand;
    private String optionCmdParameter;
    public FileMakerDlg(@Nullable Project project, String artisanCommand) {
        super(project);
        this.project = project;
        this.artisanCommand = artisanCommand;
        init();
        setTitle("New Laravel File");
        setResizable(false);
    }

    public FileMakerDlg(@Nullable Project project, String artisanCommand, String optionText, String optionCmdParameter) {
        this(project, artisanCommand);
        this.additionCommandCheckBox.setVisible(true);
        this.additionCommandCheckBox.setText(optionText);
        this.optionCmdParameter = optionCmdParameter;
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


    @Override
    protected void doOKAction() {

        Notification notification = new Notification("LaravelStorm",
                "Success", "temp content", NotificationType.INFORMATION);

        Notification errorNotification = new Notification("LaravelStorm",
                "Error", "Could not create file.", NotificationType.ERROR);

        GeneralCommandLine cmd = new GeneralCommandLine("php", "artisan");
        cmd.setWorkDirectory(project.getBasePath());

        cmd.addParameter(artisanCommand);

        if (additionCommandCheckBox.isSelected())
            cmd.addParameter(optionCmdParameter);

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
