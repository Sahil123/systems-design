package main.elevator.entity.buttons;

import main.elevator.services.IElevatorController;

public abstract class Button {

    public int id;
    private boolean buttonPress = false;

    private IElevatorController controller;

    public boolean isButtonPress() {
        return buttonPress;
    }

    public void setButtonPress(boolean buttonPress) {
        this.buttonPress = buttonPress;
    }

    public void pressButton() {
        if (!isButtonPress()) {
            controller.processRequest(this);
            setButtonPress(true);
        }
    }


}
