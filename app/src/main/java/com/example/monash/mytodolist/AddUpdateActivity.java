package com.example.monash.mytodolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddUpdateActivity extends AppCompatActivity {

    public int position = 0;
    EditText editItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_update);

        //get data from the main screen and set the edit text
        String editItem = getIntent().getStringExtra("item");
        position = getIntent().getIntExtra("position", -1);
        this.editItem = (EditText) findViewById(R.id.addupdateText);
        this.editItem.setText(editItem);
    }

    public void onSubmit(View v) {

        editItem = (EditText) findViewById(R.id.addupdateText);

        //send back data on save to add or update
        Intent data = new Intent();
        data.putExtra("item", editItem.getText().toString());
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onCancel(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddUpdateActivity.this);
        builder.setTitle(R.string.titlemsg2)
                .setMessage(R.string.appmsg3)
                .setPositiveButton(R.string.optionmsg1, new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //send back empty data on cancel
                                Intent data = new Intent();
                                setResult(RESULT_CANCELED, data);
                                finish();
                            }
                        })
                .setNegativeButton(R.string.optionmsg3, new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
        builder.create().show();
    }

}
