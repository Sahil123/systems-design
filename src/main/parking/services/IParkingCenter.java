package main.parking.services;

import main.parking.entity.Vehicle;

public interface IParkingCenter {
    boolean park(Vehicle vehicle);

    boolean unPark(Vehicle vehicle);

    int valetPark(Vehicle vehicle);
}
