# TODO lists

- [x] LoginActivity
- [x] RegisterActivity
- [x] GetPasswordActivity
- [x] HomeActivity
- [x] LearningPathActivity
- [ ] WordMatchingActivity
- [x] CompleteSentenceActivity
- [x] MultipleChoiceActivity
- [x] ConversationActivity   
- [x] DictionaryActivity
- [x] MistakeListActivity
- [x] NoteActivity
- [x] CompleteLessonActivity
- [x] ReviewActivity
- [x] LeaderBoardActivity
- [x] ContestActivity
- [x] CreateContestActivity
- [ ] CompleteContestActivity
- [x] ProfileActivity
- [ ] OverralActivity
- [x] AchievementActivity
- [ ] CommunityActivity
- [ ] CreateCommunityActivity
- [x] ShareActivity
- [ ] LicenseActivity
- [ ] ThanksActivity

### SERVICE API TODO
## LearningPathService
- [ ] getLearningPathQuestions (): return list of questions 
- [ ] getLearningPathAnswers (): return list of answers
- [ ] getLearningPath (list of answers) : return starting lesson_id

## ReviewService
- [ ] updateReview (int lesson_id, int ratingInt: số sao, String review: đánh giá): add review with lesson_id to database

## ContestService
- [ ] getContest (contest_id) : return json with all attributes of a contest in database
- [ ] getPreviousContest () : return a list of contest_id that happened
- [ ] getCurrentContest () : return a list of contest_id that is happening
- [ ] getCommingContest () : return a list of contest_id is comming

## LessonCompletedService
- [ ] updateLessonResult (user_id, lesson_id, score) : update this result to table lesson_completion

## ConversationExerciseService
- [ ] getCurrentConversationExercise(String userID): get current conversation exercise. 
    API returned format: 
    {
        "npc":"npc message",
        "user": user messages (a list of responses, the correct answer is the first message)
        "npc"...
        "user"...
    }

## MultipleChoiceService
- [ ] getMultipleChoiceQuestions(String userID): get current multiple choice questions.
    API returned format:
    [
        {
            "question": a question,
            "choices: [list of choices],
            "correct_answer": correctAnswerIndex
        },
        ...
    ]

## SentenceTranslationService
- [ ] getTranslationQuestions(String userID): get current translation questions.
    API returned format:
    [
        {
            "origin": the original sentence needs to be translated,
            "destination: the correct translation,
            "possible_words": [list of possible words]
        },
        ...
    ]

## RegisterService
- [ ] registerUser(String userName, String email, String password): register user with specified username, email and password
    Return registration results as integer: code error or success