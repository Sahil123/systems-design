package main.elevator.entity.buttons;

import main.elevator.enums.DoorStatus;

/*
Specifies the each floor button present in the lift
*/
public class DoorButton extends Button {
    private DoorStatus status;

    public DoorButton(int buttonId, DoorStatus status) {
        this.id = buttonId;
        this.status = status;
    }
}