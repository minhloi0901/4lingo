package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.CreateContestService;
import com.example.a4lingo.item.ContestItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateContestActivity extends OneTopNavActivity {
    String[] privacyOptions = new String[] {"Public", "Private"};
    private Spinner spinnerPrivacy;

    int user_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Tổ chức cuộc thi", null);
    }

    @Override
    protected void renderLayout(String pageTittle, String rightButtonText) {
        super.renderLayout(pageTittle, rightButtonText);

        super.renderLayout(pageTittle, rightButtonText);
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_create_contest, root, false);
        root.addView(v);
        getIntentData();
        setupSpinner();
    }

    private void getIntentData() {
        // Get the intent that started this activity
        Intent intent = getIntent();

        // Check if the intent has extra data with the tag USER_ID
        if (intent.hasExtra("USER_ID")) {
            // Retrieve the USER_ID value and store it in user_id
            user_id = intent.getIntExtra("USER_ID", 0); // 0 is a default value
        } else {
            // Handle the case where USER_ID is not provided
            // For example, you might set a default value or show an error message
            user_id = 0; // Setting default value as 0
            // Optionally, you can show an error message or take other appropriate actions
        }
    }

    private void setupSpinner() {
        spinnerPrivacy = findViewById(R.id.mySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                privacyOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrivacy.setAdapter(adapter);
    }

    @Override
    protected void renderNavigation() {
        super.renderNavigation();

        Button btn = findViewById(R.id.createContestcontinueButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if all EditText fields are filled
                if (isAnyFieldEmpty()) {
                    Toast.makeText(CreateContestActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check date and time format
                EditText editTextDate = findViewById(R.id.createContestDate);
                EditText editTextTime = findViewById(R.id.createContestTime);
                if (!isValidDateTimeFormat(editTextDate.getText().toString(), editTextTime.getText().toString())) {
                    Toast.makeText(CreateContestActivity.this, "Invalid date or time format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get the selected item from Spinner
                String selectedPrivacy = spinnerPrivacy.getSelectedItem().toString();

                EditText contestNameEditText = findViewById(R.id.createContestName);
                String contestName = contestNameEditText.getText().toString();

                EditText contestDateEditText = findViewById(R.id.createContestDate);
                String contestDate = contestDateEditText.getText().toString();

                EditText contestTimeEditText = findViewById(R.id.createContestTime);
                String contestTime = contestTimeEditText.getText().toString();

                EditText contestDurationEditText = findViewById(R.id.createContestDuration);
                String contestDurationStr = contestDurationEditText.getText().toString();

                EditText contestMinRatingEditText = findViewById(R.id.createContestMinRating);
                String contestMinRatingStr = contestMinRatingEditText.getText().toString();

                // Convert duration and minimum rating to integer
                int contestDuration, contestMinRating;
                try {
                    contestDuration = Integer.parseInt(contestDurationStr);
                    contestMinRating = Integer.parseInt(contestMinRatingStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(CreateContestActivity.this, "Wrong format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parsing the date and time for timeBegin
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date timeBegin;
                try {
                    timeBegin = dateTimeFormat.parse(contestDate + " " + contestTime);
                } catch (ParseException e) {
                    Toast.makeText(CreateContestActivity.this, "Invalid date or time format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Use the gathered data to create ContestItem object
                Date timeCreated = new Date(); // Current date and time for timeCreated

                // TODO: SEND data to server
                // BEGIN
                ContestItem theContest = new ContestItem(0, user_id, 0, contestName, contestMinRating, timeCreated, timeBegin, contestDuration);
                CreateContestService createContestService = new CreateContestService();
                createContestService.updateContest(theContest);
                // END

                Intent intent = new Intent(CreateContestActivity.this, HomeActivity.class);
                intent.putExtra("USER_ID", user_id);
                startActivity(intent);
            }
        });
    }

    private boolean isAnyFieldEmpty() {
        EditText contestName = findViewById(R.id.createContestName);
        EditText contestDate = findViewById(R.id.createContestDate);
        EditText contestTime = findViewById(R.id.createContestTime);
        EditText contestDuration = findViewById(R.id.createContestDuration);
        EditText contestMinRating = findViewById(R.id.createContestMinRating);

        return contestName.getText().toString().isEmpty() ||
                contestDate.getText().toString().isEmpty() ||
                contestTime.getText().toString().isEmpty() ||
                contestDuration.getText().toString().isEmpty() ||
                contestMinRating.getText().toString().isEmpty();
    }

    private boolean isValidDateTimeFormat(String date, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        // Set lenient to false for strict date parsing
        dateFormat.setLenient(false);
        timeFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(date);
            Date parsedTime = timeFormat.parse(time);

            // Check if the date is valid
            if (parsedDate == null || parsedTime == null) {
                return false;
            }

        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}