package org.example.plugin.event;

import com.hydraclient.hydra.api.event.Event;

import java.util.Random;

public class EventCustomInput extends Event {

    int number = new Random().nextInt();

    public EventCustomInput() {
        super(Type.PRE);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
