package BlueMarbleDTO;

public class City {
    private String name;

    private int owner = -1;
    private int landPrice;
    private int hotel;
    private int hotelPay;
    private int hotelCount = 0;
    private int building;
    private int buildingPay;
    private int buildingCount = 0;

    public City(String name, int landPrice, int hotel, int hotelPay, int building, int buildingPay) {
        this.name = name;
        this.landPrice = landPrice;
        this.hotel = hotel;
        this.hotelPay = hotelPay;
        this.building = building;
        this.buildingPay = buildingPay;
    }
    public void addHotel(int count){
        hotel += count;
    }
    public boolean removeHotel(int count){
        if(hotel-count < 0){
            System.out.println("호텔의 갯수가 부족합니다");
            return false;
        }else{
            hotel -= count;
            System.out.println("호텔 판매!");
            return true;
        }
    }
    public void addBuilding(int count){
        building += count;
    }
    public boolean removeBuilding(int count){
        if(building-count < 0){
            System.out.println("빌딩의 갯수가 부족합니다");
            return false;
        }else{
            hotel -= count;
            System.out.println("빌딩 판매!");
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getLandPrice() {
        return landPrice;
    }

    public void setLandPrice(int landPrice) {
        this.landPrice = landPrice;
    }

    public int getHotel() {
        return hotel;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }

    public int getHotelPay() {
        return hotelPay;
    }

    public void setHotelPay(int hotelPay) {
        this.hotelPay = hotelPay;
    }

    public int getHotelCount() {
        return hotelCount;
    }

    public void setHotelCount(int hotelCount) {
        this.hotelCount = hotelCount;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getBuildingPay() {
        return buildingPay;
    }

    public void setBuildingPay(int buildingPay) {
        this.buildingPay = buildingPay;
    }

    public int getBuildingCount() {
        return buildingCount;
    }

    public void setBuildingCount(int buildingCount) {
        this.buildingCount = buildingCount;
    }
}
