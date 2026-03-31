public class ParkingLot {
    private Vehicle[] spots;
    public ParkingLot(int capacity){
        spots = new Vehicle[capacity];
    }

    // ticket is related to the parking lot, so thats why is nested
    public static class Ticket {
        int spotNumber;
        String licensePlate;
        public Ticket(int spotNumber, String licensePlate){
            this.spotNumber = spotNumber;
            this.licensePlate = licensePlate;
        }

        @Override
        public String toString() {
            return "Receipt for the purchased Ticket! The spot number is: " + spotNumber + " for license plate: " + licensePlate;
        }
    }

    //logic for parking
    public Ticket park(Vehicle vehicle){
        for(int i = 0; i < spots.length; i++){
            //checking if the spot that vehicle wants to park is empty
            if(spots[i] == null) {
                spots[i] = vehicle;
                return new Ticket(i, vehicle.getLicensePlate());
            }
        }
        // if the parking lot is full
        System.out.println("Sorry, the parking lot is full!");
        return null;
    }

    public void leave(int spotNumber){
        //checking if the car leaves from a valid spot
        if(spotNumber < 0 || spotNumber >= spots.length){
            System.out.println("Error: Invalid spot number");
            return;
        }

        //checking if the spot is already empty
        if(spots[spotNumber] == null){
            System.out.println("Spot is already empty!");
            return;
        }

        //if everything is ok, the car should leave
        String licensePlateLeft = spots[spotNumber].getLicensePlate();
        System.out.println("Vehicle " + licensePlateLeft + " has left spot " + spotNumber);
        spots[spotNumber] = null;
    }

}
