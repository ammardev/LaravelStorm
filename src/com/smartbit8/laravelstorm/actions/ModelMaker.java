package com.smartbit8.laravelstorm.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.smartbit8.laravelstorm.Icons;
import com.smartbit8.laravelstorm.ui.FileMakerDlg;

/**
 * Created by ammar on 7/8/17.
 */
public class ModelMaker extends AnAction {

    public ModelMaker() {
        super(Icons.MODEL);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        FileMakerDlg dialog = new FileMakerDlg(e.getProject(), "make:model",
            "Create migration for this model.", "-m");
        dialog.show();
    }
}
