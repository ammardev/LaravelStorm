package com.smartbit8.laravelstorm.run;

import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;

import java.io.*;

public class LaravelRunMgr implements Runnable {

    private ProcessHandler handler;
    private long pointer;
    private File file;

    LaravelRunMgr(ProcessHandler handler, File file) {
        this.handler = handler;
        this.file = file;
        pointer = file.length();
        new Thread(this).start();
    }

    @Override
    public void run() {

        while (!handler.isProcessTerminated()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                if (file.length() >= pointer)
                    reader.skip(pointer);

                String line;
                while ((line = reader.readLine()) != null){
                    if (line.contains(".ERROR: ") || line.equals("Stack trace:") || line.matches("(#\\d+ .+)"))
                        handler.notifyTextAvailable(line + "\n",ProcessOutputTypes.STDERR);
                    else
                        handler.notifyTextAvailable(line + "\n",ProcessOutputTypes.STDOUT);


                    pointer = file.length();
                }

                Thread.sleep(500);
            } catch (InterruptedException | IOException ignored){}
        }

    }
}
