package com.cleverox.nuevopudahuel.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzutils.BZUtils;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.BaseActivity;
import com.cleverox.nuevopudahuel.model.Flight;

import io.realm.RealmResults;

/**
 * Created by moddity on 7/3/16.
 */
public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

    private BaseActivity activity;
    private RealmResults<Flight> vols;
    private View.OnClickListener onItemListener;

    public FlightsAdapter(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(activity.getLayoutInflater().inflate(R.layout.flights_cell, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Flight vol = vols.get(position);
        holder.id.setText(vol.getFlightCode());
        holder.origen.setText(vol.isArrival() ? vol.getOrigin() : vol.getDestination());
        holder.tiempo.setText(BZUtils.dateToString(vol.getEstimated(), "HH:mm"));
        holder.estado.setText(vol.getStatusText());
        holder.favorite.setImageResource(vol.isFavorite() ? R.drawable.iconalertaenvuelo : R.drawable.iconarrowvuelos);
        holder.estado.setText(vol.isFavorite() ? "Favorito" : vol.getStatusText());
        holder.cell.setTag(vol);
        holder.cell.setOnClickListener(this.onItemListener);

    }

    @Override
    public int getItemCount() {
       if(vols != null) return vols.size();
           return 0;
    }

    public void setVols(RealmResults<Flight> vols){
        this.vols = vols;
    }

    public void setOnItemListener(View.OnClickListener onItemListener){
        this.onItemListener = onItemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id, origen, tiempo, estado;
        private LinearLayout cell;
        private ImageView favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView)itemView.findViewById(R.id.list_id);
            origen = (TextView) itemView.findViewById(R.id.list_origen);
            tiempo = (TextView) itemView.findViewById(R.id.list_tiempo);
            estado = (TextView) itemView.findViewById(R.id.list_estado);
            cell = (LinearLayout) itemView.findViewById(R.id.list_cell);
            favorite = (ImageView) itemView.findViewById(R.id.list_img);
        }
    }
}
