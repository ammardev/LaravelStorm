package com.smartbit8.laravelstorm.ui;

import com.intellij.ui.ListCellRendererWrapper;
import javax.swing.JList;
import javax.swing.Icon;

public class Renderer<T> extends ListCellRendererWrapper<T> {
    @Override
    public void customize(JList jList, Object o, int i, boolean b, boolean b1) {
        Item item = (Item) o;

        setText(item.getText());
        setIcon(item.getIcon());
    }
}

class Item {
    private Icon icon;
    private String text;
    private String commands[];

    Item(String text, Icon icon, String... commands) {
        this.icon = icon;
        this.text = text;
        this.commands = commands;
    }

    Icon getIcon() {
        return icon;
    }

    String getText() {
        return text;
    }

    public String[] getCommands() {
        return commands;
    }
}