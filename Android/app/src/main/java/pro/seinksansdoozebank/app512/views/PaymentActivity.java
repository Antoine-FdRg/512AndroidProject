package pro.seinksansdoozebank.app512.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.text.HtmlCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import pro.seinksansdoozebank.app512.R;

public class PaymentActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    Button dateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ImageButton backButton = findViewById(R.id.back_button);
        int carId = getIntent().getIntExtra("carId", -1);
        int latitude = getIntent().getIntExtra("latitude", -1);
        int longitude = getIntent().getIntExtra("longitude", -1);
        backButton.setOnClickListener(v -> finish());
        Button buyButton = findViewById(R.id.buy_button);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(v -> datePickerDialog.show());

        buyButton.setOnClickListener(v -> {
            if(savePurchaseToJSON(carId, String.valueOf(firstName.getText()), String.valueOf(lastName.getText()), String.valueOf(dateButton.getText()), latitude, longitude)){
                sendConfirmationNotification();
                //TODO rajouter la page de confirmation de paiement
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



    private boolean savePurchaseToJSON(int carId, String name, String lastName, String date, double latitude, double longitude) {
        JSONObject root = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("carId", carId);
            jsonObject.put("name", name);
            jsonObject.put("lastName", lastName);
            jsonObject.put("date", date);
            jsonObject.put("latitude", latitude);
            jsonObject.put("longitude", longitude);
            jsonArray.put(jsonObject);
            root.put("purchases", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String FILENAME = "purchases.json";
        String jsonString = root.toString();
        try {
            FileOutputStream fos = this.getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

    private void sendConfirmationNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.car)
                .setAutoCancel(true)
                .setContentTitle(HtmlCompat.fromHtml("<font color=\"" + getColor(R.color.blue) + "\"><b>" + getString(R.string.notification_title) + "</b></font>", HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setContentText(getString(R.string.notification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("512Cars", "PERMISSION NOT GRANTED");
            return;
        }
        notificationManager.notify(1, builder.build());
    }


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(this,  dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}