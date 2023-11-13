## Pet project for test task

### Documentation
**LoadingScreen** - the first screen of the app, shows a progress of converting **dictionary.json** file to the SQLite database. When converting progress is succsesful it won't be showed a second time when app's opened.
###
**SearchScreen, DetailsScreen** - screens for searching a word in database and show its definition and synonyms
###
**DefaultLoadDataScreenComponent, DefaultSearchScreenComponent, DefaultDetailsScreenComponent** - components (same as view models) for screens.
###
**DefaultRootComponent** - the root component contains navigation logic
###
**DataSourceImpl** - a class for working with database.
###
**app/src/main/sqldelight/offarkdev/.sq** - a file with SQL queries 
###

**Converting dictionary process** - the app takes **dictionary.json** converts it to list of **WordEntity** using **kotlinx-serialization** and then inserts it to the database.
In json file some words have a few entries with the same "word" text. So on the DetailsScreen you can see a list with multiple different difinitions for the same word.

