package com.smartbit8.laravelstorm.run;

import com.intellij.diagnostic.logging.AdditionalTabComponent;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.ui.DarculaColors;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;
import org.apache.batik.util.gui.resource.JToolbarButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LogTab extends AdditionalTabComponent {

    private Project project;
    private ConsoleView console;
    private EmptyProcess handler;
    private ActionGroup group;

    public LogTab(Project project) {
        super(new BorderLayout());
        this.project = project;
        handler = new EmptyProcess();
        group = new ActionGroup() {
            @NotNull
            @Override
            public AnAction[] getChildren(@Nullable AnActionEvent anActionEvent) {
                return console.createConsoleActions();
            }
        };
        console = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        console.attachToProcess(handler);


    }

    @NotNull
    @Override
    public String getTabTitle() {
        return "Laravel.log";
    }

    @Override
    public JComponent getPreferredFocusableComponent() {
        return console.getPreferredFocusableComponent();
    }

    @Override
    public void dispose() {

    }

    @Nullable
    @Override
    public ActionGroup getToolbarActions() {
        return group;
    }

    @Nullable
    @Override
    public JComponent getSearchComponent() {
        return null;
    }

    @Nullable
    @Override
    public String getToolbarPlace() {
        return ActionPlaces.UNKNOWN;
    }

    @Nullable
    @Override
    public JComponent getToolbarContextComponent() {
        return console.getComponent();
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        add(console.getComponent(), BorderLayout.CENTER);

        return this;
    }

    @Override
    public boolean isContentBuiltIn() {
        return false;
    }

    public void start(){

        new LaravelRunMgr(handler, new File(project.getBasePath()+("/storage/logs/laravel.log")));

    }
}
