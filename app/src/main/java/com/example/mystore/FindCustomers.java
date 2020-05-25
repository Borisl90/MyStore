package com.example.mystore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class FindCustomers extends AppCompatActivity implements View.OnClickListener{
    Button btFindCust;
    EditText etLikeNmae;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_customers);
        btFindCust = findViewById(R.id.btFind);
        btFindCust.setOnClickListener(this);
        myDb = new DatabaseHelper(this);
        etLikeNmae =findViewById(R.id.etfName);
    }

    @Override
    public void onClick(View v) {
        if (v == btFindCust)
        {
            String strNmae = etLikeNmae.getText().toString();
            Cursor res = myDb.getLikeName(strNmae);
            if (res.getCount() == 0) {
                showMessage("Error", "אין התאמה כלל");
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
            Intent i = new Intent(FindCustomers.this, show_all.class);
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
