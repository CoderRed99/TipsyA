package com.app.dianna.tipsy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class BillActivity extends Activity
{
    public String name;
    public double meal, total, tip;
    NumberFormat nm = NumberFormat.getCurrencyInstance();
    private EditText etBillAmt;
    private double billAmt = 0;
    private int noOfGuests = 0;
    private static int guestCounter = 0;
    private Spinner spGuestNumber;
    private Button btnSetTaxes;
    private Button btnCalculate;
    private TextView tvMealDiff;
    private final int REQUEST_CODE = 20;
    private ListView lvGuests;
    GuestsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("DEBUG", "onCreate");
        setContentView(R.layout.activity_bill);
        setupViews();

        // Allows for automatic recalculation based on any change to the meal amount
/*        etBillAmt.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {// Fires right after the text has changed

                Toast.makeText(BillActivity.this, "bill amt changed, " +
                        "select guest for meal amt changes", Toast.LENGTH_SHORT).show();
                String strmealAmount = etMealAmt.getText().toString();
                mealAmount = Double.parseDouble(strmealAmount);
                Toast.makeText(TipCalcActivity.this, "afterTextChanged-meal amt", Toast.LENGTH_SHORT).show();

                if (etCustomTipAmt == null)
                {
                    Log.d("DEBUG", "meal amt changed, calling onDetermineTip!");
                    onDetermineTip(vCurrentTipAmt);
                } else {
                    Log.d("DEBUG", "meal amt changed, calling onCustomTip!");
                    onCustomTip(etCustomTipAmt) ;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {// Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {// Fires right before text is changing
            }
        }); */
        // onSetupListView();
        Log.d("DEBUG", "onCreate end");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
           return true;
/*             You can easily load a string array to use in your code using the
                getStringArray() method of the Resources class. The following
                code can be dropped into your Activity class to load an array
                resource defined in your application:*/
           // String[] cRaces = getResources().getStringArray(R.array.races_array);
/*            In your Activity class, you can now implement an OnItemSelectedListener
                to capture when the user selects a specific character race, like this:
                Here we simply react whenever an item in the Spinner is selected. We
                look up the data selected using the getItemAtPosition() method, which,
                in the case of a string array resource, is the string data itself.*/
/*            Spinner cRaceSpinner = (Spinner) findViewById(R.id.spinnerOfCharacterRaces);
            cRaceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> arg0, View arg1,arg2, long arg3) {
                    String strChosenRace = (String) arg0.getItemAtPosition(arg2);
                }
                public void onNothingSelected(AdapterView<?> arg0) {}
            });*/
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCalculateMeal(View view)
    {
        billAmt = Double.parseDouble(etBillAmt.getText().toString());
        noOfGuests = Integer.parseInt(spGuestNumber.getSelectedItem().toString());
        {       /* make sure the user has entered all of the data to allow for
                    calculating meal amts. */
            if (noOfGuests == 0 || billAmt == 0)
            {
                Toast.makeText(this, "Please select the number of guests and "
                        + "enter total bill amount", Toast.LENGTH_SHORT).show();
            } else if (noOfGuests > 0)
            {       /* if there is more than 1 guest, there will be a running tally
                        to make sure the individual meals total up to the total bill
                        amount; therefore make that tally field visible and create a
                        counter to make sure all guest meals are calculated. */
                if (noOfGuests > 1)
                {
                    tvMealDiff.setVisibility(View.VISIBLE);
                    guestCounter = noOfGuests;
                }

                mealCalculator(guestCounter);
            }

        }
        // TODO Determine tip amount and total for guests

        // TODO Add each guest to the array

        onAddGuest(name, total, tip, meal);

        onDisplayGuest(name, total, tip, meal);
    }

    public void mealCalculator(int noOfGuests)
    {
        name = "Nathan";
        billAmt = 14.95;
        total = 17.19;
        tip = 2.24;
        Guest guest = new Guest(name, total, tip, billAmt);
        Intent intCalcMeal = new Intent(this, CalcActivity.class);
        // String strBillAmt = String.valueOf(billAmt);
        intCalcMeal.putExtra("Guest", guest); // pass arbitrary data to launched activity
        startActivityForResult(intCalcMeal, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
        {
            // Extract name value from result extras
            Guest guest = (Guest) data.getSerializableExtra("Guest");

            TextView activityMsg = (TextView) findViewById(R.id.tv_data);
            activityMsg.append(" " + guest.getName() + " " + guest.getTotal() + " total " +
                    guest.getTip() + " tip " + guest.getMeal() +  " meal ");

            // String name = data.getExtras().getString("name");
            // Toast the name to display temporarily on screen
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }
    }
    public void onAddGuest(String name, double meal,
                           double total, double tip)
    {
        // Add item to adapter
        // Guest newGuest = new Guest(name, meal, total, tip);
/*                , .010, .015, .018, .020, .023);*/

    /*     adapter.add(newGuest);
             Or even append an entire new collection
                Fetching some data, data has now returned
                If data was JSON, convert to ArrayList of User objects.
                JSONArray jsonArray =...;
                ArrayList<Guest> newGuests = Guest.fromJson(jsonArray)
                adapter.addAll(newGuests);  */

/*              You can also clear the entire list at any time with:
                adapter.clear();
                */
    }

    public void onDisplayGuest(String name, double total, double tip, double meal)
/*    , double badTip, double mediocreTip, double avgTip,
                    double goodTip, double greatTip)*/
    {


    }

    public void onSetupListView()
    {
        // Construct the data source
        ArrayList<Guest> arrayOfGuests = new ArrayList<Guest>();
        // Create the adapter to convert the array to views
        adapter = new GuestsAdapter(this, arrayOfGuests);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvGuests);
        listView.setAdapter(adapter);
        Log.d("DEBUG", "onSetupListView end");
    }


    public void determineGuests()
    {
        noOfGuests = Integer.parseInt(spGuestNumber.getSelectedItem().toString());

        if (noOfGuests > 1)
            {
                tvMealDiff.setVisibility(View.VISIBLE);
            }

        Log.d("DEBUG", "onItemSelected end");
    }

/*    public void onNothingSelected(AdapterView<?> parent)
    {

    }*/

    private void setupViews()
    {
        etBillAmt = (EditText) findViewById(R.id.etBillAmt);
        spGuestNumber = (Spinner) findViewById(R.id.spGuestNumber);
        // spGuestNumber.setOnItemSelectedListener(this);
        btnSetTaxes = (Button) findViewById(R.id.btnSetTaxes);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        tvMealDiff = (TextView) findViewById(R.id.tvMealDiff);
        lvGuests = (ListView) findViewById(R.id.lvGuests);
        Log.d("DEBUG", "setupViews end");
    }
}