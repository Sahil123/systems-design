package main.elevator.entity.buttons;

/*
Specifies the each floor button present in the lift
*/
public class ElevatorButton extends Button {
    private int floorId;

    public ElevatorButton(int buttonId, int floorId) {
        this.id = buttonId;
        this.floorId = floorId;
    }
}
