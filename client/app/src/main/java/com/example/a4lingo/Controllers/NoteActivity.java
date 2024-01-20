package com.example.a4lingo.Controllers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4lingo.R;
import com.example.a4lingo.Services.NoteService;
import com.example.a4lingo.Services.Utils;
import com.example.a4lingo.adapter.NoteAdapter;
import com.example.a4lingo.item.WordItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends MainActivity{
    @Override
    protected void renderLayout(){
        super.renderLayout();
        LinearLayout root = (LinearLayout) findViewById(R.id.content);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.activity_note, root, false);

        renderAnInstance(v);

        root.addView(v);
    }

    @Override
    protected void renderNavigation(){
        super.renderNavigation();
    }

    private void renderAnInstance(View v) {
        // Create a list of WordItems
        NoteService noteService = new NoteService(getApplicationContext());

        String token = Utils.getToken(getApplicationContext());
        if(token != null){
            noteService.getAllWords(token, new Utils.Callback() {
                @Override
                public void onSuccess(String response) {
                    runOnUiThread( () -> {
                        try {
                            System.out.println(response);
                            JSONArray jsonResponse = new JSONArray(response);

                            List<WordItem> wordItemList = noteService.parseJsonResponse(jsonResponse);

                            // Find the RecyclerView
                            RecyclerView recyclerView = v.findViewById(R.id.recyclerViewNotes);

                            // Create and set the adapter
                            NoteAdapter wordAdapter = new NoteAdapter(getApplicationContext(), wordItemList);
                            recyclerView.setAdapter(wordAdapter);

                            // Set the layout manager
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
    }
}
