package usecase;


public class FlightManager {


    public Flight addFlight(String company, int flightCode, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Flight getFlight(String company, int flightCode, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }


    public void updateFlight(String company, int flightCode, int year, int month, int day, Flight updatedFlightInfo) {
        throw new RuntimeException("Not yet implemented.");


    }

    public void addPassenger(String dni, String name, String surname, Flight flight) {
        throw new RuntimeException("Not yet implemented.");
    }


    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> flightsByDate(int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> getFlightsByPassenger(Passenger passenger) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> getFlightsByDestination(String destination, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");

    }

}