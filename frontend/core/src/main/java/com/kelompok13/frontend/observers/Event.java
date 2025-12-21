package com.kelompok13.frontend.observers;

public class Event {
    public final EventType type;
    public final Object data;

    public Event(EventType type, Object data) {
        this.type = type;
        this.data = data;
    }


    @SuppressWarnings("unchecked") //ignore unchecked cast warning
    public <T> T getDataAs(Class<T> tClass) {
        if (data == null) return null;
        if (!tClass.isInstance(data)){
            throw new ClassCastException("Event.data is " + data.getClass().getName()
                + " not " + tClass.getName());
        }
        return (T) data;
    }


}
