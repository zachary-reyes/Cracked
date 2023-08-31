package ParkingLot;

public class Car extends Vehicle {
    public Car() {
        spotsNeeded = 1;
        size = VehicleSize.Compact;
    }

    /* Checks if the spot is Compact or Large. Doesn't check num of spots */
    public boolean canFitInSpot(ParkingSpot spot) {return spot.getSize() == VehicleSize.Large || spot.getSize() == VehicleSize.Compact;}
}
