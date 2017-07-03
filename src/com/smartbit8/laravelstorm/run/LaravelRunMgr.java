package com.smartbit8.laravelstorm.run;

import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;

import java.io.*;

public class LaravelRunMgr implements Runnable {

    private OSProcessHandler handler;
    private long pointer;
    private File file;

    LaravelRunMgr(OSProcessHandler handler, File file) {
        this.handler = handler;
        this.file = file;
        pointer = file.length();
        new Thread(this).start();
    }

    @Override
    public void run() {

        while (handler.getProcess().isAlive()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                if (file.length() >= pointer)
                    reader.skip(pointer);

                String line;
                while ((line = reader.readLine()) != null){
                    handler.notifyTextAvailable(line+"\n",
                            line.contains(".ERROR: ")?ProcessOutputTypes.STDERR:ProcessOutputTypes.STDOUT);
                    pointer = file.length();
                }

                Thread.sleep(500);
            } catch (InterruptedException | IOException ignored){}
        }

    }
}
