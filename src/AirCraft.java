public class AirCraft {

    private String aircraftId;
    private AircraftClass aircraftClass;
    private double crewCost;


    public AirCraft(String aircraftId, AircraftClass aircraftClass, double crewCost) {
        this.aircraftId = aircraftId;
        this.aircraftClass = aircraftClass;
        this.crewCost = crewCost;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        if (aircraftId.isEmpty()) {
            throw new IllegalArgumentException("ID на самолета не може да е празно.");
        }
        this.aircraftId = aircraftId;
    }

    public AircraftClass getAircraftClass() {
        return aircraftClass;
    }

    public void setAircraftClass(AircraftClass aircraftClass) {
        this.aircraftClass = aircraftClass;
    }

    public double getCrewCost() {
        return crewCost;
    }

    public void setCrewCost(double crewCost) {
        if (crewCost < 0) {
            throw new IllegalArgumentException("Разходите трябва да са позитивно число.");
        }
        this.crewCost = crewCost;
    }

    public double calculateFlightCost(double distance, int passengers) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Дистанцията трбява да е позотивна");
        }
        if (passengers <= 0 || passengers > aircraftClass.getSeats()) {
            throw new IllegalArgumentException("Броя на пътниците трябва да позитивен и в рамките на броя на седалките.");
        }
        double fuelCost = distance * passengers * aircraftClass.getFuelConsumptionPerKMPerSeat();
        return fuelCost + crewCost;
    }

    @Override
    public String toString() {
        return "Информация за самолета:\n" +
                "ID на самолета: " + aircraftId + "\n" +
                "Клас: " + getAircraftClass().getType() + "\n" +
                "Производител: " + getAircraftClass().getManufacturer() + "\n" +
                "Модел: " + getAircraftClass().getModel() + "\n" +
                "Брой места: " + getAircraftClass().getSeats() + "\n" +
                "Минимална дължина на пистата (м): " + getAircraftClass().getMinRunwayLength() + "\n" +
                "Разход на гориво на км на седалка (литри): " + getAircraftClass().getFuelConsumptionPerKMPerSeat() + "\n" +
                "Максимално разстояние което може да измине: " + getAircraftClass().calculateMaxFlightDistance() + " км\n" +
                "Капацитет на резервоара за гориво (литри): " + getAircraftClass().getFuelTankCapacity() + "\n" +
                "Средна скорост (км/ч): " + getAircraftClass().getAverageSpeed() + "\n" +
                "Разход за екипаж ($): " + crewCost + "\n";
    }
}
