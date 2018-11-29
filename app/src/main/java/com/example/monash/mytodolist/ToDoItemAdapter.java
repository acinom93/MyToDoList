package com.example.monash.mytodolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    //variables
    private Context mContext;
    private List<ToDoItem> toDoItemsList = new ArrayList<>();

    //constructor
    public ToDoItemAdapter(@NonNull Context context, ArrayList<ToDoItem> list) {
        super(context, 0, list);
        mContext = context;
        toDoItemsList = list;
    }

    //gets the UI view for the adapter
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent, false);
        }

        ToDoItem currentItem = toDoItemsList.get(position);

        TextView itemDate = (TextView) listItem.findViewById(R.id.todoitemdate);
        itemDate.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(currentItem.getDate()));

        TextView item = (TextView) listItem.findViewById(R.id.todoitemlabel);
        item.setText(currentItem.getLabel());

        return listItem;
    }
}
