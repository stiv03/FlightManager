public class AircraftClass {

   private  Type type;
    private String manufacturer;
    private String model;
    private int seats;
    private double minRunwayLength;
    private  double fuelConsumptionPerKMPerSeat;
    private double fuelTankCapacity;
    private double averageSpeed;

    public AircraftClass(String manufacturer, String model, int seats,
                         double minRunwayLength, double fuelConsumptionPerKMPerSeat,
                         double fuelTankCapacity, double averageSpeed) {
        setManufacturer(manufacturer);
        setModel(model);
        setSeats(seats);
        setMinRunwayLength(minRunwayLength);
        setFuelConsumptionPerKMPerSeat(fuelConsumptionPerKMPerSeat);
        setFuelTankCapacity(fuelTankCapacity);
        setAverageSpeed(averageSpeed);
        autoDetermineType();
    }

    public void autoDetermineType() {
        if (seats < 100 && minRunwayLength < 2000) {
            type = Type.A; // Малък самолет
        } else if (seats >= 100 && seats < 200 &&
                minRunwayLength >= 2000 && minRunwayLength < 3000) {
            type = Type.B; // Среден самолет
        } else if (seats >= 200 && seats <= 300 && minRunwayLength >= 3000) {
            type = Type.C; // Голям самолет
        }else {
            type = Type.UNRECOGNIZED;
        }
    }

    public double calculateMaxFlightDistance(){
        return fuelTankCapacity / fuelConsumptionPerKMPerSeat;
    }


    public Type getType() {
        return type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer.isEmpty()) {
            throw new IllegalArgumentException("Производителя не може да бъде празен.");
        }
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (model.isEmpty()) {
            throw new IllegalArgumentException("Модела не може да бъде празен.");
        }
        this.model = model;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Седалките трябва да са позитивно число");
        }
        this.seats = seats;
    }

    public double getMinRunwayLength() {
        return minRunwayLength;
    }

    public void setMinRunwayLength(double minRunwayLength) {
        if (minRunwayLength <= 0) {
            throw new IllegalArgumentException("Пистата трябва да е положително число");
        }
        this.minRunwayLength = minRunwayLength;
    }

    public double getFuelConsumptionPerKMPerSeat() {
        return fuelConsumptionPerKMPerSeat;
    }

    public void setFuelConsumptionPerKMPerSeat(double fuelConsumptionPerKMPerSeat) {
        if (fuelConsumptionPerKMPerSeat <= 0) {
            throw new IllegalArgumentException("Трябва да е положително число");
        }
        this.fuelConsumptionPerKMPerSeat = fuelConsumptionPerKMPerSeat;
    }

    public double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(double fuelTankCapacity) {
        if (fuelTankCapacity <= 0) {
            throw new IllegalArgumentException("Капацитета на горивото трябва да е положително число");
        }
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        if (averageSpeed <= 0) {
            throw new IllegalArgumentException("Средната скорост трябва да е положително число");
        }
        this.averageSpeed = averageSpeed;
    }

    @Override
    public String toString() {
        return "Клас на самолета\n" +
                "Клас: " + type + "\n" +
                "Прозиводител: " + manufacturer + "\n" +
                "Модел: " + model + "\n" +
                "Седалки: " + seats + "\n" +
                "Максимално разстояние: " + calculateMaxFlightDistance() + " км\n" +
                "Минимална дължина на пистата: " + minRunwayLength + " метра\n";
    }
}
