package main.elevator.services;

import main.elevator.entity.Elevator;

public interface IElevatorService {

    int getGoalFloor(Elevator elevator);

    boolean stop(Elevator elevator);

    boolean restart(Elevator elevator);

    void openDoors(Elevator elevator);

    void closeDoors(Elevator elevator);
}

