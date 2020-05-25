package com.example.mystore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editFirstName, editLastName, editAvg,editAddress,editTextId;
    Button btnAddData;
    Button btnviewAll;
    Button btnDelete;
    Button btnviewUpdate;
    Customer temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        // capture
        editFirstName = (EditText)findViewById(R.id.et_first_name);
        editLastName = (EditText)findViewById(R.id.et_last_name);
        editAvg = (EditText)findViewById(R.id.et_avg_shopping);
        editTextId = (EditText)findViewById(R.id.et_national_id);
        editAddress = (EditText)findViewById(R.id.et_address);
        btnAddData = (Button)findViewById(R.id.btn_add);
        btnviewAll = (Button)findViewById(R.id.btn_view);
        btnviewUpdate= (Button)findViewById(R.id.btn_uppdate);
        btnDelete= (Button)findViewById(R.id.btn_delete);
    }


    public void add(View view) {
        try {
            temp = new Customer(editFirstName.getText().toString(), editLastName.getText().toString(), editAddress.getText().toString(), Integer.parseInt(editAvg.getText().toString()));

            boolean isInserted = myDb.insertData(temp);
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

    public void checkname(View view)
    {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Insert Name")
                .setMessage("Insert name To check substrings!")
                .setView(taskEditText)
                .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            if(res.getString(1).toLowerCase().contains(taskEditText.getText().toString().toLowerCase())) {
                                buffer.append("Id :" + res.getString(0) + "\n");
                                buffer.append("Name :" + res.getString(1) + "\n");
                                buffer.append("Surname :" + res.getString(2) + "\n");
                                buffer.append("City :" + res.getString(3) + "\n");
                                buffer.append("Avg :" + res.getString(4) + "\n\n");
                            }
                        }
                        showMessage("Check Name "+taskEditText.getText()+" Result",buffer.toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void checkavg(View view)
    {
        final EditText taskEditText = new EditText(this);
        taskEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Insert Avg")
                .setMessage("Insert avg to check sorting!")
                .setView(taskEditText)
                .setPositiveButton("Check", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor res = myDb.getAVG(Integer.parseInt(taskEditText.getText().toString()));
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Surname :" + res.getString(2) + "\n");
                            buffer.append("City :" + res.getString(3) + "\n");
                            buffer.append("Avg :" + res.getString(4) + "\n\n");
                        }
                        showMessage("Check AVG "+taskEditText.getText().toString(),buffer.toString());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new
                AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    public void update(View view) {
        temp=new Customer(editFirstName.getText().toString(),editLastName.getText().toString(),editAddress.getText().toString(),Integer.parseInt(editAvg.getText().toString()));
        boolean isUpdate = myDb.updateData(editTextId.getText().toString(),temp);
        if(isUpdate == true)
            Toast.makeText(MainActivity.this,
                    "Data Update",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,
                    "Data not Updated",Toast.LENGTH_LONG).show();
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
