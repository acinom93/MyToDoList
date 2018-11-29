package com.example.monash.mytodolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //constants
    public final int EDIT_ITEM_REQUEST_CODE = 647;
    public final int ADD_ITEM_REQUEST_CODE = 327;

    //variables
    ListView uiListView;
    ArrayList<ToDoItem> todoItemList;
    ToDoItemAdapter todoItemAdapter;
    ItemsDAO todoItemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        uiListView = (ListView) findViewById(R.id.lstView);

        //read data from db and setup the list
        todoItemList = new ArrayList<>();
        todoItemDAO = ToDoItemDB.getDatabase(this).toDoItemDao();
        databaseRead();
        todoItemAdapter = new ToDoItemAdapter(this, todoItemList);
        uiListView.setAdapter(todoItemAdapter);

        setupListViewListener();
    }

    public void onAddClick(View view) {

        Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
        if (intent != null) {

            //put the data and call the add_update activity
            intent.putExtra("item", "");
            intent.putExtra("position", -1);
            startActivityForResult(intent, ADD_ITEM_REQUEST_CODE);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //add new item to list and database
                String userInput = data.getExtras().getString("item");
                ToDoItem editedItem = new ToDoItem(userInput, new Date());

                if (editedItem.getLabel() != null && editedItem.getLabel().length() > 0) {
                    todoItemList.add(0, editedItem);
                    databaseSaveAll();
                    todoItemAdapter.notifyDataSetChanged();
                }
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }

        if (requestCode == EDIT_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                //update item in list and database
                String userInput = data.getExtras().getString("item");
                int position = data.getIntExtra("position", -1);

                ToDoItem editedItem = new ToDoItem(userInput, new Date());
                todoItemList.remove(position);
                todoItemList.add(0, editedItem);
                todoItemAdapter.notifyDataSetChanged();
                databaseSaveAll();
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }


    private void setupListViewListener() {

        uiListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long rowId) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.titlemsg1)
                        .setMessage(R.string.appmsg2)
                        .setPositiveButton(R.string.optionmsg1, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        //remove item from the list and database
                                        todoItemList.remove(position);
                                        todoItemAdapter.notifyDataSetChanged();
                                        databaseSaveAll();
                                    }
                                })
                        .setNegativeButton(R.string.optionmsg3, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                builder.create().show();
                return true;
            }
        });

        uiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem updateItem = todoItemAdapter.getItem(position);
                String updateString = updateItem.getLabel();

                Intent intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                if (intent != null) {

                    //put the data and call the add_update activity
                    intent.putExtra("item", updateString);
                    intent.putExtra("position", position);

                    startActivityForResult(intent, EDIT_ITEM_REQUEST_CODE);
                    todoItemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void databaseRead() {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {

                    //read from database
                    List<ToDoItem> itemsFromDB = todoItemDAO.listAll();
                    if (itemsFromDB != null) {
                        todoItemList.addAll(itemsFromDB);
                    }
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e("Read", ex.getStackTrace().toString());
        }
    }

    private void databaseSaveAll() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                //save to database
                todoItemDAO.deleteAll();
                for (ToDoItem todo : todoItemList) {
                    todoItemDAO.insert(todo);
                }
                return null;
            }
        }.execute();
    }
}
