package com.smartbit8.laravelstorm.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.smartbit8.laravelstorm.Icons;
import com.smartbit8.laravelstorm.ui.FileMakerDlg;

public class MiddlewareMaker extends AnAction {

    public MiddlewareMaker() {
        super(Icons.MIDDLEWARE);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        FileMakerDlg dialog = new FileMakerDlg(e.getProject(), "make:middleware");
        dialog.show();
    }
}
