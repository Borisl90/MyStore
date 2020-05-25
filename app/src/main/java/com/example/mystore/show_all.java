package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Objects;

public class show_all extends AppCompatActivity {
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        status           = findViewById(R.id.allCustomers);
        Intent i           = getIntent();
        String strCustomers      = Objects.requireNonNull(i.getExtras()).getString("allcustomers");
        status.setText(strCustomers);
    }
}
