package ognora.alterationapp.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import ognora.alterationapp.Model.AddressModel;
import ognora.alterationapp.R;

public class PickupDetailActivity extends AppCompatActivity {

    TextView pickup_adddress, date, time;
    Button calender, clock, cont;
    int hh, mm, y, m, da;
    Toolbar toolbar ;
    String str_date;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_detail);

        pickup_adddress = findViewById(R.id.pickup_address);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        calender = findViewById(R.id.calender);
        clock = findViewById(R.id.clock);
        cont = findViewById(R.id.cont);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Date and Time");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String address;
        Bundle bundle = getIntent().getExtras();
        final AddressModel addressModel = (AddressModel) bundle.get("pickup_address");

        if (addressModel.getLandmark().equalsIgnoreCase("")) {
            address = addressModel.getArea() + ", " + addressModel.getCity() + ", " + addressModel.getState() + "- " +
                    addressModel.getPincode();
        } else {
            address = addressModel.getArea() + ", " + addressModel.getLandmark() + ", " + addressModel.getCity() + ", " +
                    addressModel.getState() + "- " + addressModel.getPincode();
        }

        pickup_adddress.setText(address);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                y = cal.get(Calendar.YEAR);
                m = cal.get(Calendar.MONTH);
                da= cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PickupDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        y, m, da);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.show();
            //    str_date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
            }

        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                // Log.d(finalTAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

               // String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d).toString();
                y = year;
                m = month;
                da = day;

                final String date_str = day + "/" + month + "/" + year;
                date.setText(date_str);

              //  str_date = DateFormat.format("dd-MM-yyyy hh:mm:ss", datePicker).toString();

            }
        };

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                hh = cal.get(Calendar.HOUR);
                mm = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(PickupDetailActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener, hh, mm, false);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.show();
            }

        });


        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {

                final String time_str = hour + " : " + min + " ";
                time.setText(time_str);
                hh = hour ;
                mm = min;

            }


        };

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Calendar d = Calendar.getInstance();

               // Date d = new Date(year, month, day, hh, mm);

                Calendar d = Calendar.getInstance();
                d.set(y , m, da, hh, mm);

                str_date = DateFormat.format("dd-MM-yyyy hh:mm:ss", d).toString();
                Intent intent = new Intent(PickupDetailActivity.this, BillActivity.class);
                intent.putExtra("bill_address", address);
                intent.putExtra("date", str_date);
                intent.putExtra("address_id", addressModel.get_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}

