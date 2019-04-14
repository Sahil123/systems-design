package main.elevator.entity;

import main.elevator.entity.buttons.*;
import main.elevator.enums.Direction;
import main.elevator.enums.DoorStatus;
import main.elevator.enums.Status;

import java.util.List;

public class Elevator {
    private int id;
    private boolean isMoving = false;
    private boolean[] upFloors;
    private boolean[] downFloors;
    private List<ElevatorButton> elevatorButtons;
    private DoorButton openButton;
    private DoorButton closeButton;
    private int currFloor = 0;                                      // Assuming in rest state elevator is at ground floor
    private int countUp = 0;
    private int countDown = 0;
    private Direction elevatorDirection = Direction.NONE;
    private Status elevatorStatus = Status.WORKING;
    private ShutDownButton shutDownButton;
    private ResetButton resetButton;
    private int maxFloor, minFloor = 0;
    private DoorStatus doorStatus = DoorStatus.CLOSED;


    public Elevator(int elevatorId, int numFloor) {
        this.id = elevatorId;
        initialise(numFloor);
    }

    private void initialise(int numFloor) {
        upFloors = new boolean[numFloor];
        downFloors = new boolean[numFloor];
        /*
        For given elevator with id 3 and numFloor 20
        Floor       ElevatorButtonId
        0           3*20+0=60
        1           3*20+1=61
        ...
        19          3*20+19=79
        numFloor includes ground as one floor so maxFloor in building is 19

        For given elevator with id 4 and numFloor 20
        Floor       ElevatorButtonId
        0           4*20+0=80
        1           4*20+1=81
        ...
        So every elevatorButton in the system has unique id to identify
         */
        for (int i = 0; i < numFloor; i++) {
            int buttonId = id * numFloor + i;
            elevatorButtons.add(new ElevatorButton(buttonId, i));
        }
        openButton = new DoorButton(2 * id, DoorStatus.OPEN);
        closeButton = new DoorButton(2 * id + 1, DoorStatus.CLOSED);
        shutDownButton = new ShutDownButton(id);
        resetButton = new ResetButton(id);
        maxFloor = numFloor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean[] getUpFloors() {
        return upFloors;
    }

    public void setUpFloors(boolean[] upFloors) {
        this.upFloors = upFloors;
    }

    public boolean[] getDownFloors() {
        return downFloors;
    }

    public void setDownFloors(boolean[] downFloors) {
        this.downFloors = downFloors;
    }

    public List<ElevatorButton> getElevatorButtons() {
        return elevatorButtons;
    }

    public void setElevatorButtons(List<ElevatorButton> elevatorButtons) {
        this.elevatorButtons = elevatorButtons;
    }

    public int getCurrFloor() {
        return currFloor;
    }

    public void setCurrFloor(int currFloor) {
        this.currFloor = currFloor;
    }

    public int getCountUp() {
        return countUp;
    }

    public void setCountUp(int countUp) {
        this.countUp = countUp;
    }

    public int getCountDown() {
        return countDown;
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    public Direction getElevatorDirection() {
        return elevatorDirection;
    }

    public void setElevatorDirection(Direction elevatorDirection) {
        this.elevatorDirection = elevatorDirection;
    }

    public Status getElevatorStatus() {
        return elevatorStatus;
    }

    public void setElevatorStatus(Status elevatorStatus) {
        this.elevatorStatus = elevatorStatus;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(int minFloor) {
        this.minFloor = minFloor;
    }

    public DoorStatus getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(DoorStatus doorStatus) {
        this.doorStatus = doorStatus;
    }
}
