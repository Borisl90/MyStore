package com.example.mystore;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AvgShopping extends AppCompatActivity implements View.OnClickListener {
    Button btMaxVal;
    EditText etAvgValue;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avg_shopping);
        btMaxVal = findViewById(R.id.btFind);
        btMaxVal.setOnClickListener(this);
        myDb = new DatabaseHelper(this);
        etAvgValue = findViewById(R.id.etMaxAvg);

    }

    @Override
    public void onClick(View v) {
        if (v == btMaxVal)
        {
            String strAvg = etAvgValue.getText().toString();
            Cursor res = myDb.getMaxAvg(strAvg);
            if (res.getCount() == 0) {
                showMessage("Error", "Nothing found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()) {
                buffer.append("Id :" + res.getString(0) + "\n");
                buffer.append("First name :" + res.getString(1) + "\n");
                buffer.append("Last name :" + res.getString(2) + "\n");
                buffer.append("Address :" + res.getString(3) + "\n");
                buffer.append("Avg shopping :" + res.getString(4) + "\n");
            }
            Intent i = new Intent(AvgShopping.this, show_all.class);
            i.putExtra("allcustomers", (CharSequence) buffer);
            startActivity(i);
        }
    }
    private void showMessage(String title, String Message){
        AlertDialog.Builder builder = new
                AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}