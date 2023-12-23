# TODO lists

- [x] LoginActivity
- [x] RegisterActivity
- [x] GetPasswordActivity
- [x] HomeActivity
- [x] LearningPathActivity
- [ ] WordMatchingActivity
- [ ] CompleteSentenceActivity
- [ ] MultipleChoiceActivity
- [ ] ConversationActivity   
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