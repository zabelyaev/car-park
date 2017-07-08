package carpark.carpark;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Roman on 08.07.2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<GetDataAdapter> getDataAdapter;

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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        holder.Type_bid.setText(getDataAdapter1.getType_bid());
        holder.Address_start.setText(String.valueOf(getDataAdapter1.getAddress_start()));
        holder.Address_finish.setText(String.valueOf(getDataAdapter1.getAddress_finish()));
    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Type_bid, Address_start, Address_finish;

        public ViewHolder(View itemView) {

            super(itemView);

            Type_bid = itemView.findViewById(R.id.type_bid);
            Address_start = itemView.findViewById(R.id.address_start);
            Address_finish = itemView.findViewById(R.id.address_finish);

        }
    }

}
