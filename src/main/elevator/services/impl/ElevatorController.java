package main.elevator.services.impl;

import main.elevator.entity.Elevator;
import main.elevator.entity.buttons.*;
import main.elevator.enums.Direction;
import main.elevator.enums.Status;
import main.elevator.services.IElevatorService;

import java.util.ArrayList;
import java.util.List;


/*

 */
public class ElevatorController {
    private static ElevatorController elevatorController;
    private static int numFloor, numElevator;
    private static boolean isStopped = false;
    static List<FloorButton> upButtons;
    static List<FloorButton> downButtons;
    private static List<Elevator> elevators;
    private static IElevatorService elevatorService;


    public ElevatorController getInstance(int numFloor, int numElevator) {
        if (elevatorController == null)
            synchronized (ElevatorController.class) {
                if (elevatorController == null)
                    elevatorController = new ElevatorController(numFloor, numElevator);
            }
        return elevatorController;
    }

    /*
     * The upButtons, downButtons refer to direction button on each floor.
     * For Example:
     *               For floor 3 the upButton has id 3 and direction UP
     *               the downButton has id 3 and direction DOWN
     */
    private ElevatorController(int nFloor, int nElevator) {
        numFloor = nFloor;
        numElevator = nElevator;
        upButtons = new ArrayList<>(numFloor);
        downButtons = new ArrayList<>(numFloor);
        for (int i = 0; i < numFloor; i++) {
            upButtons.add(new FloorButton(i, Direction.UP));
            downButtons.add(new FloorButton(i, Direction.DOWN));
        }
        for (int i = 1; i <= numElevator; i++) {
            elevators.add(new Elevator(i, numFloor));
            // TODO: 2019-03-27 thread implementation
        }
        elevatorService = new ElevatorService();
    }

    public void processRequest(Button button) {
        if (button instanceof ShutDownButton)
            shutDownElevator(button);
        else if (button instanceof ResetButton)
            restartElevator(button);
        else if (button instanceof ElevatorButton) {
            int elevatorId = button.id / numFloor;
            Elevator elevator = findElevator(elevatorId);
            int request = button.id % numFloor;
            int currFloor = elevator.getCurrFloor();
            if (currFloor == request)
                button.setButtonPress(false);
            else if (currFloor < request) {
                elevator.getUpFloors()[request] = true;
                elevator.setCountUp(elevator.getCountUp() + 1);
            } else {
                elevator.getDownFloors()[request] = true;
                elevator.setCountDown(elevator.getCountDown() + 1);
            }
        } else if (button instanceof DoorButton) {
            int elevatorId = button.id / numFloor;
            int doorButtonId = button.id % numFloor;
            Elevator elevator = findElevator(elevatorId);
            if (doorButtonId == 0)
                elevatorService.openDoors(elevator);
            else
                elevatorService.closeDoors(elevator);
        } else if (button instanceof FloorButton) {
            Elevator elevator = allotElevator(button);
            int request = button.id % numFloor;
            int currFloor = elevator.getCurrFloor();
            Direction bDir = ((FloorButton) button).getDirection();
            Direction eDir = elevator.getElevatorDirection();
            if (currFloor == request && bDir == eDir)
                button.setButtonPress(false);
            else if (bDir == Direction.UP && !elevator.getUpFloors()[request]) {
                elevator.getUpFloors()[request] = true;
                elevator.setCountUp(elevator.getCountUp() + 1);
            } else if (bDir == Direction.DOWN && !elevator.getDownFloors()[request]) {
                elevator.getDownFloors()[request] = true;
                elevator.setCountDown(elevator.getCountDown() + 1);
            }

        } else
            return;
    }


    /*
    * The algorithm for allotting the closest elevator on basis of
    * distance between the current floor and request floor
    */
    private Elevator allotElevator(Button button) {
        Direction bDirection = ((FloorButton) button).getDirection();
        int minDistance = Integer.MAX_VALUE;
        int elevDistance = Integer.MAX_VALUE;
        Elevator closestElevator = null;
        for (Elevator e : elevators) {
            if (e.getElevatorStatus() == Status.WORKING) {
                int currFloor = e.getCurrFloor();
                Direction eDirection = e.getElevatorDirection();
                if (eDirection == Direction.NONE)
                    elevDistance = Math.abs(currFloor - button.id);
                else {
                    if ((eDirection == Direction.UP && bDirection == Direction.UP && button.id > currFloor) ||
                            (eDirection == Direction.DOWN && bDirection == Direction.DOWN && button.id < currFloor))
                        elevDistance = Math.abs(currFloor - button.id);
                    else {
                        int goalFloor = elevatorService.getGoalFloor(e);
                        elevDistance = Math.abs(currFloor - goalFloor) +
                                Math.abs(goalFloor - button.id);
                    }
                }
            }
            if (elevDistance < minDistance)
                closestElevator = e;
        }
        return closestElevator;
    }

    private void shutDownElevator(Button button) {
        Elevator elevator = findElevator(button.id);
        if (elevator != null)
            elevatorService.stop(elevator);
    }

    private void restartElevator(Button button) {
        Elevator elevator = findElevator(button.id);
        if (elevator != null)
            elevatorService.restart(elevator);
    }

    private Elevator findElevator(int elevatorId) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == elevatorId)
                return elevator;
        }
        return null;
    }
}
