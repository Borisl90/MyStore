package com.example.mystore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.zip.CheckedInputStream;

public class UpdateById extends AppCompatActivity implements View.OnClickListener {
    Button btnUpdate;
    EditText etLikeNmae;
    EditText editFirstName, editLastName, editAvg,editAddress,editTextId;
    DatabaseHelper myDb;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_by_id);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        myDb = new DatabaseHelper(this);
        etLikeNmae =findViewById(R.id.etfName);
        editFirstName = (EditText)findViewById(R.id.et_first_name);
        editLastName = (EditText)findViewById(R.id.et_last_name);
        editAvg = (EditText)findViewById(R.id.et_avg_shopping);
        editTextId = (EditText)findViewById(R.id.et_national_id);
        editAddress = (EditText)findViewById(R.id.et_address);
    }

    @Override
    public void onClick(View v) {
        if(v== btnUpdate)
        {
            int nId = Integer.parseInt(editTextId.getText().toString());
            if(CheckId(nId))
            {
                customer = new Customer(editFirstName.getText().toString(),editLastName.getText().toString(),editAddress.getText().toString(),Integer.parseInt(editAvg.getText().toString()));
                boolean isUpdate = myDb.updateData(editTextId.getText().toString(),customer);

                if(isUpdate == true)
                    Toast.makeText(UpdateById.this,
                            "Data Update",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(UpdateById.this,
                            "Data not Updated",Toast.LENGTH_LONG).show();
            }

        }

    }

    private Boolean CheckId(int nId)
    {
        Boolean bOk = false;
        if(!myDb.checkExistId(nId))
            try {
                throw new Exception("לא קיים לקוח עם מספר זיהוי כזה במערכת");
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                return bOk;
            }
        bOk = true;
        return bOk;
    }
}
