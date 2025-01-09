public class Flight {

    private String flightId;
    private String departure;
    private String destination;
    private double distance;
    private int passengers;
    private AirCraft aircraft;
    double flightDuration;

    public Flight(String flightId, String departure, String destination,
                  double distance, int passengers, AirCraft aircraft) {

        setFlightId(flightId);
        setFlightId(departure);
        setDestination(destination);
        setDistance(distance);
        setPassengers(passengers);
        setAircraft(aircraft);
        calculateFlightDuration();
    }

    public double calculateFlightDuration() {
        flightDuration = distance / aircraft.getAircraftClass().getAverageSpeed();
        return flightDuration;
    }

    public double calculateFlightCost() {
        return aircraft.calculateFlightCost(distance, aircraft.getAircraftClass().getSeats());
    }

    public void checkPassengersCompatibility()  {
        if (passengers > aircraft.getAircraftClass().getSeats()) {
            throw new IllegalArgumentException("Броя на пасажерите e прекалено голямо за този самолет, самолета не е съвместим");
        }
    }

    public void checkAircraftCompatibility(){
        if (distance > aircraft.getAircraftClass().calculateMaxFlightDistance()) {
            throw new IllegalArgumentException("Разстоянието e прекалено голямо за този самолет, самолета не е съвместим");
        }

    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        if (flightId.isEmpty()) {
            throw new IllegalArgumentException("ID на полета не може да бъде празен.");
        }
        this.flightId = flightId;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        if (departure.isEmpty()) {
            throw new IllegalArgumentException("Мястото на заминаване не може да бъде празно.");
        }
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        if (destination.isEmpty()) {
            throw new IllegalArgumentException("Дестинацията не може да бъде празна.");
        }
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Разстоянието трябва да бъде положително.");
        }
        this.distance = distance;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        if (passengers <= 0) {
            throw new IllegalArgumentException("Броя на пасажерите трябва да бъде положителен.");
        }
        this.passengers = passengers;
    }

    public AirCraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(AirCraft aircraft) {
        this.aircraft = aircraft;
    }

    public double getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(double flightDuration) {
        this.flightDuration = flightDuration;
    }

    @Override
    public String toString() {
        return "Информация за полета:\n" +
                "ID на полета: " + flightId + "\n" +
                "От: " + departure + "\n" +
                "До: " + destination + "\n" +
                "Разстояние: " + distance + " км\n" +
                "Продължителност на полета: " + flightDuration + " часа\n" +
                "Извършва се с самолет: " + getAircraft().getAircraftId() + "\n" +
                "Брой пътници: " + getPassengers() + "\n" +
                "Цена на полета: " + String.format("%.0f", calculateFlightCost()) + " $\n";
    }
}
