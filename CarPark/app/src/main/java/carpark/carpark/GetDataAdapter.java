package carpark.carpark;

/**
 * Created by Roman on 08.07.2017.
 */

public class GetDataAdapter {

    public GetDataAdapter(String type_bid,String address_start,String address_finish,
                          String id_bid, String status_bid)
    {
        this.setType_bid(type_bid);
        this.setAddress_start(address_start);
        this.setAddress_finish(address_finish);
        this.setId_bid(id_bid);
        this.setStatus_bid(status_bid);
    }

    String type_bid, address_start, address_finish, id_bid, status_bid;


    public String getType_bid() {
        return type_bid;
    }

    public void setType_bid(String type_bid) {
        this.type_bid = type_bid;
    }

    public String getAddress_start() {
        return address_start;
    }

    public void setAddress_start(String address_start) {
        this.address_start = address_start;
    }

    public String getAddress_finish() {
        return address_finish;
    }

    public void setAddress_finish(String address_finish) {
        this.address_finish = address_finish;
    }

    public String getId_bid() {
        return id_bid;
    }

    public void setId_bid(String id_bid) {
        this.id_bid = id_bid;
    }

    public String getStatus_bid() {
        return status_bid;
    }

    public void setStatus_bid(String status_bid) {
        this.status_bid = status_bid;
    }
}

