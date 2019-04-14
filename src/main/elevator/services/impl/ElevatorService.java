package main.elevator.services.impl;

import main.elevator.entity.Elevator;
import main.elevator.entity.buttons.Button;
import main.elevator.enums.Direction;
import main.elevator.enums.DoorStatus;
import main.elevator.enums.Status;
import main.elevator.services.IElevatorService;

public class ElevatorService implements IElevatorService {

    public void moveNext(Elevator elevator) {
        Direction direction = elevator.getElevatorDirection();
        int elevatorPos = elevator.getCurrFloor();
        if (direction == Direction.NONE ||
                elevator.getElevatorStatus() == Status.STOPPED) {
            synchronized (elevator) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Some exception occured " + e.getMessage());
                }
            }
        }
        if (direction == Direction.NONE && elevator.getElevatorStatus() == Status.WORKING) {
            Direction newDir = (elevator.getCountUp() == 0 ?
                    (elevator.getCountDown() == 0 ? Direction.NONE : Direction.DOWN) :
                    Direction.UP);
            elevator.setElevatorDirection(newDir);
        }
        if (direction == Direction.DOWN) {
            boolean[] downFloors = elevator.getDownFloors();
            /*
            While loop signifies elevator moving down till it reaches
            next floor where it is expected to stop
            */
            while (elevatorPos >= 0 && !downFloors[elevatorPos])
                elevatorPos--;
            openDoors(elevator);
            if (elevatorPos >= 0) {
                downFloors[elevatorPos] = false;
                elevator.setCountDown(elevator.getCountDown() - 1);
                ElevatorController.downButtons.get(elevatorPos).setButtonPress(false);
            } else {
                int countUp = elevator.getCountUp();
                int countDown = elevator.getCountDown();
                elevator.setElevatorDirection(
                        (countUp == 0 && countDown == 0) ? Direction.NONE : Direction.UP);
                // Signifies lift moving back to the ground floor
                elevator.setCurrFloor(0);
            }
        } else if (direction == Direction.UP) {
            boolean[] upFloors = elevator.getUpFloors();
            int maxFloor = elevator.getMaxFloor();
            /*
            While loop signifies elevator moving up till it reaches
            next floor where it is expected to stop
            */
            while (elevatorPos < maxFloor && !upFloors[elevatorPos])
                elevatorPos++;
            openDoors(elevator);
            if (elevatorPos < maxFloor) {
                upFloors[elevatorPos] = false;
                elevator.setCountUp(elevator.getCountUp() - 1);
                ElevatorController.upButtons.get(elevatorPos).setButtonPress(false);
            } else {
                int countUp = elevator.getCountUp();
                int countDown = elevator.getCountDown();
                elevator.setElevatorDirection(
                        (countUp == 0 && countDown == 0) ? Direction.NONE : Direction.DOWN);
                // Signifies lift moving back to the top floor
                elevator.setCurrFloor(maxFloor - 1);
            }
        }
        if (elevator.getElevatorDirection() == Direction.NONE)
            elevator.setMoving(false);
    }

    public int getGoalFloor(Elevator elevator) {
        if (elevator.getElevatorDirection() == Direction.UP)
            return elevator.getMaxFloor();
        if (elevator.getElevatorDirection() == Direction.DOWN)
            return elevator.getMinFloor();
        return -1;
    }

    public void openDoors(Elevator elevator) {
        elevator.setDoorStatus(DoorStatus.OPEN);
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println("Exception occured " + e);
        }
        elevator.setDoorStatus(DoorStatus.CLOSED);
    }

    public void closeDoors(Elevator elevator) {
        elevator.setDoorStatus(DoorStatus.CLOSED);
    }

    public boolean restart(Elevator elevator) {
        if (stop(elevator)) {
            reset(elevator);
        }
        return true;
    }

    public boolean stop(Elevator elevator) {
        elevator.setElevatorStatus(Status.STOPPED);
        return true;
    }

    private void reset(Elevator elevator) {
        elevator.setMoving(false);
        elevator.setUpFloors(new boolean[elevator.getMaxFloor()]);
        elevator.setDownFloors(new boolean[elevator.getMaxFloor()]);
        elevator.setElevatorDirection(Direction.NONE);
        elevator.setElevatorStatus(Status.WORKING);
        elevator.setDoorStatus(DoorStatus.CLOSED);
        elevator.setCurrFloor(0);
        elevator.setCountUp(0);
        elevator.setCountDown(0);
    }
}
