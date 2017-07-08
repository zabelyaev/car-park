package carpark.carpark;

/**
 * Created by Roman on 08.07.2017.
 */

public class GetDataAdapter {

    String type_bid, address_start, address_finish;

    public void setAddress_start(String address_start) {
        this.address_start = address_start;
    }

    public void setAddress_finish(String address_finish) {
        this.address_finish = address_finish;
    }

    public String getAddress_start() {
        return address_start;
    }

    public String getAddress_finish() {
        return address_finish;
    }

    public String getType_bid() {
        return type_bid;
    }

    public void setType_bid(String type_bid) {
        this.type_bid = type_bid;
    }


}
