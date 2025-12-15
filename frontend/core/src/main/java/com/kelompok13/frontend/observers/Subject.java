package com.kelompok13.frontend.observers;

public interface Subject {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver(Event event);
}
