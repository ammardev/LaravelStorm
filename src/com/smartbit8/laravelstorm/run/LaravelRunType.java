package com.smartbit8.laravelstorm.run;


import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;
import com.smartbit8.laravelstorm.Icons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class LaravelRunType implements ConfigurationType {
    @Override
    public String getDisplayName() {
        return "Laravel";
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Artisan server runner";
    }

    @Override
    public Icon getIcon() {
        return Icons.LARAVEL;
    }

    @NotNull
    @Override
    public String getId() {
        return "LaravelStorm_run_conf_serve";
    }

    @Override
    public ConfigurationFactory[] getConfigurationFactories() {
        return new ConfigurationFactory[]{new ConfigurationFactory(this) {
            @NotNull
            @Override
            public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
                return new LaravelRunConf(project, this, "Laravel");
            }
        }};
    }
}
