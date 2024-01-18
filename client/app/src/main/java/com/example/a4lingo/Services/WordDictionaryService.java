//package com.example.a4lingo.Services;
//
//import com.example.a4lingo.item.WordItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class WordDictionaryService {
//    public String getSpellingPronunciation(String word) {
//        return "/ˈlɪŋɡəʊ/";
//    }
//
//    public List<WordItem> getWordItemList(String word) {
//        List<WordItem> wordItemList = new ArrayList<>();
//        wordItemList.add(new WordItem(word, "1. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích."));
//        wordItemList.add(new WordItem(word, "2. ngoại ngữ", "\u2022" + " If you live abroad it helps to know the local lingo.", "Nếu bạn sống ở nước ngoài, việc biết biệt ngữ địa phương sẽ giúp ích."));
//        return wordItemList;
//    }
//}
//

package com.example.a4lingo.Services;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.a4lingo.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class WordDictionaryService {
    private Context context;

    public WordDictionaryService(Context context) {
        this.context = context;
    }

    public void searchWord(String word, Utils.Callback callback) {
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("word", word);
        } catch (JSONException e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(() ->
                    Toast.makeText(context, "Error creating JSON", Toast.LENGTH_SHORT).show());
            return;
        }

        DataManager dataManager = new DataManager();
        dataManager.sendRequest("POST", "dictionary/search", jsonParam, Utils.createCallback(context, callback));
    }
}
