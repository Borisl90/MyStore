package com.example.mystore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DatabaseHelper myDb;
    EditText editFirstName, editLastName, editAvg,editAddress,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;
    Button btnFindCustomer;
    Button btnAvg;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        // capture
        editFirstName   = (EditText)findViewById(R.id.et_first_name);
        editLastName    = (EditText)findViewById(R.id.et_last_name);
        editAvg         = (EditText)findViewById(R.id.et_avg_shopping);
        editTextId      = (EditText)findViewById(R.id.et_national_id);
        editAddress     = (EditText)findViewById(R.id.et_address);
        btnAddData      = (Button)findViewById(R.id.btn_add);
        btnviewAll      = (Button)findViewById(R.id.btn_view);
        btnviewUpdate   = (Button)findViewById(R.id.btn_uppdate);
        btnDelete       = (Button)findViewById(R.id.btn_delete);
        btnFindCustomer = (Button)findViewById(R.id.btn_find_customer);
        btnAvg          = (Button)findViewById(R.id.btn_avg);
        btnFindCustomer = findViewById(R.id.btn_find_customer);
        btnFindCustomer.setOnClickListener(this);
        btnAvg = findViewById(R.id.btn_avg);
        btnAvg.setOnClickListener(this);
        btnviewUpdate = findViewById(R.id.btn_uppdate);
        btnviewUpdate.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v)
    {
        if(v ==  btnFindCustomer)
        {
            Intent intent = new Intent(this, FindCustomers.class);
            startActivity(intent);
        }
        else if(v == btnAvg)
        {
            Intent intent = new Intent(this, AvgShopping.class);
            startActivity(intent);
        }
        else if(v == btnviewUpdate)
        {
            Intent intent = new Intent(this, UpdateById.class);
            startActivity(intent);
        }
    }

    public void add(View view) {
        try {

            if(!ValidateFields())
                return;

            String strFirstName = editFirstName.getText().toString();
            String strLastName = editLastName.getText().toString();
            Cursor res = myDb.getSameCustomer(strFirstName, strLastName);
            if(res.getCount() != 0) {
                showMessage("Error","לקוח זה קיים במערכת, לא ניתן להוסיף עוד פעם");
                return;
            }

            customer = new Customer(editFirstName.getText().toString(), editLastName.getText().toString(), editAddress.getText().toString(), Integer.parseInt(editAvg.getText().toString()));

            boolean isInserted = myDb.insertData(customer);
            if (isInserted == true)
                Toast.makeText(MainActivity.this,
                        "Data Inserted", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(MainActivity.
                        this, "Data not Inserted", Toast.LENGTH_LONG).show();
            editFirstName.setText("");
            editLastName.setText("");
            editAvg.setText("");
            editTextId.setText("");
            editAddress.setText("");
        }
        catch (Exception e1)
        {
            Toast.makeText(MainActivity.
                    this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void all(View view) {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("Name :"+ res.getString(1)+"\n");
            buffer.append("Surname :"+ res.getString(2)+"\n");
            buffer.append("City :"+ res.getString(3)+"\n");
            buffer.append("Avg :"+ res.getString(4)+"\n\n");

        }
        // Show all data

        Intent i = new Intent(MainActivity.this, show_all.class);
        i.putExtra("allcustomers", (CharSequence) buffer);
        startActivity(i);

    }

    private Boolean ValidateFields() {
        Boolean bOk = false;

        String strFirstName = editFirstName.getText().toString();
        String strLastName = editLastName.getText().toString();
        String strAddress = editAddress.getText().toString();
        String strAvg = editAvg.getText().toString();
        if((strFirstName.isEmpty() || strLastName.isEmpty() || strAddress.isEmpty() || strAvg.isEmpty()))
        {
            try {
                throw new Exception("נא למלא את כל השדות (שם פרטי, שם משפחה, עיר, ממוצע וקניות)");
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                return bOk;
            }

        }

        if(strFirstName.matches(".*\\d.*") || strLastName.matches(".*\\d.*"))
        {
            try {
                throw new Exception("נא למלא קלט תקין, רק אותיות בשם פרטי ובשם משפחה");
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                return bOk;
            }
        }
        if(strAddress.matches(".*\\d.*"))
        {
            try {
                throw new Exception("נא למלא קלט תקין, רק אותיות בכתובת - שם היישוב בלבד");
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                return bOk;
            }
        }
        if(!strAvg.matches(".*\\d.*"))
        {
            try {
                throw new Exception("נא למלא קלט תקין, רק ספרות בממוצע קניות");
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                return bOk;
            }
        }
        bOk = true;
        return  bOk;

    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new
                AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    public void delete(View view) {
        Integer deletedRows =
                myDb.deleteData(editTextId.getText().toString());
        if(deletedRows > 0)
            Toast.makeText(MainActivity.this,
                    "Data Deleted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,
                    "Data not Deleted",Toast.LENGTH_LONG).show();
    }

}
