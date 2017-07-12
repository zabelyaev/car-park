package carpark.carpark;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 08.07.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<GetDataAdapter> getDataAdapter;
    ItemClickListener clickListener;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context) {
        super();
        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    public void setListContent(ArrayList<GetDataAdapter> getDataAdapter){
        this.getDataAdapter=getDataAdapter;
        notifyItemRangeChanged(0,getDataAdapter.size());

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        holder.Type_bid.setText(getDataAdapter1.getType_bid());
        holder.Address_start.setText(String.valueOf(getDataAdapter1.getAddress_start()));
        holder.Address_finish.setText(String.valueOf(getDataAdapter1.getAddress_finish()));
        holder.Bid_Id.setText(String.valueOf(getDataAdapter1.getId_bid()));
        holder.Status_Bid.setText(String.valueOf(getDataAdapter1.getStatus_bid()));
    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Type_bid, Address_start, Address_finish, Bid_Id, Status_Bid;

        public ViewHolder(View itemView) {

            super(itemView);

            Type_bid = itemView.findViewById(R.id.type_bid);
            Address_start = itemView.findViewById(R.id.address_start);
            Address_finish = itemView.findViewById(R.id.address_finish);
            Bid_Id = itemView.findViewById(R.id.id_task_item);
            Status_Bid = itemView.findViewById(R.id.status_bid);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);

        }
            @Override
            public void onClick(View view) {
                if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
            }
    }

    public void removeAt(int position) {
        getDataAdapter.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, getDataAdapter.size());
    }

}

