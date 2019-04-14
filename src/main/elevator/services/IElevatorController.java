package main.elevator.services;

import main.elevator.entity.buttons.Button;

public interface IElevatorController {

    void processRequest(Button button);
}
