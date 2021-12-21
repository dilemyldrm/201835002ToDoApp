package com.mis.a201835002_todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class beyzadilemMain extends AppCompatActivity {

    List<String> toDoList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText editText;

    private TimePickerDialog timePickerDialog;
    final static int islem_kodu=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.list_view_layout, toDoList);
        listView = findViewById(R.id.id_list_view);
        listView.setAdapter(arrayAdapter);
        editText = findViewById(R.id.id_edit_text);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int which_item = position;


                new AlertDialog.Builder(beyzadilemMain.this)
                        .setNeutralButton("Set Alarm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openPickerDialog(false);

                            }

                            private void openPickerDialog(boolean b) {


                                Calendar calendar = Calendar.getInstance();
                                timePickerDialog = new TimePickerDialog(
                                        beyzadilemMain.this,
                                        onTimeSetListener,
                                        calendar.get(Calendar.HOUR_OF_DAY),
                                        calendar.get(Calendar.MINUTE),
                                        b);
                                timePickerDialog.setTitle("Set Alarm");
                                timePickerDialog.show();
                            }

                            TimePickerDialog.OnTimeSetListener onTimeSetListener
                                    =(((view1, hourOfDay, minute) ->{
                                Calendar calNow = Calendar.getInstance();
                                Calendar calSet = (Calendar) calNow.clone();

                                calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calSet.set(Calendar.MINUTE, minute);
                                calSet.set(Calendar.SECOND, 0);
                                calSet.set(Calendar.MILLISECOND, 0);

                                if(calSet.compareTo(calNow) <= 0) {

                                    calSet.add(Calendar.DATE, 1);

                                }

                                setAlarm(calSet);
                            }));

                            private void setAlarm(Calendar calSet) {
                                Toast.makeText(getApplicationContext(), "Alarm set!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getBaseContext(), AlertReceiver.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), islem_kodu, intent, 0);
                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

                            }


                        })
                        .setIcon(android.R.drawable.ic_menu_help)
                        .setTitle("What do you want ?")
                        .setMessage("Choose what you want to do")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(beyzadilemMain.this)
                                        .setIcon(android.R.drawable.ic_delete)
                                        .setTitle("Are you sure?")
                                        .setMessage("Do you want to delete this item ?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                toDoList.remove(which_item);
                                                arrayAdapter.notifyDataSetChanged();
                                            }
                                        })
                                        .setNegativeButton("Cancel", null)
                                        .show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }

        });

    }

    public void addItemToList(View view) {
        toDoList.add(editText.getText().toString());
        arrayAdapter.notifyDataSetChanged();
        editText.setText("");
    }
}