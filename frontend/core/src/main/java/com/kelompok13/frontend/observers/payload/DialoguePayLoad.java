package com.kelompok13.frontend.observers.payload;

public class DialoguePayLoad {
    public final String text;
    public final String username;
    public final boolean show; // true show, false hide
    public DialoguePayLoad(String text, String username, boolean show){
        this.text = text;
        this.username = username;
        this.show = show;
    }
}
