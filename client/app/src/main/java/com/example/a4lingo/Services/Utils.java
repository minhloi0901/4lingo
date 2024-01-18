package com.example.a4lingo.Services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a4lingo.Controllers.CompleteLessonActivity;
import com.example.a4lingo.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Utils {
    public static BottomSheetDialog showBottomSheet(Context context, boolean isCorrect, String message) {
        // Inflate the bottom sheet layout
        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_item, null);

        // Get views from the layout
        ImageView resultImageView = bottomSheetView.findViewById(R.id.resultState);
        TextView resultMessageTextView = bottomSheetView.findViewById(R.id.resultMessage);

        // Set views based on correctness
        resultImageView.setImageResource(isCorrect ? R.drawable.ic_correct : R.drawable.ic_incorrect);
        resultMessageTextView.setText(message);

        // Create and show the bottom sheet dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        return bottomSheetDialog;
    }


    public static <T> boolean checkCompletion(int questionIndex, List<T> questions, long startTimeMillis, Context context, int correctCount) {
        if (questionIndex == questions.size()) {
            long endTimeMillis = SystemClock.elapsedRealtime();  // Record the end time
            long elapsedTimeSeconds = (endTimeMillis - startTimeMillis) / 1000;

            Intent intent = new Intent(context, CompleteLessonActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("SCORE", 100);
            intent.putExtra("TIME", elapsedTimeSeconds);
            intent.putExtra("ACCURACY", correctCount  * 100.0f / questions.size());
            intent.putExtra("LESSON_ID", 001);
            context.startActivity(intent);
            // All questions are completed

            return true;
        }
        return false;
    }

    public static <T> void swap(T[] array, int index1, int index2) {
        if (index1 >= 0 && index1 < array.length && index2 >= 0 && index2 < array.length) {
            T temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        } else {
            // Handle invalid indices if needed
            System.out.println("Invalid indices");
        }
    }


    public interface Callback {
        void onSuccess(String response);
        void onFailure(String error);
    }

    public static Callback createCallback(Context context, Callback callback) {
        return new Callback() {
            @Override
            public void onSuccess(String response) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        callback.onSuccess(jsonResponse.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onFailure(error);
                });
            }
        };
    }
}
