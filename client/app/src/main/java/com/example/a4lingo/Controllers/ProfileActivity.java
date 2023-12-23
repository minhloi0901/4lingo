package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.LearningPathService;
import com.example.a4lingo.Services.ProfileService;

import java.util.ArrayList;

public class ProfileActivity extends OneTopNavActivity{
    private ProfileService profileService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderLayout("Tài khoản", "LƯU");
    }

    @Override
    protected void renderLayout(String pageTittle, String rightButtonText){
        super.renderLayout(pageTittle, rightButtonText);

        LinearLayout root = (LinearLayout) findViewById(R.id.content);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_profile, root, false);

//        updateUI();

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();

        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ask if changed but didn't save
                finish();
            }
        });

        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store to database

                // Finish
                finish();
            }
        });

        findViewById(R.id.changeAvatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ChangeAvatarActivity.class);
//                startActivity(intent);
            }
        });


        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.overallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OverallActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.determineLeaningPathButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LearningPathActivity.class);
                LearningPathService learningPathService = new LearningPathService();
                intent.putExtra("QUESTION_ID", 0);
                intent.putStringArrayListExtra("QUESTION", learningPathService.getLearningPathQuestions());
                intent.putExtra("ANSWER", learningPathService.getLearningPathAnswers());
                ArrayList<Integer> selected_answer = new ArrayList<>();
                intent.putIntegerArrayListExtra("SELECTED", selected_answer);
                startActivity(intent);
            }
        });

        findViewById(R.id.achievementsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AchievementActivity.class);
                startActivity(intent);
            }
        });

        SwitchCompat switchCompat = findViewById(R.id.soundToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.themeModeToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.notiToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.contactsToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.facebookToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        switchCompat = findViewById(R.id.gmailToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.smsNotiToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        switchCompat = findViewById(R.id.emailNotiToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        findViewById(R.id.notiTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });



        // ------------------*******************************
        findViewById(R.id.joinCommunicationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommunicationActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.createCommunicationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateCommunicationActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.rulesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RuleActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.gratefulButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThanksActivity.class);
                startActivity(intent);
            }
        });
    }
}
