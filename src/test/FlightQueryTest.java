package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.Flight;
import usecase.FlightQuery;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlightQueryTest {
    private FlightQuery searchDB;
    private ArrayList<String> airport;
    private ArrayList<String> companies;

    @BeforeEach
    void setUp() {
        searchDB = new FlightQuery();

        airport = new ArrayList<>();
        companies = new ArrayList<>();
        airport.add("Madrid-Barajas Adolfo Suárez (MAD)");
        airport.add("Barcelona-El Prat (BCN)");
        airport.add("Alicante-Elche (ALC)");
        airport.add("Palma de Mallorca (PMI)");
        airport.add("BOGOTA /EL DORADO (BOG)");
        airport.add("PARIS /ORLY (ORY)");//5
        airport.add("PARIS /CHARLES DE GAULLE (CDG)");
        airport.add("PARIS /BEAUVAIS-TILLE (BVA)");
        airport.add("LONDRES /HEATHROW (LHR)");//8
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


    private Flight newFlight(String company, int flightCode, int year, int month, int day, String destination){
        Flight f = new Flight();
        f.setCompany(company);
        f.setFlightCode(flightCode);
        f.setDate(year,month,day);
        f.setDestination(destination);
        return f;
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void searchByDates() {
        //Nota: La salida (output) solo esta ordenada por fecha. El orden entre vuelos con la misma fecha
        //no esta definido y es arbitrario, por lo tanto depende de la implementacion de arbol que usemos.
        //No deberiais tener problemas para obtener la misma salida en vuelos con la misma fecha ya que
        //partimos de la mismas implementaciones de BST.

        Iterable<Flight> output;
        String expected;
        Flight f1 = newFlight(companies.get(1),770,2018,12,6, airport.get(5));
        Flight f2 = newFlight(companies.get(1),730,2018,12,7, airport.get(8));
        Flight f4 = newFlight(companies.get(1),730,2018,12,12, airport.get(8));
        Flight f3 = newFlight(companies.get(1),770,2018,12,31, airport.get(5));

        Flight f5 = newFlight(companies.get(2),340,2016,12,31, airport.get(6));
        Flight f6 = newFlight(companies.get(2),340,2018,6,30, airport.get(6));
        Flight f7 = newFlight(companies.get(2),350,2018,4,17, airport.get(7));
        Flight f8 = newFlight(companies.get(2),350,2018,12,7, airport.get(7));

        Flight f9 = newFlight(companies.get(3),401,2018,12,7, airport.get(4));
        Flight f10 = newFlight(companies.get(3),402,2018,12,7, airport.get(12));
        Flight f11 = newFlight(companies.get(3),403,2019,12,7, airport.get(13));
        Flight f12 = newFlight(companies.get(3),413,2011,1,7, airport.get(14));

        output = searchDB.searchByDates(1900, 1, 1, 2020, 12, 31);
        assertNotNull(output);
        assertFalse(output.iterator().hasNext());//empty
        expected = "[]";
        assertEquals(expected, iterableToString(output));

        RuntimeException e = assertThrows(RuntimeException.class, ()->searchDB.searchByDates(2018, 1, 1, 2017, 12, 31));
        assertEquals("Invalid range. (min>max)",e.getMessage());

        searchDB.addFlight(f1);
        searchDB.addFlight(f2);
        searchDB.addFlight(f3);
        searchDB.addFlight(f4);


        output = searchDB.searchByDates(1900, 1, 1, 2020, 12, 31);
        expected = "[6-12-2018\tAV770\tPARIS /ORLY (ORY), 7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));
        output = searchDB.searchByDates(2018, 12, 6, 2018, 12, 31);
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByDates(2018, 12, 7, 2018, 12, 31);
        expected = "[7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByDates(2019, 12, 7, 2019, 12, 31);
        assertNotNull(output);
        assertFalse(output.iterator().hasNext());//empty
        expected = "[]";
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByDates(2016, 1, 1, 2016, 12, 31);
        assertNotNull(output);
        assertFalse(output.iterator().hasNext());//empty
        expected = "[]";
        assertEquals(expected, iterableToString(output));

        searchDB.addFlight(f5);
        searchDB.addFlight(f6);
        searchDB.addFlight(f7);

        output = searchDB.searchByDates(2016, 1, 1, 2016, 12, 31);
        expected = "[31-12-2016\tAEA340\tPARIS /CHARLES DE GAULLE (CDG)]";
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByDates(2016, 1, 1, 2016, 12, 31);
        expected = "[31-12-2016\tAEA340\tPARIS /CHARLES DE GAULLE (CDG)]";
        assertEquals(expected, iterableToString(output));


        searchDB.addFlight(f8);
        searchDB.addFlight(f9);
        searchDB.addFlight(f10);
        searchDB.addFlight(f11);
        searchDB.addFlight(f12);

        output = searchDB.searchByDates(2018, 12, 7, 2018, 12, 7);
        expected = "[7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 7-12-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 7-12-2018\tDAL401\tBOGOTA /EL DORADO (BOG), 7-12-2018\tDAL402\tLISBOA (LIS)]";
        assertEquals(expected, iterableToString(output));

    }

    @Test
    void searchByDestinations() {
        //Nota: La salida solo esta ordenada por el String de destino. El orden para vuelos con el mismo
        //destino no esta definido y es arbitrario, por lo tanto depende de la implementacion de arbol que usemos.
        //No deberiais tener problemas para obtener la misma salida en vuelos con la misma fecha ya que
        //partimos de la mismas implementaciones de BST.
        Iterable<Flight> output;
        String expected;

        Flight f1 = newFlight(companies.get(1),770,2018,12,6, airport.get(5));
        Flight f2 = newFlight(companies.get(1),730,2018,12,7, airport.get(8));
        Flight f4 = newFlight(companies.get(1),730,2018,12,12, airport.get(8));
        Flight f3 = newFlight(companies.get(1),770,2018,12,31, airport.get(5));

        Flight f5 = newFlight(companies.get(2),340,2016,12,31, airport.get(6));
        Flight f6 = newFlight(companies.get(2),340,2018,6,30, airport.get(6));
        Flight f7 = newFlight(companies.get(2),350,2018,4,17, airport.get(7));
        Flight f8 = newFlight(companies.get(2),350,2018,12,7, airport.get(7));

        Flight f9 = newFlight(companies.get(3),401,2018,12,7, airport.get(8));
        Flight f10 = newFlight(companies.get(3),402,2018,12,7, airport.get(9));
        Flight f11 = newFlight(companies.get(3),403,2019,12,7, airport.get(10));
        Flight f12 = newFlight(companies.get(3),413,2011,1,7, airport.get(11));

        output = searchDB.searchByDestinations("", Character.MAX_VALUE + "");
        expected = "[]";
        assertNotNull(output);
        assertFalse(output.iterator().hasNext());
        assertEquals(expected, iterableToString(output));

        searchDB.addFlight(f1);
        searchDB.addFlight(f2);
        searchDB.addFlight(f3);
        searchDB.addFlight(f4);

        RuntimeException e = assertThrows(RuntimeException.class, ()->searchDB.searchByDestinations("Z", "A"));
        assertEquals("Invalid range. (min>max)",e.getMessage());

        output = searchDB.searchByDestinations("", Character.MAX_VALUE + "");
        expected = "[7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 6-12-2018\tAV770\tPARIS /ORLY (ORY), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByDestinations("A", "H");
        expected = "[]";
        assertNotNull(output);
        assertFalse(output.iterator().hasNext());
        assertEquals(expected, iterableToString(output));


        output = searchDB.searchByDestinations("L", "P"+Character.MAX_VALUE);
        expected = "[7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 6-12-2018\tAV770\tPARIS /ORLY (ORY), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));


        searchDB.addFlight(f5);
        searchDB.addFlight(f6);
        searchDB.addFlight(f7);

        output = searchDB.searchByDestinations("PARIS", "PARIS"+Character.MAX_VALUE);
        expected = "[17-4-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 31-12-2016\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 30-6-2018\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 6-12-2018\tAV770\tPARIS /ORLY (ORY), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));


        searchDB.addFlight(f8);
        searchDB.addFlight(f9);
        searchDB.addFlight(f10);
        searchDB.addFlight(f11);
        searchDB.addFlight(f12);

        output = searchDB.searchByDestinations("LONDRES", "LONDRES"+Character.MAX_VALUE);
        expected = "[7-12-2018\tDAL402\tLONDRES /GATWICK (LGW), 7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 7-12-2018\tDAL401\tLONDRES /HEATHROW (LHR), 7-1-2011\tDAL413\tLONDRES /LUTON (LTN), 7-12-2019\tDAL403\tLONDRES /STANSTED (STN)]";
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByDestinations("", Character.MAX_VALUE + "");
        expected = "[7-12-2018\tDAL402\tLONDRES /GATWICK (LGW), 7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 7-12-2018\tDAL401\tLONDRES /HEATHROW (LHR), 7-1-2011\tDAL413\tLONDRES /LUTON (LTN), 7-12-2019\tDAL403\tLONDRES /STANSTED (STN), 17-4-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 7-12-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 31-12-2016\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 30-6-2018\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 6-12-2018\tAV770\tPARIS /ORLY (ORY), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));
    }

    @Test
    void searchByCompanyAndFLightCode() {
        //Nota: La salida solo esta ordenada por el String de destino. El orden para vuelos con el mismo
        //destino no esta definido y es arbitrario, por lo tanto depende de la implementacion de arbol que usemos.
        //No deberiais tener problemas para obtener la misma salida en vuelos con la misma fecha ya que
        //partimos de la mismas implementaciones de BST.
        Iterable<Flight> output;
        String expected;

        Flight f1 = newFlight(companies.get(1),770,2018,12,6, airport.get(5));
        Flight f2 = newFlight(companies.get(1),730,2018,12,7, airport.get(8));
        Flight f4 = newFlight(companies.get(1),730,2018,12,12, airport.get(8));
        Flight f3 = newFlight(companies.get(1),770,2018,12,31, airport.get(5));

        Flight f5 = newFlight(companies.get(2),340,2016,12,31, airport.get(6));
        Flight f6 = newFlight(companies.get(2),340,2018,6,30, airport.get(6));
        Flight f7 = newFlight(companies.get(2),350,2018,4,17, airport.get(7));
        Flight f8 = newFlight(companies.get(2),350,2018,12,7, airport.get(7));

        Flight f9 = newFlight(companies.get(3),401,2018,12,7, airport.get(8));
        Flight f10 = newFlight(companies.get(3),402,2018,12,7, airport.get(9));
        Flight f11 = newFlight(companies.get(3),403,2019,12,7, airport.get(10));
        Flight f12 = newFlight(companies.get(3),413,2011,1,7, airport.get(11));

        output = searchDB.searchByCompanyAndFLightCode("",0,""+Character.MAX_VALUE, Integer.MAX_VALUE);
        expected = "[]";
        assertNotNull(output);
        assertFalse(output.iterator().hasNext());
        assertEquals(expected, iterableToString(output));

        searchDB.addFlight(f1);
        searchDB.addFlight(f2);
        searchDB.addFlight(f3);
        searchDB.addFlight(f4);

        output = searchDB.searchByCompanyAndFLightCode(companies.get(1),0,companies.get(1), Integer.MAX_VALUE);
        expected = "[7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 6-12-2018\tAV770\tPARIS /ORLY (ORY), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));

        RuntimeException e = assertThrows(RuntimeException.class, () -> searchDB.searchByCompanyAndFLightCode("B", 330, "A",770));
        assertEquals("Invalid range. (min>max)",e.getMessage());

        e = assertThrows(RuntimeException.class, () -> searchDB.searchByCompanyAndFLightCode("A", 100, "A",0));
        assertEquals("Invalid range. (min>max)",e.getMessage());

        output = searchDB.searchByCompanyAndFLightCode("H",0,"Z", Integer.MAX_VALUE);
        expected = "[]";
        assertEquals(expected, iterableToString(output));

        searchDB.addFlight(f5);
        searchDB.addFlight(f6);
        searchDB.addFlight(f7);
        searchDB.addFlight(f8);
        searchDB.addFlight(f9);
        searchDB.addFlight(f10);
        searchDB.addFlight(f11);
        searchDB.addFlight(f12);

        output = searchDB.searchByCompanyAndFLightCode("A",0,"A"+Character.MAX_VALUE, Integer.MAX_VALUE);
        expected = "[31-12-2016\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 30-6-2018\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 17-4-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 7-12-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 6-12-2018\tAV770\tPARIS /ORLY (ORY), 31-12-2018\tAV770\tPARIS /ORLY (ORY)]";
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByCompanyAndFLightCode("", 0,Character.MAX_VALUE + "", Integer.MAX_VALUE);
        expected = "[31-12-2016\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 30-6-2018\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 17-4-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 7-12-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 7-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 12-12-2018\tAV730\tLONDRES /HEATHROW (LHR), 6-12-2018\tAV770\tPARIS /ORLY (ORY), 31-12-2018\tAV770\tPARIS /ORLY (ORY), 7-12-2018\tDAL401\tLONDRES /HEATHROW (LHR), 7-12-2018\tDAL402\tLONDRES /GATWICK (LGW), 7-12-2019\tDAL403\tLONDRES /STANSTED (STN), 7-1-2011\tDAL413\tLONDRES /LUTON (LTN)]";
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByCompanyAndFLightCode(companies.get(2), 0, companies.get(2), Integer.MAX_VALUE);
        expected = "[31-12-2016\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 30-6-2018\tAEA340\tPARIS /CHARLES DE GAULLE (CDG), 17-4-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA), 7-12-2018\tAEA350\tPARIS /BEAUVAIS-TILLE (BVA)]";
        assertEquals(expected, iterableToString(output));
        output = searchDB.searchByCompanyAndFLightCode(companies.get(2), 300, companies.get(2), 350);
        assertEquals(expected, iterableToString(output));

        output = searchDB.searchByCompanyAndFLightCode(companies.get(3), 401, companies.get(3), 403);
        expected = "[7-12-2018	DAL401	LONDRES /HEATHROW (LHR), 7-12-2018	DAL402	LONDRES /GATWICK (LGW), 7-12-2019	DAL403	LONDRES /STANSTED (STN)]";
        assertEquals(expected, iterableToString(output));

    }

    private String iterableToString(Iterable<Flight> iterable){
        StringBuilder sb = new StringBuilder("[");
        for (Flight f: iterable){
            sb.append(f.toString()).append(", ");
        }
        if(sb.length()>2){
            sb.setLength(sb.length()-2);
        }
        sb.append(']');
        return sb.toString();
    }
}