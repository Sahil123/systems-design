package main.parking.entity;

import main.parking.enums.Size;

public class Slot {

    private int slotId;
    private boolean isOccupied = false;
    private Size slotSize;

    public Slot(int slotId, Size slotSize) {
        this.slotId = slotId;
        this.slotSize = slotSize;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Size getSlotSize() {
        return slotSize;
    }

    public void setSlotSize(Size slotSize) {
        this.slotSize = slotSize;
    }
}