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

## CreateContestService
- [ ] updateContest(): gửi về server 1 json diễn tả contest được tạo. Server update contest được tạo vào database. Ví dụ json như sau: 
    {
    "contestId": 0,
    "creator": 123, // User ID
    "numberOfRegisters": 0,
    "contestName": "Sample Contest",
    "difficulty": 5,
    "timeCreated": "2023-12-29T15:30:00Z", // Example ISO 8601 formatted date-time
    "timeBegin": "2024-01-01T10:00:00Z", // Example ISO 8601 formatted date-time
    "duration": 60
    }

## AchivementService
- [ ] getAchivements(int user_id): gửi về server user_id. Server trả lại một json thể hiện các achivements mà user_id này có. Ví dụ: 
    [
        {
            "name": "First Step",
            "description": "Complete your first lesson.",
            "progress": 1,
            "total": 1,
            "imageResourceId": 101
        },
        {
            "name": "Persistent Learner",
            "description": "Complete 10 lessons.",
            "progress": 7,
            "total": 10,
            "imageResourceId": 102
        },
        {
            "name": "Quiz Master",
            "description": "Score 100% in 5 quizzes.",
            "progress": 3,
            "total": 5,
            "imageResourceId": 103
        }
        // ... additional achievements ...
    ]
 
## NoteService
    [
        {
            "word": "Apple",
            "meaning": "A fruit with red, green, or yellow skin and a sweet taste"
        },
        {
            "word": "Book",
            "meaning": "A set of pages that are bound together and written on"
        },
        {
            "word": "Cat",
            "meaning": "A small domesticated carnivorous mammal with soft fur"
        },
        {
            "word": "Dog",
            "meaning": "A domesticated carnivorous mammal that typically has a long snout"
        }
        // ... additional words ...
    ]
