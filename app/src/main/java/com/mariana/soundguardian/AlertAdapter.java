package com.mariana.soundguardian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    private List<Alert> alertList;
    private Context context;

    public AlertAdapter(Context context, List<Alert> alertList) {
        this.context = context;
        this.alertList = alertList;
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_item, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        Alert alert = alertList.get(position);
        holder.bind(alert);
    }

    @Override
    public int getItemCount() {
        return alertList.size();
    }

    public class AlertViewHolder extends RecyclerView.ViewHolder {

        private TextView alertText;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            alertText = itemView.findViewById(R.id.alertText);
        }

        public void bind(Alert alert) {
            alertText.setText(alert.getMessage());
        }
    }
}
