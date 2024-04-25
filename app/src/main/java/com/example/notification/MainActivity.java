package com.example.notification;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int mYear;
    int mMonth;
    int mDay;
    int mHour;
    int mMinute;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

    }
    Calendar alarmFor; //текущее время
    @RequiresApi(Build.VERSION_CODES.N)
    public void NotificationCheckPoint() {

//        alarmFor.set(mYear, mMonth, mDay);
//        alarmFor.set(Calendar.HOUR_OF_DAY, mHour);
//        alarmFor.set(Calendar.MINUTE, mMinute);
        alarmFor.set(mYear, mMonth, mDay, mHour, mMinute, 0);
        AlertDialog.Builder titleDialogBuilder = new AlertDialog.Builder(this);
        titleDialogBuilder.setTitle("Введите заголовок уведомления");

        EditText titleEditText = new EditText(this);
        titleDialogBuilder.setView(titleEditText);

        titleDialogBuilder.setPositiveButton("OK", (dialog, which) -> {
            String customTitle = titleEditText.getText().toString(); // Получаем введенный пользователем заголовок


            Intent MyIntent = new Intent(getApplicationContext(), AlarmReceiver_SendOn.class); //создаем интент для нашего получателя
            MyIntent.putExtra("title", customTitle);
            PendingIntent MyPendIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, MyIntent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager MyAlarm = (AlarmManager) getSystemService(ALARM_SERVICE); //планируем уведомление в нужное время
            MyAlarm.set(AlarmManager.RTC_WAKEUP, alarmFor.getTimeInMillis(), MyPendIntent);

        });
        //titleDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        titleDialogBuilder.show();


        //alarmFor.add(Calendar.SECOND, 5); //добавляем 5 секунд


        //MyAlarm.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmFor.getTimeInMillis(), MyPendIntent);
    }

    //Button btn1 = (Button) findViewById(R.id.b1);
    @RequiresApi(Build.VERSION_CODES.N)
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.b1) {
            callDate();
        }

    }

    private void callDate() {

        alarmFor = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mydate = dayOfMonth + "." + (month + 1) + "." + year;
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        callTime();
                    }
                }, alarmFor.get(Calendar.YEAR), alarmFor.get(Calendar.MONTH), alarmFor.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    private void callTime() {


        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String myTime = hourOfDay + " : " + minute;
                        mHour = hourOfDay;
                        mMinute = minute;
                        NotificationCheckPoint();
                    }
                }, alarmFor.get(Calendar.HOUR_OF_DAY), alarmFor.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }
}