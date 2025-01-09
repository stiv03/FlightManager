import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlightManager {
    public List<Flight> flights;
    public List<AirCraft> aircrafts;

    public FlightManager() {
        this.aircrafts = new ArrayList<>();
        this.flights = new ArrayList<>();
    }


    public void addFlight(Flight flight) {
        for (Flight existingFlight : flights) {
            if (existingFlight.getFlightId().equals(flight.getFlightId())){
                System.out.println("Полет с ID " + flight.getFlightId() + " вече съществува.\n");
                return;
            }
        }
        flights.add(flight);
    }

   public void addAircraft(AirCraft aircraft) {
        for ( AirCraft existingAircraft : aircrafts) {
            if (existingAircraft.getAircraftId().equals(aircraft.getAircraftId())) {
                System.out.println("Самолет с ID " + aircraft.getAircraftId() + " вече съществува.\n");
                return;
            }
        }
        aircrafts.add(aircraft);
    }

    public List<Flight> findFlightsByDestination(String destination) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getDestination().equals(destination)) {
                result.add(flight);
            }
        }
        return result;
    }

   public void displayAllFlights(){
        if(flights.isEmpty()){
            System.out.println("Няма добавени полети\n");
        }
        for (Flight flight : flights) {
            System.out.println(flight);
        }
    }

    void displayAllAircrafts() {
        if (aircrafts.isEmpty()) {
            System.out.println("Няма добавени самолети\n");
        } else {
            for (AirCraft aircraft : aircrafts) {
                System.out.println(aircraft);
            }
        }
    }

    public List<AirCraft> getAircrafts() {
        return aircrafts;
    }

    public void saveToFile() {
        if (aircrafts.isEmpty() && flights.isEmpty()) {
            System.err.println("Няма данни за записване.");
            return;
        }

        try (BufferedWriter aircraftWriter = new BufferedWriter(new FileWriter("aircrafts.txt"));
             BufferedWriter flightWriter = new BufferedWriter(new FileWriter("flights.txt"))) {

            for (AirCraft aircraft : aircrafts) {
                aircraftWriter.write(aircraft.getAircraftId() + " " +
                        aircraft.getAircraftClass().getManufacturer() + " " +
                        aircraft.getAircraftClass().getModel() + " " +
                        aircraft.getAircraftClass().getSeats() + " " +
                        aircraft.getAircraftClass().getMinRunwayLength() + " " +
                        aircraft.getAircraftClass().getFuelConsumptionPerKMPerSeat() + " " +
                        aircraft.getAircraftClass().getFuelTankCapacity() + " " +
                        aircraft.getAircraftClass().getAverageSpeed() + " " +
                        aircraft.getCrewCost() + "\n");
            }

            for (Flight flight : flights) {
                flightWriter.write(flight.getFlightId() + " " +
                        flight.getDeparture() + " " +
                        flight.getDestination() + " " +
                        flight.getDistance() + " " +
                        flight.getAircraft().getAircraftId() + " " +
                        flight.getPassengers() + "\n");
            }

        } catch (IOException e) {
            System.err.println("Възникна грешка по време на запис на файлове: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        aircrafts.clear();
        flights.clear();

        try (BufferedReader aircraftReader = new BufferedReader(new FileReader("aircrafts.txt"))) {
            String line;
            while ((line = aircraftReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" ");
                if (parts.length == 9) {
                    String aircraftId = parts[0];
                    String manufacturer = parts[1];
                    String model = parts[2];
                    int seats = Integer.parseInt(parts[3]);
                    double minRunwayLength = Double.parseDouble(parts[4]);
                    double fuelConsumption = Double.parseDouble(parts[5]);
                    double fuelTankCapacity = Double.parseDouble(parts[6]);
                    double averageSpeed = Double.parseDouble(parts[7]);
                    double crewCost = Double.parseDouble(parts[8]);

                    boolean exists = aircrafts.stream().anyMatch(a -> a.getAircraftId().equals(aircraftId));

                    if (!exists) {
                        AircraftClass aircraftClass = new AircraftClass(manufacturer, model, seats, minRunwayLength, fuelConsumption, fuelTankCapacity, averageSpeed);
                        AirCraft newAircraft = new AirCraft(aircraftId, aircraftClass, crewCost);
                        aircrafts.add(newAircraft);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Грешка при зареждане на самолети: " + e.getMessage());
        }

        try (BufferedReader flightReader = new BufferedReader(new FileReader("flights.txt"))) {
            String line;
            while ((line = flightReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(" ");
                if (parts.length == 6) {
                    String flightId = parts[0];
                    String departure = parts[1];
                    String destination = parts[2];
                    double distance = Double.parseDouble(parts[3]);
                    String aircraftId = parts[4];
                    int passengers = Integer.parseInt(parts[5]);


                    boolean exists = flights.stream().anyMatch(f -> f.getFlightId().equals(flightId));

                    if (!exists) {

                        AirCraft matchingAircraft = aircrafts.stream()
                                .filter(a -> a.getAircraftId().equals(aircraftId))
                                .findFirst()
                                .orElse(null);

                        if (matchingAircraft != null) {
                            Flight newFlight = new Flight(flightId, departure, destination, distance, passengers, matchingAircraft);
                            flights.add(newFlight);
                        }
                    }
                } else {
                    System.err.println("Невалиден ред във файла: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Грешка при зареждане на полети: " + e.getMessage());
        }
    }
}


