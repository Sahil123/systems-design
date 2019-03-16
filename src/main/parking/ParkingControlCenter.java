package main.parking;

import main.parking.entity.Slot;
import main.parking.enums.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingControlCenter {

    private List<Slot> smallSlots;
    private List<Slot> midSlots;
    private List<Slot> largeSlots;
    private Map<Integer, Slot> slotMap = new HashMap();

    public ParkingControlCenter(int numSmallSlots, int numMedSlots, int numLargeSlots) {
        smallSlots = initialiseSlots(numSmallSlots, Size.SMALL);
        midSlots = initialiseSlots(numMedSlots, Size.MEDIUM);
        largeSlots = initialiseSlots(numLargeSlots, Size.LARGE);
    }

    private List<Slot> initialiseSlots(int numSlots, Size slotSize) {
        List<Slot> slots = new ArrayList<>(numSlots);
        for (int i = 0; i < numSlots; i++)
            slots.add(new Slot(i + 1, slotSize));
        return slots;
    }

}