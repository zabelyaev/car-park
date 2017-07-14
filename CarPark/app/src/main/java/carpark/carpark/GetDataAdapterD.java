package carpark.carpark;

/**
 * Created by Roman on 08.07.2017.
 */

public class GetDataAdapterD {

    public GetDataAdapterD(String type_bid_d,String address_start_d,String address_finish_d,String id_bid_d)
    {
        this.setType_bid_d(type_bid_d);
        this.setAddress_start_d(address_start_d);
        this.setAddress_finish_d(address_finish_d);
        this.setId_bid_d(id_bid_d);
    }

    String type_bid_d, address_start_d, address_finish_d, id_bid_d;


    public String getType_bid_d() {
        return type_bid_d;
    }

    public void setType_bid_d(String type_bid_d) {
        this.type_bid_d = type_bid_d;
    }

    public String getAddress_start_d() {
        return address_start_d;
    }

    public void setAddress_start_d(String address_start_d) {
        this.address_start_d = address_start_d;
    }

    public String getAddress_finish_d() {
        return address_finish_d;
    }

    public void setAddress_finish_d(String address_finish_d) {
        this.address_finish_d = address_finish_d;
    }

    public String getId_bid_d() {
        return id_bid_d;
    }

    public void setId_bid_d(String id_bid_d) {
        this.id_bid_d = id_bid_d;
    }
}

