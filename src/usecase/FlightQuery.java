package usecase;


public class FlightQuery {
    /*
     * En cada caso solo se ordena por las claves especificadas.
     *
     * Al buscar por (date1, date1) se deberia devolver Vuelo2 pero al ser la misma fecha se compara
     * por codigo de vuelo y se excluye.
     *
     * Vuelo( date1, code=550) > Vuelo2 (date1, code=0)
     */


    public void addFlight(Flight flight) {
        throw new RuntimeException("Not yet implemented.");
    }


    public Iterable<Flight> searchByDates(int start_year, int start_month, int start_day, int end_year, int end_month, int end_day) throws RuntimeException {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> searchByDestinations(String start_destination, String end_destination) throws RuntimeException {
        throw new RuntimeException("Not yet implemented.");
    }


    public Iterable<Flight> searchByCompanyAndFLightCode(String start_company, int start_flightCode, String end_company, int end_flightCode) {
        throw new RuntimeException("Not yet implemented.");
    }
}
