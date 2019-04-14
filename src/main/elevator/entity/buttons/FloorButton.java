package main.elevator.entity.buttons;

import main.elevator.enums.Direction;
/*
Specifies the up and down buttons present on each floor
*/
public class FloorButton extends Button {
    private Direction direction;

    public FloorButton(int buttonId, Direction direction) {
        this.id = buttonId;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
