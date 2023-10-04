package com.mariana.soundguardian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AlertHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlertAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Crear una lista de alertas de ejemplo (debes reemplazar esto con tus propios datos)
        List<Alert> alertList = new ArrayList<>();
        alertList.add(new Alert("Alerta 1", "Esta es la primera alerta.", "2023-09-18 10:30 AM"));
        alertAdapter = new AlertAdapter(this, alertList);
        recyclerView.setAdapter(alertAdapter);
    }
}
