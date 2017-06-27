package com.smartbit8.laravelstorm.ui;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.smartbit8.laravelstorm.run.LaravelRunConf;
import org.jetbrains.annotations.NotNull;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;


public class LaravelRunConfSettingsEditor extends SettingsEditor<LaravelRunConf> {
    private JTextField host;
    private JSpinner port;
    private JPanel mainPanel;

    @Override
    protected void resetEditorFrom(@NotNull LaravelRunConf laravelRunConf) {
        port.setValue(laravelRunConf.getPort());
        host.setText(laravelRunConf.getHost());
    }

    @Override
    protected void applyEditorTo(@NotNull LaravelRunConf laravelRunConf) throws ConfigurationException {
        laravelRunConf.setPort((int)port.getValue());
        laravelRunConf.setHost(host.getText());
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return mainPanel;
    }

    private void createUIComponents() {
        port = new JSpinner(new SpinnerNumberModel(8000, 1024, 65535, 1));
        JSpinner.NumberEditor editor;
        port.setEditor(editor = new JSpinner.NumberEditor(port, "#"));
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);
    }
}
