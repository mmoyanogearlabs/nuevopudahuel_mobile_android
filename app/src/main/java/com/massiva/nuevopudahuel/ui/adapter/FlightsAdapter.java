package com.massiva.nuevopudahuel.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bzutils.BZUtils;
import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.BaseActivity;
import com.massiva.nuevopudahuel.controllers.FlightsController;
import com.massiva.nuevopudahuel.model.Flight;

import io.realm.RealmResults;

/**
 * Created by moddity on 7/3/16.
 */
public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

    private BaseActivity activity;
    private RealmResults<Flight> vols;
    private View.OnClickListener onItemListener;
    private int bottomOffset = 0;

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
        holder.tiempo.setText(null != vol.getEstimated() ? BZUtils.dateToString(vol.getEstimated(), "HH:mm") : "");
        holder.estado.setText(vol.getStatusText());
        holder.favorite.setImageResource(vol.isFavorite() ? R.drawable.iconalertaenvuelo : R.drawable.iconarrowvuelos);
        holder.estado.setText(vol.getStatusText());
        holder.estado.setTextColor(activity.getResources().getColor(FlightsController.getInstance().getStatusColor(activity, vol)));
        holder.stopoverLayout.setVisibility(TextUtils.isEmpty(vol.getStopOver()) ? View.INVISIBLE : View.VISIBLE);
        holder.stopover.setText(vol.getStopOver());
        holder.mainFlight.setText(vol.getMainFlightCode());
        holder.cell.setTag(vol);
        holder.cell.setOnClickListener(this.onItemListener);
        LinearLayout.LayoutParams offsetParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, bottomOffset);
        holder.bottomOffset.setLayoutParams(offsetParams);
        holder.bottomOffset.setVisibility(position == getItemCount() - 1 ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
       if(vols != null) return vols.size();
           return 0;
    }

    public void setVols(RealmResults<Flight> vols){
        this.vols = vols;
    }

    public RealmResults<Flight> getVols() {
        return vols;
    }

    public void setOnItemListener(View.OnClickListener onItemListener){
        this.onItemListener = onItemListener;
    }

    public void setBottomOffset(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id, origen, tiempo, estado, stopover, mainFlight;
        private LinearLayout cell, stopoverLayout, bottomOffset;
        private ImageView favorite;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView)itemView.findViewById(R.id.list_id);
            origen = (TextView) itemView.findViewById(R.id.list_origen);
            tiempo = (TextView) itemView.findViewById(R.id.list_tiempo);
            estado = (TextView) itemView.findViewById(R.id.list_estado);
            cell = (LinearLayout) itemView.findViewById(R.id.list_cell);
            favorite = (ImageView) itemView.findViewById(R.id.list_img);
            stopover = (TextView) itemView.findViewById(R.id.flights_cell_stopover);
            stopoverLayout = (LinearLayout) itemView.findViewById(R.id.flights_cell_stopover_layout);
            mainFlight = (TextView) itemView.findViewById(R.id.list_id_main_flight);
            bottomOffset = (LinearLayout) itemView.findViewById(R.id.list_bottom_offset);
        }
    }
}
