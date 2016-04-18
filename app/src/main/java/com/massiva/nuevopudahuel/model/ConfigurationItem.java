package com.massiva.nuevopudahuel.model;


/**
 * Created by moddity on 15/3/16.
 */
public class ConfigurationItem {

    private int id;
    private String text;
    private boolean checked;

    public ConfigurationItem(){}

    public ConfigurationItem(int id, String text, boolean checked) {
        this.id = id;
        this.text = text;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
