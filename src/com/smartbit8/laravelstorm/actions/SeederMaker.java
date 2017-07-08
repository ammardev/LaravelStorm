package com.smartbit8.laravelstorm.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.smartbit8.laravelstorm.Icons;
import com.smartbit8.laravelstorm.ui.FileMakerDlg;

public class SeederMaker extends AnAction {

    public SeederMaker() {
        super(Icons.SEEDER);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        FileMakerDlg dialog = new FileMakerDlg(e.getProject(), "make:seeder");
        dialog.show();
    }
}
