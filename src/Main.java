import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FlightManager manager = new FlightManager();

        manager.loadFromFile();

        Menu menu = new Menu(manager);
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            menu.displayMenu();

            System.out.print("Въведете вашия избор: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Невалиден избор. Моля въведете число.");
                scanner.next();
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    menu.handleAddAircraft();
                    break;
                case 2:
                    menu.handleAddFlight();
                    break;
                case 3:
                    menu.handleViewAllAircrafts();
                    break;
                case 4:
                    menu.handleViewAllFlights();
                    break;
                case 5:
                    menu.handleFindFlightsByDestination();
                    break;
                case 6:
                    System.out.println("Довиждане!");
                    break;
                default:
                    System.out.println("Невалиден избор. Моля опитайте отново.");
            }
        } while (choice != 6);

        scanner.close();
    }
}