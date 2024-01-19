package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.LearningPathService;
import com.example.a4lingo.Services.ProfileService;
import com.example.a4lingo.Services.Utils;
import com.example.a4lingo.adapter.NoteAdapter;
import com.example.a4lingo.item.ProfileItem;
import com.example.a4lingo.item.WordItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends OneTopNavActivity{
    private ProfileItem profileItem;
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

        ProfileService profileService = new ProfileService(getApplicationContext());
        String token = Utils.getToken(getApplicationContext());
        if (token != null){
            profileService.getProfileItem(token, new Utils.Callback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread( () -> {
                        try {
                            System.out.println(response);
                            JSONObject jsonResponse = new JSONObject(response);
                            profileItem = profileService.parseJsonReponse(jsonResponse);
                            updateUI(v);

                        } catch (JSONException e) {
                            System.out.println("Error parsing JSON in NoteActivity");
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON in NoteActivity", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            });

        }

        root.addView(v);
    }

    private void updateUI(View v) {
        EditText userNameTextView = v.findViewById(R.id.userName);
        TextView loginNameTextView = v.findViewById(R.id.loginName);
        EditText emailTextView = v.findViewById(R.id.email);
        TextView passwordTextView = v.findViewById(R.id.password);
        EditText phoneNumberTextView = v.findViewById(R.id.phoneNumber);
        SwitchCompat sound = v.findViewById(R.id.soundToggle);
        SwitchCompat darkMode = v.findViewById(R.id.themeModeToggle);
        SwitchCompat encourageNoti = v.findViewById(R.id.notiToggle);
        SwitchCompat contact = v.findViewById(R.id.contactsToggle);
        SwitchCompat facebook = v.findViewById(R.id.facebookToggle);
        SwitchCompat gmail = v.findViewById(R.id.gmailToggle);
        SwitchCompat smsNoti = v.findViewById(R.id.smsNotiToggle);
        SwitchCompat emailNoti = v.findViewById(R.id.emailNotiToggle);
        TextView notiTime = v.findViewById(R.id.notiTime);

        userNameTextView.setText(profileItem.getUserName());
        loginNameTextView.setText(profileItem.getLoginName());
        emailTextView.setText(profileItem.getEmail());

        int pass_len = profileItem.getPassword().length();
        passwordTextView.setText(generateAsterisks(Math.min(pass_len, 10)));
        phoneNumberTextView.setText(profileItem.getPhoneNumber());

        sound.setChecked(profileItem.isSound());
        darkMode.setChecked(profileItem.isDarkMode());
        encourageNoti.setChecked(profileItem.isEncourageNoti());
        contact.setChecked(profileItem.isContact());
        facebook.setChecked(profileItem.isFacebook());
        gmail.setChecked(profileItem.isGmail());
        smsNoti.setChecked(profileItem.isSmsNoti());
        emailNoti.setChecked(profileItem.isEmailNoti());
        notiTime.setText(profileItem.getNotiTime());
    }

    // Helper method to generate asterisks for password
    private String generateAsterisks(int length) {
        StringBuilder asterisks = new StringBuilder();
        for (int i = 0; i < length; i++) {
            asterisks.append("*");
        }
        return asterisks.toString();
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
                ProfileService profileService = new ProfileService(getApplicationContext());
                String token = Utils.getToken(getApplicationContext());
                if(token != null){
//                    EditText userName = findViewById(R.id.userName);
                    EditText email = findViewById(R.id.email);
                    EditText phone_number = findViewById(R.id.phoneNumber);
                    profileItem.setEmail(email.getText().toString());
                    profileItem.setPhoneNumber(phone_number.getText().toString());
                    profileService.saveProfile(token, profileItem, new Utils.Callback() {
                        @Override
                        public void onSuccess(String response) {
//                            System.out.println(response);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                // Finish
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
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.themeModeToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.notiToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.contactsToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.facebookToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        switchCompat = findViewById(R.id.gmailToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchCompat = findViewById(R.id.smsNotiToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
                }
            }
        });


        switchCompat = findViewById(R.id.emailNotiToggle);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    // if isChecked
//                    Toast.makeText(getApplicationContext(), "Switch On", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Switch Off", Toast.LENGTH_SHORT).show();
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


        //-------------------------------------------------------------

//        EditText userName = findViewById(R.id.userName);
//        EditText email = findViewById(R.id.email);
//        EditText phone_number = findViewById(R.id.phoneNumber);

        TextView password = findViewById(R.id.password);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}
