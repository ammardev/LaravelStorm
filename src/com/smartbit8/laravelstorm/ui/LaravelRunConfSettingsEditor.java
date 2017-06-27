package com.smartbit8.laravelstorm.ui;

import com.intellij.ide.browsers.BrowserSelector;
import com.intellij.ide.browsers.WebBrowser;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.smartbit8.laravelstorm.run.LaravelRunConf;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.UUID;


public class LaravelRunConfSettingsEditor extends SettingsEditor<LaravelRunConf> {
    private JPanel mainPanel;
    private JTextField hostInput;
    private JSpinner portInput;
    private JTextField routeInput;
    private JButton hostSetDefault;
    private JButton portSetDefault;
    private JButton routeSetDefault;
    private JPanel browserInputContainer;
    private BrowserSelector browserInput;
    @Override
    protected void resetEditorFrom(@NotNull LaravelRunConf laravelRunConf) {
        hostInput.setText(laravelRunConf.getHost());
        portInput.setValue(laravelRunConf.getPort());
        routeInput.setText(laravelRunConf.getRoute());
        browserInput.setSelected(laravelRunConf.getBrowser());
    }

    @Override
    protected void applyEditorTo(@NotNull LaravelRunConf laravelRunConf) throws ConfigurationException {
        laravelRunConf.setHost(hostInput.getText());
        laravelRunConf.setPort((int) portInput.getValue());
        laravelRunConf.setRoute(routeInput.getText());
        laravelRunConf.setBrowser(browserInput.getSelected());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }

    private void createUIComponents() {
        portInput = new JSpinner(new SpinnerNumberModel(8000, 1024, 65535, 1));
        JSpinner.NumberEditor editor;
        portInput.setEditor(editor = new JSpinner.NumberEditor(portInput, "#"));
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        browserInput = new BrowserSelector();
        browserInputContainer = new JPanel(new GridLayout(0, 1));
        browserInputContainer.add(browserInput.getMainComponent());
    }

    public LaravelRunConfSettingsEditor() {
        ActionListener listener = actionEvent -> {
            if (actionEvent.getSource() == hostSetDefault)
                hostInput.setText("localhost");
            if (actionEvent.getSource() == portSetDefault)
                portInput.setValue(8000);
            if (actionEvent.getSource() == routeSetDefault)
                routeInput.setText("/");
        };
        hostSetDefault.addActionListener(listener);
        portSetDefault.addActionListener(listener);
        routeSetDefault.addActionListener(listener);
    }
}
