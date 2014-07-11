package com.app.dianna.tipsy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;

public class CalcActivity extends Activity
{
    private String guestName;
    private double guestMeal;
    private double guestTotal;
    private double guestTip;
    NumberFormat nfc = NumberFormat.getCurrencyInstance();
    NumberFormat nfp = NumberFormat.getPercentInstance();
    private EditText etGuestName;
    private EditText etGuestMealAmt;
    private Spinner spService;
    private Button btnAdd;
    private Button btnDone;
    private TextView tvMealTotal;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        setupViews();

        Toast.makeText(this, "Finished onCreate", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "onCreate end");
    }

    public void onAddClick(View view)
    {
        String strGuestMeal = etGuestMealAmt.getText().toString();
        Log.d("DEBUG", strGuestMeal);
        Toast.makeText(this, strGuestMeal, Toast.LENGTH_SHORT).show();

        guestMeal = Double.parseDouble(strGuestMeal);
        Log.d("DEBUG", String.valueOf(guestMeal));
        Toast.makeText(this, String.valueOf(guestMeal), Toast.LENGTH_SHORT).show();

        guestTotal = (guestTotal + guestMeal);
        Log.d("DEBUG", String.valueOf(guestTotal));
        Toast.makeText(this, "GUEST TOTAL= " + String.valueOf(guestTotal),
                Toast.LENGTH_LONG).show();

        tvMealTotal.setText(String.valueOf(guestTotal));
        tvMealTotal.setVisibility(View.VISIBLE);
        etGuestMealAmt.setText("");

        Toast.makeText(this, "Finished onAddClick", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "onAddClick end");
    }

    private void setupViews()
    {
        etGuestName = (EditText) findViewById(R.id.etGuestName);
        etGuestMealAmt = (EditText) findViewById(R.id.etGuestMealAmt);
        spService = (Spinner) findViewById(R.id.spService);
        String selectedTip = getResources().getStringArray(R.array.tips_array)[spService
                .getSelectedItemPosition()];
        Toast.makeText(this, "selectedTip: " + selectedTip, Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "selectedTip=" + selectedTip);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDone = (Button) findViewById(R.id.btnDone);
        tvMealTotal = (TextView) findViewById(R.id.tvMealTotal);
        listView = (ListView) findViewById(R.id.listView);

        populateData();
        Toast.makeText(this, "Finished setupViews", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "setupViews end");
    }

    private void populateData()
    {
        Toast.makeText(this, "Starting to populateData()", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "populateData start");

        Intent intent = getIntent();
        Guest guest = (Guest) intent.getSerializableExtra("Guest");
        guestName = guest.getName();
        etGuestName.setText(guestName);
        guestMeal = guest.getMeal();
        etGuestMealAmt.setText(String.valueOf(guestMeal));

        // if guest tip exists, then set the spinner to the currently used tip percent
        guestTip = guest.getTip();
        double percentAmt = 0;
        if (guestTip != 0)
        {
            percentAmt = (guestTip / guestMeal);
        }

        Toast.makeText(this, "bill amt = " + guestMeal, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,
                "tip percent=" + String.valueOf(nfp.format(percentAmt))
                        + "; tip amt-" + String.valueOf(nfc.format(guestTip ))
                        + " / meal amt-" + String.valueOf(nfc.format(guestMeal)),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calc, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDoneClick(View view)
    {       // Retrieve all the guest data, Pass guest object back as a result
        guestMeal = Double.parseDouble(tvMealTotal.getText().toString());
        String selectedTip = getResources().getStringArray(R.array.tips_array)[spService
                .getSelectedItemPosition()];
        double tipPercent = Double.parseDouble(selectedTip);
        guestTip = (guestMeal * tipPercent);
        guestTotal = (guestTip + guestMeal);

        Guest guest = new Guest(guestName, guestTotal, guestTip, guestMeal);

        // Prepare data intent
        Intent data = new Intent();
        data.putExtra("Guest", guest);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        Toast.makeText(this, "Finished onDoneClick", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "onDoneClick end");
        finish(); // closes the activity, pass data to parent
    }

    // get the index of the value stored in spinner
    private int getIndex(Spinner spinner, String myString)
    {
        Log.d("DEBUG", "getIndex");
        int index = 0;

        for (int i=0;i<spinner.getCount();i++)
        {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString))
            {
                index = i;
                i=spinner.getCount();//will stop the loop, kind of break, by making condition false
            }
        }
        return index;
    }

    // Setting spinner item based on value (rather than item position):
    public void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break; // terminate loop
            }
        }
        spinner.setSelection(index);
    }

    public void onExcessText()
    {
        // String inReplyTo = getIntent().getStringExtra("in_reply_to");
        // int code = getIntent().getIntExtra("code", 0);

        /*            String strPercentAmt = String.valueOf(percentAmt);
            strPercentAmt.setText(R.array.tips_array[1]);
            OR MAYBE R.string.percent_Amt ??? */
/*                getResources().putStringArray(R.array.tips_array)[spService
                        .getSelectedItemPosition()];  percent_Amt*/
/*            String selectedVal = getResources().getStringArray(R.array.tips_array)[spService
                    .getSelectedItemPosition()];*/

/*            spService = (Spinner) findViewById(R.id.spService);
            if (size != null && !size.isEmpty())
            {
                spService.setSelection(getIndex(spService, size));
            }*/

        // String value = spinner.getSelectedItem().toString();
        // setSpinnerToValue(spinner, value);
        // guestTotal = guest.getTotal();
        // tvMealTotal.setText(nfc.format(guestTotal));

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
/*        String selectedVal = getResources().getStringArray(R.array.tips_array)[spService
                .getSelectedItemPosition()];
        spService.setText(nfc.format(guestTip));*/
    }
}
