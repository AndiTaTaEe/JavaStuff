public class Main {
    public static void main(String[] args) {
        ParkingLot parkinglot1 = new ParkingLot(3);
        Car car1 = new Car("CAR-111");
        Motorcycle moto1 = new Motorcycle("MOTO-222");
        Truck truck1 = new Truck("TRK-333");
        Vehicle van1 = new Vehicle("VAN-444");

        System.out.println(parkinglot1.park(car1));
        System.out.println(parkinglot1.park(moto1));
        System.out.println(parkinglot1.park(truck1));
        System.out.println(parkinglot1.park(van1));

        //leave a car
        parkinglot1.leave(1);
        System.out.println(parkinglot1.park(van1));

    }
}