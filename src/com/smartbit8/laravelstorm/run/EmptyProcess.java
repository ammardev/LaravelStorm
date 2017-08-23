package com.smartbit8.laravelstorm.run;

import com.intellij.execution.process.ProcessHandler;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;

public class EmptyProcess extends ProcessHandler{
    @Override
    protected void destroyProcessImpl() {

    }

    @Override
    protected void detachProcessImpl() {

    }

    @Override
    public boolean detachIsDefault() {
        return false;
    }

    @Nullable
    @Override
    public OutputStream getProcessInput() {
        return null;
    }
}
