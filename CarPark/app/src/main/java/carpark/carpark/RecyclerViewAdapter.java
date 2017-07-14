package carpark.carpark;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BidViewHolder> {

    ArrayList<GetDataAdapter> bid = new ArrayList<GetDataAdapter>();
    Context ctx;

    public RecyclerViewAdapter(ArrayList<GetDataAdapter> bid, Context ctx) {
        this.bid = bid;
        this.ctx = ctx;
    }

    Context context;

    @Override
    public BidViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);

        BidViewHolder bidViewHolder = new BidViewHolder(v,ctx,bid);

        return bidViewHolder;
    }


    @Override
    public void onBindViewHolder(BidViewHolder holder, int position) {

        GetDataAdapter BID =  bid.get(position);

        holder.Type_bid.setText(BID.getType_bid());
        holder.Address_start.setText(String.valueOf(BID.getAddress_start()));
        holder.Address_finish.setText(String.valueOf(BID.getAddress_finish()));
        holder.Bid_Id.setText(String.valueOf(BID.getId_bid()));
        holder.Status_Bid.setText(String.valueOf(BID.getStatus_bid()));
    }

    @Override
    public int getItemCount() {

        return bid.size();
    }

    public static class BidViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView Type_bid, Address_start, Address_finish, Bid_Id, Status_Bid;

        ArrayList<GetDataAdapter> bid = new ArrayList<GetDataAdapter>();
        Context ctx;

        public BidViewHolder(View view, Context ctx, ArrayList<GetDataAdapter> bid) {

            super(view);
            view.setOnClickListener(this);
            this.bid = bid;
            this.ctx = ctx;

            Type_bid = view.findViewById(R.id.type_bid);
            Address_start = view.findViewById(R.id.address_start);
            Address_finish = view.findViewById(R.id.address_finish);
            Bid_Id = view.findViewById(R.id.id_task_item);
            Status_Bid = view.findViewById(R.id.status_bid);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            GetDataAdapter bid = this.bid.get(position);

            if (Status_Bid.getText().toString().equals("Не принято")) {
                Intent intent = new Intent(this.ctx, edit_task.class);
                intent.putExtra("id",bid.getId_bid());
                intent.putExtra("type_bid",bid.getType_bid());
                this.ctx.startActivity(intent);
            } else if (Status_Bid.getText().toString().equals("Принято")) {
                Intent intent = new Intent(this.ctx, activ_task.class);
                intent.putExtra("id", bid.getId_bid());
                this.ctx.startActivity(intent);
            }

        }
    }


}

