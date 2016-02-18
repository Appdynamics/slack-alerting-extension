package com.appdynamics.extensions.slack.api;

/**
 * Created by balakrishnavadavalasa on 17/02/16.
 */
public class Attachment {
    private String title;
    private String color;
    private String text;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
