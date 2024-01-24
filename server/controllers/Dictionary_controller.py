from flask import jsonify, request
import requests
from config.config import config

def translate_text(text, source_lang="en", target_lang="vi"):
    url = "https://google-api31.p.rapidapi.com/translate"
    headers = {
        "content-type": "application/json",
        "X-RapidAPI-Key": config['GG_API_KEY'],
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

def translate_example_sentences(example_sentences):
    # Concatenate example sentences with '/'
    sentences_to_translate = '/'.join(example_sentences)

    # Call the translation API
    translated_text = translate_text(sentences_to_translate)

    # Split the translated text by lines
    translated_sentences = translated_text.split('/')

    return translated_sentences

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

        # Collect example sentences
        example_sentences = []
        for entry in dict_data:
            for meaning in entry.get("meanings", []):
                for definition in meaning.get("definitions", []):
                    if "example" in definition:
                        example_sentences.append(definition["example"])

        # Translate all example sentences in a single API call
        translated_sentences = translate_example_sentences(example_sentences)

        # Update the dictionary data with translations
        example_index = 0
        for entry in dict_data:
            entry['translated'] = translated_word  # Add translated word at the same level

            for meaning in entry.get("meanings", []):
                for definition in meaning.get("definitions", []):
                    if "example" in definition:
                        # Update the translatedText for each example
                        definition["translatedText"] = translated_sentences[example_index]
                        example_index += 1

        return jsonify(reformat_data(dict_data[0])), 200
    else:
        return jsonify({"error": "Word not found or error in external API"}), dict_response.status_code

def reformat_data(dict_data):
    converted_data = {
    "word": dict_data["word"],
    "phonetics": [
        {
            "audio": phonetic.get("audio", ""),
            "text": phonetic.get("text", "")
        } for phonetic in dict_data["phonetics"] if phonetic.get("audio", "") != "" and phonetic.get("text", "") != ""
    ], 
    "meanings": [
        {
            "definition": definition["definition"],
            "example": definition.get("example"),
            "translatedText": definition.get("translatedText")
        } for meaning in dict_data["meanings"]
        for definition in meaning["definitions"] if definition.get("translatedText", "") != "" and definition.get("example", "") != ""
    ]
    }
    
    return converted_data