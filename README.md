## Pet project for test task

### Documentation
**LoadingScreen** - the first screen of the app, shows a progress of converting **dictionary.json** file to the SQLite database. When converting progress is succsesful it won't be shown a second time when app is opened.
###
**SearchScreen, DetailsScreen** - screens for searching a word in database and showing its definition and synonyms
###
**DefaultLoadDataScreenComponent, DefaultSearchScreenComponent, DefaultDetailsScreenComponent** - components (same as view models) for screens.
###
**DefaultRootComponent** - the root component contains navigation logic
###
**DataSourceImpl** - a class for working with database.
###
**app/src/main/sqldelight/offarkdev/.sq** - a file with SQL queries 
###

**Converting dictionary process** - the app takes **dictionary.json** converts it to a list of **WordEntity** using **kotlinx-serialization** and then inserts it into the database.
In the JSON file some words have a few entries with the same "word" text. So on the DetailsScreen you can see a list with multiple different definitions for the same word.

### Task text
[file with task text](README.md)


