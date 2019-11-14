package test;


import usecase.Flight;
import usecase.FlightManager;
import usecase.Passenger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightManagerTest {
    FlightManager manager;
    FlightManager emptyManager;

    ArrayList<String> airport;
    ArrayList<String> companies;

    private Flight newFlight(String company, int flightCode, int year, int month, int day){
        Flight flight = new Flight();
        flight.setCompany(company);
        flight.setFlightCode(flightCode);
        flight.setDate(year,month,day);
        return flight;
    }

    private Passenger newPassenger(String dni, String name, String surname){
        Passenger passenger = new Passenger();
        passenger.setDNI(dni);
        passenger.setName(name);
        passenger.setSurname(surname);
        return passenger;
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        emptyManager = new FlightManager();
        manager = new FlightManager();

        airport = new ArrayList<>();
        companies = new ArrayList<>();
        airport.add("Madrid-Barajas Adolfo Suárez (MAD)");
        airport.add("Barcelona-El Prat (BCN)");
        airport.add("Alicante-Elche (ALC)");
        airport.add("Palma de Mallorca (PMI)");
        airport.add("BOGOTA /EL DORADO (BOG)");
        airport.add("PARIS /ORLY (ORY)");
        airport.add("PARIS /CHARLES DE GAULLE (CDG)");
        airport.add("PARIS /BEAUVAIS-TILLE (BVA)");
        airport.add("LONDRES /HEATHROW (LHR)");
        airport.add("LONDRES /GATWICK (LGW)");
        airport.add("LONDRES /STANSTED (STN)");
        airport.add("LONDRES /LUTON (LTN)");
        airport.add("LISBOA (LIS)");
        airport.add("BEIRUT (BEY)");
        airport.add("MUNICH (MUC)");

        companies.add("IBE");
        companies.add("AV");
        companies.add("AEA");
        companies.add("DAL");
        companies.add("RYR");
        companies.add("VLG");
        companies.add("ASL");




    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void addFlight() {
        Flight flight = manager.addFlight(companies.get(0), 1200, 2018, 12, 20);
        assertNotNull(flight);
        assertEquals(companies.get(0), flight.getCompany());
        assertEquals(1200,flight.getFlightCode());
        assertEquals(2018,flight.getYear());
        assertEquals(12, flight.getMonth());
        assertEquals(20,flight.getDay());

        manager.addFlight(companies.get(0), 1201, 2018, 12, 20);
        manager.addFlight(companies.get(0), 1202, 2018, 12, 20);
        manager.addFlight(companies.get(0), 1200, 2019, 12, 20);
        manager.addFlight(companies.get(1), 1201, 2018, 12, 20);
        manager.addFlight(companies.get(1), 1202, 2018, 12, 20);
        manager.addFlight(companies.get(1), 1200, 2018, 12, 20);
        manager.addFlight(companies.get(2), 1201, 2018, 12, 20);
        manager.addFlight(companies.get(2), 1202, 2018, 12, 20);
        manager.addFlight(companies.get(2), 1202, 2020, 12, 20);

        Exception e = assertThrows(RuntimeException.class,
                () -> manager.addFlight(companies.get(0),1200,2018,12,20));
        assertEquals( "The flight already exists.",e.getMessage());


        e = assertThrows(RuntimeException.class,
                () -> manager.addFlight(companies.get(0),1202,2018,12,20));
        assertEquals("The flight already exists.",e.getMessage());

        e = assertThrows(RuntimeException.class,
                () -> manager.addFlight(companies.get(2),1202,2020,12,20));
        assertEquals( "The flight already exists.",e.getMessage());

    }


    @org.junit.jupiter.api.Test
    void getFlight() {
        manager.addFlight(companies.get(0), 1200, 2018, 12, 20);


        Flight flight1 = manager.getFlight(companies.get(0), 1200, 2018, 12, 20);
        //Modificar el objeto devuelto por getFlight no deberia afectar el contenido de FlightManager
        //De acuerdo al enunciado de la practica esta encapsulacion solo se pide para getFlight.
        flight1.setCapacity(80);
        flight1.setTime(8,15);
        flight1.setOrigin(airport.get(0));
        flight1.setDestination(airport.get(1));
        flight1.setProperty("LUGGAGE", "NO");
        flight1.setDelay(10);


        Flight flight2 = manager.getFlight(companies.get(0), 1200, 2018, 12, 20);

        assertNotSame(flight1,flight2);
        assertNotEquals(flight1.toString(),flight2.toString());
        assertEquals(flight1.toString(), "20-12-2018\tIBE1200\t8:15\tMadrid-Barajas Adolfo Suárez (MAD)\tBarcelona-El Prat (BCN)\tDELAYED (10min)");
        assertEquals(flight2.toString(), "20-12-2018\tIBE1200");

        flight1.setCompany(companies.get(1));

        Exception e = assertThrows(RuntimeException.class, () -> manager.getFlight(companies.get(1), 1200, 2018, 12, 20));
        assertEquals("Flight not found.",e.getMessage());

    }

    @org.junit.jupiter.api.Test
    void updateFlight_data() {
        manager.addFlight(companies.get(0), 1200, 2018, 12, 20);
        Flight flight1 = manager.getFlight(companies.get(0), 1200, 2018, 12, 20);

        flight1.setCapacity(80);
        flight1.setTime(8,15);
        flight1.setOrigin(airport.get(0));
        flight1.setDestination(airport.get(1));
        flight1.setProperty("LUGGAGE", "NO");
        flight1.setDelay(10);

        manager.updateFlight(companies.get(0), 1200, 2018, 12, 20, flight1);

        Flight flight2 = manager.getFlight(companies.get(0), 1200, 2018, 12, 20);

        assertEquals(flight1.toString(),flight2.toString());
        assertEquals(flight2.toString(), "20-12-2018\tIBE1200\t8:15\tMadrid-Barajas Adolfo Suárez (MAD)\tBarcelona-El Prat (BCN)\tDELAYED (10min)");
        assertEquals("NO", flight2.getProperty("LUGGAGE"));
        Exception e = assertThrows(RuntimeException.class, ()->manager.updateFlight(companies.get(1), 1300, 2000, 1, 1, flight1));
        assertEquals("The flight doesn't exists and can't be updated.",e.getMessage());
    }

    @org.junit.jupiter.api.Test
    void updateFlight_identifiers() {
        manager.addFlight(companies.get(0), 1200, 2018, 12, 20);
        manager.addFlight(companies.get(0), 1201, 2018, 12, 20);
        Flight flight1 = manager.getFlight(companies.get(0), 1200, 2018, 12, 20);

        flight1.setFlightCode(1201);
        flight1.setCapacity(80);
        flight1.setTime(8,15);
        flight1.setOrigin(airport.get(0));
        flight1.setDestination(airport.get(1));
        flight1.setProperty("LUGGAGE", "NO");
        flight1.setDelay(0);

        Exception e = assertThrows(RuntimeException.class, ()-> manager.updateFlight(companies.get(0), 1200, 2018, 12, 20, flight1));
        assertEquals("The new flight identifiers are already in use.",e.getMessage());

        flight1.setCompany(companies.get(5));
        flight1.setFlightCode(704);
        manager.updateFlight(companies.get(0), 1200, 2018, 12, 20, flight1);
        Flight flightUpdated = manager.getFlight(companies.get(5), 704, 2018, 12, 20);


        assertEquals(flight1.toString(),flightUpdated.toString());
        assertEquals("20-12-2018\tVLG704\t8:15\tMadrid-Barajas Adolfo Suárez (MAD)\tBarcelona-El Prat (BCN)", flightUpdated.toString());

        e = assertThrows(RuntimeException.class, ()->manager.getFlight(companies.get(0), 1200, 2018, 12, 20));
        assertEquals("Flight not found.",e.getMessage());
    }

    @org.junit.jupiter.api.Test
    void addPassenger() {
        manager.addFlight(companies.get(0), 1200, 2018, 12, 20);
        Flight flight = manager.getFlight(companies.get(0), 1200, 2018, 12, 20);
        flight.setCapacity(3);
        manager.updateFlight(companies.get(0), 1200, 2018, 12, 20, flight);

        manager.addPassenger("09409833G", "A", "A", flight);
        manager.addPassenger("09409833G", "A", "A", flight);
        manager.addPassenger("10202761F", "B", "X", flight);
        manager.addPassenger("10", "C", "Y", flight);


        Exception e = assertThrows(RuntimeException.class, () -> manager.addPassenger("09409833G", "C", "Y", flight));
        assertEquals("This flight doesn't have capacity for more passengers.", e.getMessage());

        flight.setDate(2018,1,1);
        e = assertThrows(RuntimeException.class, ()-> manager.addPassenger("09409833G", "A", "A", flight));
        assertEquals("The flight doesn't exits.",e.getMessage());
    }

    @org.junit.jupiter.api.Test
    void getPassengers() {
        manager.addFlight(companies.get(0), 1200, 2018, 12, 20);
        Flight flight =manager.getFlight(companies.get(0), 1200, 2018, 12, 20);
        flight.setCapacity(10);
        manager.updateFlight(companies.get(0), 1200, 2018, 12, 20, flight);

        manager.addPassenger("09409833G", "A", "A", flight);
        manager.addPassenger("09409833G", "A", "A", flight);
        manager.addPassenger("10202761F", "B", "X", flight);
        manager.addPassenger("10", "C", "Y", flight);

        Iterable<Passenger> passengers = manager.getPassengers(companies.get(0), 1200, 2018, 12, 20);
        List<Passenger> list = getSortedPassengers(passengers);
        String expected = "[09409833G A A, 10 C Y, 10202761F B X]";
        assertEquals(expected, list.toString());

        Exception e = assertThrows(RuntimeException.class, ()->manager.getPassengers(companies.get(1), 7344, 2018, 12, 20));
        assertEquals("The flight doesn't exists.",e.getMessage());

        manager.addFlight(companies.get(1), 1200, 2018, 12, 20);
        passengers = manager.getPassengers(companies.get(1), 1200, 2018, 12, 20);
        list = getSortedPassengers(passengers);
        assertEquals(0, list.size());
    }

    @org.junit.jupiter.api.Test
    void getPassengers_afterUpdate() {
        manager.addFlight(companies.get(0), 1200, 2018, 12, 20);
        Flight flight = manager.getFlight(companies.get(0), 1200, 2018, 12, 20);
        flight.setCapacity(10);
        manager.updateFlight(companies.get(0), 1200, 2018, 12, 20, flight);

        manager.addPassenger("09409833G", "A", "A", flight);
        manager.addPassenger("09409833G", "A", "A", flight);
        manager.addPassenger("10202761F", "B", "X", flight);
        manager.addPassenger("10", "C", "Y", flight);

        flight.setDate(2018,12,22);
        flight.setFlightCode(1408);
        manager.updateFlight(companies.get(0), 1200, 2018, 12, 20, flight);

        Exception e = assertThrows(RuntimeException.class, ()->manager.getPassengers(companies.get(0), 1200, 2018, 12, 20));
        assertEquals("The flight doesn't exists.", e.getMessage());

        Iterable<Passenger> passengers = manager.getPassengers(companies.get(0), 1408, 2018, 12, 22);
        List<Passenger> list = getSortedPassengers(passengers);
        String expected = "[09409833G A A, 10 C Y, 10202761F B X]";
        assertEquals(expected, list.toString());
    }



    @org.junit.jupiter.api.Test
    void flightsByDate() {
        manager.addFlight(companies.get(0), 7070,2019,1,31);
        manager.addFlight(companies.get(0), 7071,2019,2,10);
        manager.addFlight(companies.get(0), 7070,2019,2,11);
        manager.addFlight(companies.get(1), 8072,2019,2,10);
        manager.addFlight(companies.get(2), 9070,2019,1,11);
        manager.addFlight(companies.get(2), 9070,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,2,10);
        manager.addFlight(companies.get(3), 1002,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,1,13);

        Iterable<Flight> flightsByDate = manager.flightsByDate(2020, 1, 31);
        List<Flight> list = getSortedFlightsAsList(flightsByDate);
        assertEquals(0,list.size());

        flightsByDate = manager.flightsByDate(2019, 1, 31);
        list = getSortedFlightsAsList(flightsByDate);
        assertEquals(1,list.size());
        String expected = "[31-1-2019\tIBE7070]";
        assertEquals(expected, list.toString());

        flightsByDate = manager.flightsByDate(2019, 2, 10);
        list = getSortedFlightsAsList(flightsByDate);
        assertEquals(3,list.size());
        expected = "[10-2-2019\tAV8072, 10-2-2019\tDAL1001, 10-2-2019\tIBE7071]";
        assertEquals(expected, list.toString());

        flightsByDate = manager.flightsByDate(2000, 1, 1);
        list = getSortedFlightsAsList(flightsByDate);
        assertEquals(0,list.size());
        expected = "[]";
        assertEquals(expected, list.toString());
    }

    @org.junit.jupiter.api.Test
    void flightsByDate_AfterUpdate() {
        manager.addFlight(companies.get(0), 7070,2019,1,31);
        manager.addFlight(companies.get(0), 7071,2019,2,10);
        manager.addFlight(companies.get(0), 7070,2019,2,11);
        manager.addFlight(companies.get(1), 8072,2019,2,10);
        manager.addFlight(companies.get(2), 9070,2019,1,11);
        manager.addFlight(companies.get(2), 9070,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,2,10);
        manager.addFlight(companies.get(3), 1002,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,1,13);

        Iterable<Flight> flightsByDate = manager.flightsByDate(2020, 1, 31);
        List<Flight> list = getSortedFlightsAsList(flightsByDate);
        assertEquals(0,list.size());

        Flight flight = manager.getFlight(companies.get(0), 7070, 2019, 1, 31);
        flight.setDate(2020,1,31);
        flight.setOrigin(airport.get(0));
        flight.setDestination(airport.get(7));
        flight.setTime(8,15);
        flight.setCapacity(500);
        flight.setProperty("LUGGAGE", "FREE");
        manager.updateFlight(companies.get(0), 7070, 2019, 1, 31, flight);


        flightsByDate = manager.flightsByDate(2019, 1, 31);
        list = getSortedFlightsAsList(flightsByDate);
        assertEquals(0,list.size());
        String expected = "[]";
        assertEquals(expected, list.toString());

        flightsByDate = manager.flightsByDate(2020, 1, 31);
        list = getSortedFlightsAsList(flightsByDate);
        assertEquals(1,list.size());
        expected =  "[31-1-2020	IBE7070	8:15	Madrid-Barajas Adolfo Suárez (MAD)	PARIS /BEAUVAIS-TILLE (BVA)]";
        assertEquals(expected, list.toString());

    }



    @org.junit.jupiter.api.Test
    void getFlightsByPassenger() {
        manager.addFlight(companies.get(0), 7070, 2019, 1, 31);
        manager.addFlight(companies.get(0), 7071,2019,2,10);
        manager.addFlight(companies.get(0), 7070,2019,2,11);
        manager.addFlight(companies.get(1), 8072,2019,2,10);
        manager.addFlight(companies.get(2), 9070,2019,1,11);
        manager.addFlight(companies.get(2), 9070,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,2,10);
        manager.addFlight(companies.get(3), 1002,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,1,13);

        Flight flight1 = manager.getFlight(companies.get(0), 7070, 2019, 1, 31);
        flight1.setCapacity(10);
        manager.updateFlight(companies.get(0), 7070, 2019, 1, 31, flight1);

        Flight flight2 = manager.getFlight(companies.get(0), 7070,2019,2,11);
        flight2.setCapacity(10);
        manager.updateFlight(companies.get(0), 7070,2019,2,11, flight2);

        Flight flight3 = manager.getFlight(companies.get(2), 9070,2019,1,12);
        flight3.setCapacity(10);
        manager.updateFlight(companies.get(2), 9070,2019,1,12, flight3);


        manager.addPassenger("09409833G", "A", "A", flight1);
        manager.addPassenger("09409833G", "A", "A", flight2);
        manager.addPassenger("09409833G", "A", "A", flight3);
        manager.addPassenger("10202761F", "B", "X", flight2);
        manager.addPassenger("10202761F", "B", "X", flight3);
        manager.addPassenger("10", "C", "Y", flight3);

        Passenger p1 = newPassenger("09409833G", null, null);
        Passenger p2 = newPassenger("10202761F", null, null);
        Passenger p3 = newPassenger("10", null, null);

        Iterable<Flight> flightsByPassenger = manager.getFlightsByPassenger(p1);
        List<Flight> list = this.getSortedFlightsAsList(flightsByPassenger);
        assertEquals(3,list.size());
        String expected = "[11-2-2019\tIBE7070, 12-1-2019\tAEA9070, 31-1-2019\tIBE7070]";
        assertEquals(expected, list.toString());

        flightsByPassenger = manager.getFlightsByPassenger(p2);
        list = this.getSortedFlightsAsList(flightsByPassenger);
        assertEquals(2,list.size());
        expected = "[11-2-2019\tIBE7070, 12-1-2019\tAEA9070]";
        assertEquals(expected, list.toString());

        flightsByPassenger = manager.getFlightsByPassenger(p3);
        list = this.getSortedFlightsAsList(flightsByPassenger);
        assertEquals(1,list.size());
        expected = "[12-1-2019\tAEA9070]";
        assertEquals(expected, list.toString());

        Passenger pUnknown = newPassenger("0", null, null);
        flightsByPassenger = manager.getFlightsByPassenger(pUnknown);
        list = this.getSortedFlightsAsList(flightsByPassenger);
        assertEquals(0,list.size());
        expected = "[]";
        assertEquals(expected, list.toString());

    }

    @org.junit.jupiter.api.Test
    void getFlightsByPassenger_AfterUpdate() {
        manager.addFlight(companies.get(0), 7070, 2019, 1, 31);
        manager.addFlight(companies.get(0), 7071,2019,2,10);
        manager.addFlight(companies.get(0), 7070,2019,2,11);
        manager.addFlight(companies.get(1), 8072,2019,2,10);
        manager.addFlight(companies.get(2), 9070,2019,1,11);
        manager.addFlight(companies.get(2), 9070,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,2,10);
        manager.addFlight(companies.get(3), 1002,2019,1,12);
        manager.addFlight(companies.get(3), 1001,2019,1,13);

        Flight flight1 = manager.getFlight(companies.get(0), 7070, 2019, 1, 31);
        flight1.setCapacity(10);
        manager.updateFlight(companies.get(0), 7070, 2019, 1, 31, flight1);

        Flight flight2 = manager.getFlight(companies.get(0), 7070,2019,2,11);
        flight2.setCapacity(10);
        manager.updateFlight(companies.get(0), 7070,2019,2,11, flight2);

        Flight flight3 = manager.getFlight(companies.get(2), 9070,2019,1,12);
        flight3.setCapacity(10);
        manager.updateFlight(companies.get(2), 9070,2019,1,12, flight3);

        manager.addPassenger("10202761F", "B", "X", flight1);
        manager.addPassenger("10202761F", "B", "X", flight2);
        manager.addPassenger("10202761F", "B", "X",  flight3);

        Passenger p1 = newPassenger("10202761F", null, null);


        Iterable<Flight> flightsByPassenger = manager.getFlightsByPassenger(p1);
        List<Flight> list = this.getSortedFlightsAsList(flightsByPassenger);
        assertEquals(3,list.size());
        String expected = "[11-2-2019\tIBE7070, 12-1-2019\tAEA9070, 31-1-2019\tIBE7070]";
        assertEquals(expected, list.toString());

        flight1.setOrigin(airport.get(0));
        flight1.setDestination(airport.get(1));
        flight1.setTime(18, 7);
        flight2.setDelay(15);
        manager.updateFlight(companies.get(0), 7070, 2019, 1, 31,flight1);

        flightsByPassenger = manager.getFlightsByPassenger(p1);
        list = this.getSortedFlightsAsList(flightsByPassenger);
        assertEquals(3,list.size());
        expected = "[11-2-2019\tIBE7070, 12-1-2019\tAEA9070, 31-1-2019\tIBE7070\t18:7\tMadrid-Barajas Adolfo Suárez (MAD)\tBarcelona-El Prat (BCN)]";
        assertEquals(expected, list.toString());
        
    }

    @org.junit.jupiter.api.Test
    void getFlightsByDestination() {
        manager.addFlight(companies.get(0), 7070, 2019, 1, 31);
        manager.addFlight(companies.get(1), 7071,2019,1,31);
        manager.addFlight(companies.get(2), 7070,2019,2,11);
        manager.addFlight(companies.get(3), 8072,2019,2,11);
        manager.addFlight(companies.get(4), 9070,2019,2,12);
        manager.addFlight(companies.get(5), 9070,2019,2,13);

        Flight f1 = manager.getFlight(companies.get(0), 7070, 2019, 1, 31);
        Flight f2 = manager.getFlight(companies.get(1), 7071,2019,1,31);
        Flight f3 = manager.getFlight(companies.get(2), 7070,2019,2,11);
        Flight f4 = manager.getFlight(companies.get(3), 8072,2019,2,11);
        Flight f5 = manager.getFlight(companies.get(4), 9070,2019,2,12);
        Flight f6 = manager.getFlight(companies.get(5), 9070,2019,2,13);

        f1.setOrigin(airport.get(0));
        f2.setOrigin(airport.get(0));
        f3.setOrigin(airport.get(0));
        f4.setOrigin(airport.get(0));
        f5.setOrigin(airport.get(0));
        f6.setOrigin(airport.get(0));

        f1.setDestination(airport.get(2));
        f2.setDestination(airport.get(2));
        f3.setDestination(airport.get(3));
        f4.setDestination(airport.get(3));
        f5.setDestination(airport.get(3));
        f6.setDestination(airport.get(1));

        manager.updateFlight(companies.get(0), 7070, 2019, 1, 31,f1);
        manager.updateFlight(companies.get(1), 7071,2019,1,31,f2);
        manager.updateFlight(companies.get(2), 7070,2019,2,11,f3);
        manager.updateFlight(companies.get(3), 8072,2019,2,11,f4);
        manager.updateFlight(companies.get(4), 9070,2019,2,12,f5);
        manager.updateFlight(companies.get(5), 9070,2019,2,13,f6);

        Iterable<Flight> flights = manager.getFlightsByDestination(airport.get(2), 2019, 1, 31);
        List<Flight> list = this.getSortedFlightsAsList(flights);
        assertEquals(2,list.size());
        String expected = "[31-1-2019\tAV7071\tMadrid-Barajas Adolfo Suárez (MAD)\tAlicante-Elche (ALC), 31-1-2019\tIBE7070\tMadrid-Barajas Adolfo Suárez (MAD)\tAlicante-Elche (ALC)]";
        assertEquals(expected, list.toString());

        flights = manager.getFlightsByDestination(airport.get(3), 2019, 2, 11);
        list = this.getSortedFlightsAsList(flights);
        assertEquals(2,list.size());
        expected = "[11-2-2019\tAEA7070\tMadrid-Barajas Adolfo Suárez (MAD)\tPalma de Mallorca (PMI), 11-2-2019\tDAL8072\tMadrid-Barajas Adolfo Suárez (MAD)\tPalma de Mallorca (PMI)]";
        assertEquals(expected, list.toString());

        flights = manager.getFlightsByDestination(airport.get(1), 2019, 2, 13);
        list = this.getSortedFlightsAsList(flights);
        assertEquals(1,list.size());
        expected = "[13-2-2019\tVLG9070\tMadrid-Barajas Adolfo Suárez (MAD)\tBarcelona-El Prat (BCN)]";
        assertEquals(expected, list.toString());

        flights = manager.getFlightsByDestination(airport.get(1), 2020, 1, 1);
        list = this.getSortedFlightsAsList(flights);
        assertEquals(0,list.size());
        expected = "[]";
        assertEquals(expected, list.toString());

        Exception e = assertThrows(RuntimeException.class, ()->{
            manager.getFlightsByDestination(airport.get(4), 2019, 2, 13);
        });
        assertEquals("The destination doesn't exists.", e.getMessage());
    }

    @org.junit.jupiter.api.Test
    void getFlightsByDestination_AfterUpdate() {
        manager.addFlight(companies.get(0), 7070, 2019, 1, 31);
        manager.addFlight(companies.get(1), 7071,2019,1,31);
        manager.addFlight(companies.get(2), 7070,2019,2,11);
        manager.addFlight(companies.get(3), 8072,2019,2,11);
        manager.addFlight(companies.get(4), 9070,2019,2,12);
        manager.addFlight(companies.get(5), 9070,2019,2,13);

        Flight f1 = manager.getFlight(companies.get(0), 7070, 2019, 1, 31);
        Flight f2 = manager.getFlight(companies.get(1), 7071,2019,1,31);
        Flight f3 = manager.getFlight(companies.get(2), 7070,2019,2,11);
        Flight f4 = manager.getFlight(companies.get(3), 8072,2019,2,11);
        Flight f5 = manager.getFlight(companies.get(4), 9070,2019,2,12);
        Flight f6 = manager.getFlight(companies.get(5), 9070,2019,2,13);

        f1.setOrigin(airport.get(0));
        f2.setOrigin(airport.get(0));
        f3.setOrigin(airport.get(0));
        f4.setOrigin(airport.get(0));
        f5.setOrigin(airport.get(0));
        f6.setOrigin(airport.get(0));

        f1.setDestination(airport.get(2));
        f2.setDestination(airport.get(2));
        f3.setDestination(airport.get(3));
        f4.setDestination(airport.get(3));
        f5.setDestination(airport.get(3));
        f6.setDestination(airport.get(1));

        manager.updateFlight(companies.get(0), 7070, 2019, 1, 31,f1);
        manager.updateFlight(companies.get(1), 7071,2019,1,31,f2);
        manager.updateFlight(companies.get(2), 7070,2019,2,11,f3);
        manager.updateFlight(companies.get(3), 8072,2019,2,11,f4);
        manager.updateFlight(companies.get(4), 9070,2019,2,12,f5);
        manager.updateFlight(companies.get(5), 9070,2019,2,13,f6);

        Iterable<Flight> flights = manager.getFlightsByDestination(airport.get(1), 2019, 2, 13);
        List<Flight> list = this.getSortedFlightsAsList(flights);
        assertEquals(1,list.size());
        String expected = "[13-2-2019\tVLG9070\tMadrid-Barajas Adolfo Suárez (MAD)\tBarcelona-El Prat (BCN)]";
        assertEquals(expected, list.toString());

        f6.setDestination(airport.get(4));
        manager.updateFlight(companies.get(5), 9070,2019,2,13,f6);

        flights = manager.getFlightsByDestination(airport.get(1), 2019, 2, 13);
        list = this.getSortedFlightsAsList(flights);
        assertEquals(0,list.size());
        expected = "[]";
        assertEquals(expected, list.toString());

        flights = manager.getFlightsByDestination(airport.get(4), 2019, 2, 13);
        list = this.getSortedFlightsAsList(flights);
        assertEquals(1,list.size());
        expected = "[13-2-2019\tVLG9070\tMadrid-Barajas Adolfo Suárez (MAD)\tBOGOTA /EL DORADO (BOG)]";
        assertEquals(expected, list.toString());

    }

    private ArrayList<Passenger> getSortedPassengers(Iterable<Passenger> passengers) {
        ArrayList<Passenger> list = new ArrayList<>();
        for (Passenger passenger : passengers) {
            list.add(passenger);
        }
        list.sort(Comparator.comparing(p->p.getDNI()));
        return list;
    }
    private List<Flight> getSortedFlightsAsList(Iterable<Flight> flights) {
        ArrayList<Flight> list = new ArrayList<>();
        for (Flight flight : flights) {
            list.add(flight);
        }
        list.sort(Comparator.comparing(p->p.toString()));
        return list;
    }
}