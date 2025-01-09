import java.util.*;
import java.io.*;

public class Menu {
    private final FlightManager manager;

    public Menu(FlightManager manager) {
        this.manager = manager;
    }

    public void displayMenu() {
        System.out.println("\n===== Система за управление на летище =====");
        System.out.println("1. Добавяне на самолет");
        System.out.println("2. Добавяне на полет");
        System.out.println("3. Преглед на всички самолети");
        System.out.println("4. Преглед на всички полети");
        System.out.println("5. Търсене на полети по дестинация");
        System.out.println("6. Изход");
    }

    public void handleAddAircraft() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Въведете ID на самолета: ");
        String aircraftId = scanner.nextLine();

        System.out.print("Въведете производител: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Въведете модел: ");
        String model = scanner.nextLine();

        System.out.print("Въведете брой места: ");
        int seats = scanner.nextInt();

        System.out.print("Въведете минимална дължина на пистата (м): ");
        double minRunwayLength = scanner.nextDouble();

        System.out.print("Въведете разход на гориво на км на седалка (литри): ");
        double fuelConsumption = scanner.nextDouble();

        System.out.print("Въведете капацитет на резервоара за гориво (литри): ");
        double fuelTankCapacity = scanner.nextDouble();

        System.out.print("Въведете средна скорост (км/ч): ");
        double averageSpeed = scanner.nextDouble();

        System.out.print("Въведете разход за екипаж ($): ");
        double crewCost = scanner.nextDouble();

        AircraftClass aircraftClass = new AircraftClass(manufacturer, model, seats, minRunwayLength, fuelConsumption, fuelTankCapacity, averageSpeed);
        AirCraft newAircraft = new AirCraft(aircraftId, aircraftClass, crewCost);
        manager.addAircraft(newAircraft);
        manager.saveToFile();

        System.out.println("Добавен успешно!");
    }

    public void handleAddFlight() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Въведете ID на полета: ");
        String flightId = scanner.nextLine();

        System.out.print("Въведете място на излитане: ");
        String departure = scanner.nextLine();

        System.out.print("Въведете дестинация: ");
        String destination = scanner.nextLine();

        System.out.print("Въведете разстояние (км): ");
        double distance = scanner.nextDouble();

        System.out.print("Въведете броя на пасажерите: ");
        int passengers = scanner.nextInt();

        List<AirCraft> compatibleAircrafts = new ArrayList<>();
        for (AirCraft aircraft : manager.getAircrafts()) {
            try {
                Flight tempFlight = new Flight(flightId, departure, destination, distance, passengers, aircraft);
                tempFlight.checkAircraftCompatibility();
                tempFlight.checkPassengersCompatibility();
                compatibleAircrafts.add(aircraft);
            } catch (IllegalArgumentException e) {
                // Ignore incompatible aircraft
            }
        }

        if (compatibleAircrafts.isEmpty()) {
            System.out.println("Няма налични съвместими самолети за този полет!");
            return;
        }

        System.out.println("Подходящи самолети за полета са:");
        for (int i = 0; i < compatibleAircrafts.size(); i++) {
            AirCraft aircraft = compatibleAircrafts.get(i);
            System.out.printf("%d. %s (%d места, макс разстояние %.0f км и цена %.0f $)%n",
                    i + 1, aircraft.getAircraftId(), aircraft.getAircraftClass().getSeats(),
                    aircraft.getAircraftClass().calculateMaxFlightDistance(),
                    aircraft.calculateFlightCost(distance, aircraft.getAircraftClass().getSeats()));
        }

        int choice;
        do {
            System.out.printf("Изберете самолет (1-%d): ", compatibleAircrafts.size());
            while (!scanner.hasNextInt()) {
                System.out.println("Невалиден избор. Моля въведете число.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > compatibleAircrafts.size());

        AirCraft selectedAircraft = compatibleAircrafts.get(choice - 1);

        Flight newFlight = new Flight(flightId, departure, destination, distance, passengers, selectedAircraft);
        newFlight.checkAircraftCompatibility();
        newFlight.checkPassengersCompatibility();
        manager.addFlight(newFlight);
        manager.saveToFile();

        System.out.println("Добавен успешно!");
    }

    public void handleViewAllAircrafts() {
        System.out.println("\n--- Всички самолети ---");
        manager.displayAllAircrafts();
    }

    public void handleViewAllFlights() {
        System.out.println("\n--- Всички полети ---");
        manager.displayAllFlights();
    }

    public void handleFindFlightsByDestination() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Въведете дестинация за търсене на полет: ");
        String destination = scanner.nextLine();

        List<Flight> flights = manager.findFlightsByDestination(destination);
        if (flights.isEmpty()) {
            System.out.println("Не са намерени полети за " + destination);
        } else {
            System.out.println("\n--- Полети за " + destination + " ---");
            for (Flight flight : flights) {
                System.out.println(flight.getFlightId() + " със самолет " + flight.getAircraft().getAircraftId());
            }
        }
    }
}