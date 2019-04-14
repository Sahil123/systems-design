package main.parking.services.impl;

import main.parking.services.IParkingCenter;
import main.parking.entity.Slot;
import main.parking.entity.Vehicle;
import main.parking.enums.Size;
import main.parking.enums.VehicleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 2019-03-17 Singleton instance can be created to avoid multiple instance creations
public class ParkingCenterSizeWiseImpl implements IParkingCenter {

    private List<Slot> smallSlots;
    private List<Slot> midSlots;
    private List<Slot> largeSlots;
    private Map<Integer, Slot> vehicleSlotMap = new HashMap();

    public ParkingCenterSizeWiseImpl(int numSmallSlots, int numMedSlots, int numLargeSlots) {
        smallSlots = initialiseSlots(numSmallSlots, Size.SMALL);
        midSlots = initialiseSlots(numMedSlots, Size.MEDIUM);
        largeSlots = initialiseSlots(numLargeSlots, Size.LARGE);
    }

    public boolean park(Vehicle vehicle) {
        Slot allottedSlot = getSlotForVehicle(vehicle.getVehicleType());
        if (allottedSlot == null)
            return false;
        vehicleSlotMap.put(vehicle.getVehicleId(), allottedSlot);
        return true;
    }

    public boolean unPark(Vehicle vehicle) {
        Slot allottedSlot = vehicleSlotMap.get(vehicle.getVehicleId());
        if (allottedSlot == null)
            return false;
        putSlotBack(allottedSlot);
        vehicleSlotMap.remove(vehicle.getVehicleId());
        return true;
    }

    private List<Slot> initialiseSlots(int numSlots, Size slotSize) {
        List<Slot> slots = new ArrayList<>(numSlots);
        for (int i = 0; i < numSlots; i++)
            slots.add(new Slot(i + 1, slotSize));
        return slots;
    }

    // TODO: 2019-03-17 For valet parking slotId can be returned
    public int valetPark(Vehicle vehicle) {
        return -1;
    }

    // TODO: 2019-03-17 For real world multithreading support this
    //  function needs to be called by synchronised block
    private Slot getSlot(List<Slot> slotList) {
        if (slotList.isEmpty())
            return null;
        Slot slot = slotList.get(0);
        slotList.remove(0);
        return slot;
    }

    private void putSlotBack(Slot slot) {
        Size slotSize = slot.getSlotSize();
        if (slotSize == Size.SMALL)
            smallSlots.add(slot);
        else if (slotSize == Size.MEDIUM)
            midSlots.add(slot);
        else
            largeSlots.add(slot);
    }

    private Slot getSlotForVehicle(VehicleType vehicle) {
        Slot availableSlot = null;
        if (vehicle == VehicleType.MOTORCYCLE) {
            availableSlot = getSlot(smallSlots);
            if (availableSlot == null)
                availableSlot = getSlot(midSlots);
            if (availableSlot == null)
                availableSlot = getSlot(largeSlots);
        } else if (vehicle == VehicleType.CAR) {
            availableSlot = getSlot(midSlots);
            if (availableSlot == null)
                availableSlot = getSlot(largeSlots);
        } else if (vehicle == VehicleType.BUS)
            availableSlot = getSlot(largeSlots);
        return availableSlot;
    }

}