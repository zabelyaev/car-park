package carpark.carpark;

/**
 * Created by Roman on 13.07.2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterD extends RecyclerView.Adapter<RecyclerViewAdapterD.BidViewHolder> {

    ArrayList<GetDataAdapterD> bid = new ArrayList<GetDataAdapterD>();
    Context ctx;

    public RecyclerViewAdapterD(ArrayList<GetDataAdapterD> bid, Context ctx) {
        this.bid = bid;
        this.ctx = ctx;
    }

    Context context;

    @Override
    public BidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item_d, parent, false);

        BidViewHolder bidViewHolder = new BidViewHolder(v,ctx,bid);

        return bidViewHolder;
    }


    @Override
    public void onBindViewHolder(BidViewHolder holder, int position) {

        GetDataAdapterD BID =  bid.get(position);

        holder.Type_bid.setText(BID.getType_bid_d());
        holder.Address_start.setText(String.valueOf(BID.getAddress_start_d()));
        holder.Address_finish.setText(String.valueOf(BID.getAddress_finish_d()));
        holder.Bid_Id.setText(String.valueOf(BID.getId_bid_d()));
    }

    @Override
    public int getItemCount() {

        return bid.size();
    }

    public static class BidViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView Type_bid, Address_start, Address_finish, Bid_Id;

        ArrayList<GetDataAdapterD> bid = new ArrayList<GetDataAdapterD>();
        Context ctx;

        public BidViewHolder(View view, Context ctx, ArrayList<GetDataAdapterD> bid) {

            super(view);
            view.setOnClickListener(this);
            this.bid = bid;
            this.ctx = ctx;

            Type_bid = view.findViewById(R.id.type_bid_d);
            Address_start = view.findViewById(R.id.address_start_d);
            Address_finish = view.findViewById(R.id.address_finish_d);
            Bid_Id = view.findViewById(R.id.id_task_item_d);


        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            GetDataAdapterD bid = this.bid.get(position);

                Intent intent = new Intent(this.ctx, info_task_d.class);
                intent.putExtra("id",bid.getId_bid_d());
                this.ctx.startActivity(intent);
        }
    }

}
