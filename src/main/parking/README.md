# Parking System

## Task

Design and implement a parking system. What data structures,
interfaces and algorithms will you need? Your parking system should
be able to handle vehicles such as motorcycle,car and bus.


## IParkingCenter

The IParkingCenter interface is entry point for Parking Center Application.
It provides `park`, `unPark` and `valetPark` functions respectively.

For different designs please implement this interface.

## Design 1

- The parking center has three type of slots `SMALL`, `MEDIUM` and `LARGE`
- The design supports three type of vehicles `MOTORCYCLE`, `CAR` and `BUS`
- `MOTORCYCLE` vehicle can occupy `SMALL`, `MEDIUM` or `LARGE` slots
- `CAR` vehicle can occupy `MEDIUM` or `LARGE` slots
- `BUS` vehicle can occupy `LARGE` slot


### ParkingCenterSizeWiseImpl

The ParkingCenterSizeWiseImpl manages the logic for allotment of slot for the given vehicle.
It supports parking or valet parking of the vehicle.
Further it also supports freeing the slot post vehicle removal.


### Slot Size and Vehicle Type
 
__Size__ - A slot can have one of the three sizes
```java
public enum Size {
    SMALL,
    MEDIUM,
    LARGE
}
```

__Vehicle Type__ - A vehicle can have one of following types
```java
public enum VehicleType {
    MOTORCYCLE,
    CAR,
    BUS
}
```

## Things to improve

- [ ] Add a feature of allotting closest available possible slot. 
- [ ] Make design real world application 
  - [ ] Create singleton instance of Parking Center implementation so only one instance is created
  - [ ] Parking Center should be able to handle concurrent requests
- [ ] Valet Parking needs to be supported  