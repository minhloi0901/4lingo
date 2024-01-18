from flask import jsonify, request
import requests

def translate_text(text, source_lang="en", target_lang="vi"):
    url = "https://google-api31.p.rapidapi.com/translate"
    headers = {
        "content-type": "application/json",
        "X-RapidAPI-Key": "776026c167msh8aebcf0db7082acp192fcejsn149cde93dd69",
        "X-RapidAPI-Host": "google-api31.p.rapidapi.com"
    }
    payload = {
        "text": text,
        "to": target_lang,
        "from_lang": source_lang
    }

    response = requests.post(url, json=payload, headers=headers)
    if response.status_code == 200:
        return response.json().get("translated", text)
    else:
        return text

def search():
    word = request.args.get('word', '')

    if not word:
        return jsonify({"error": "No word provided"}), 400

    dictionary_api_url = f"https://api.dictionaryapi.dev/api/v2/entries/en/{word}"
    dict_response = requests.get(dictionary_api_url)

    if dict_response.status_code == 200:
        dict_data = dict_response.json()

        # Translate the word itself
        translated_word = translate_text(word)
        for entry in dict_data:
            entry['translated'] = translated_word  # Add translated word at the same level

            for meaning in entry.get("meanings", []):
                for definition in meaning.get("definitions", []):
                    if "example" in definition:
                        translated_example = translate_text(definition["example"])
                        definition["translatedText"] = translated_example

        return jsonify(dict_data)
    else:
        return jsonify({"error": "Word not found or error in external API"}), dict_response.status_code
