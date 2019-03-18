package test.parking;

import main.parking.IParkingCenter;
import main.parking.entity.Vehicle;
import main.parking.enums.VehicleType;
import main.parking.impl.ParkingCenterSizeWiseImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ParkingCenterSizeWiseTest {

    private IParkingCenter parkingCenter;
    private Vehicle motorcycle = new Vehicle(1, VehicleType.MOTORCYCLE);
    private Vehicle car = new Vehicle(2, VehicleType.CAR);
    private Vehicle bus = new Vehicle(3, VehicleType.BUS);

    @Before
    public void initialize() {
        parkingCenter = new ParkingCenterSizeWiseImpl(1, 3, 1);
    }

    @Test
    public void testParkWithSlotsAvailable() {
        assertTrue(parkingCenter.park(motorcycle));
        assertTrue(parkingCenter.park(car));
        assertTrue(parkingCenter.park(bus));
        parkingCenter.unPark(motorcycle);
        parkingCenter.unPark(car);
        parkingCenter.unPark(bus);
    }

    @Test
    public void testUnParkWithSlotsAvailable() {
        parkingCenter.park(motorcycle);
        parkingCenter.park(car);
        parkingCenter.park(bus);
        assertTrue(parkingCenter.unPark(motorcycle));
        assertTrue(parkingCenter.unPark(car));
        assertTrue(parkingCenter.unPark(bus));
    }

    @Test
    public void testParkAndUnParkWithSlotsUnavailable() {
        Vehicle bus2 = new Vehicle(4, VehicleType.BUS);
        parkingCenter.park(bus);
        assertFalse(parkingCenter.park(bus2));
        assertFalse(parkingCenter.unPark(bus2));
        parkingCenter.unPark(bus);
    }

    @Test
    public void testMotorcycleGetsMediumSlot() {
        Vehicle motorcycle2 = new Vehicle(4, VehicleType.MOTORCYCLE);
        parkingCenter.park(motorcycle);
        assertTrue(parkingCenter.park(motorcycle2));
        assertTrue(parkingCenter.unPark(motorcycle2));
        parkingCenter.unPark(motorcycle);
    }

}
