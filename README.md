# NBA_APP

The NBA_APP app is a convenient service for viewing various basketball information.

The service provides access to information about various games, players, and teams. 
It uses the BASKETBALL-API, a special API that provides access to information.

To use it you need to create your apikey.properties file, where your apikey will be stored.
You may get it from https://dashboard.api-football.com/ .

Also you need to have your signin.properties file,  which contains passwords to your release and debug builds.
You can ask contact our developers to ask for passwords.

**Stack**:
- Jetpack Compose
- Coroutines + Flow
- Compose destinations(raamcosta)
- Koin
- Firebase
- Coil
- Retrofit + Retrofit cache
- Gson
- Room
- JUnit
- Mockito
- Koin-test
- Clean architecture
- MVI

**Project structure**:
- data(a layer working with the database, API, containing the corresponding models, repository implementations)
- domain(a layer of business logic containing repository interfaces, corresponding models, and "interactors (referred to as managers in the project)")
- di
- presentation(a layer with all screens, common UI elements, and viewmodels)

**Screens**:
- EnterScreen(app enter screen)
- ShowingScreen(screen which shows all players, games, teams)
- Followed screen(screen which shows followed elements in all categories)
- GameDetailsScreen(screen with details about certain game)
- TeamDetailsScreen(screen with details about certain team)
- PlayerDetailsScreen(screen with details about certain player)

**SCreenshots**

![1](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/Screenshot%202025-06-15%20000235.jpg)

![2](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/Screenshot%202025-06-15%20000235.jpg)

![3](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/Screenshot%202025-06-15%20000328.jpg)

![5](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/Screenshot%202025-06-15%20000340.jpg)

![6](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/Screenshot%202025-06-15%20000357.jpg)

![7](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/Screenshot%202025-06-15%20000415.jpg)

![8](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/Screenshot%202025-06-15%20000529.jpg)

![9](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/login%20screen.jpg)

![10](https://github.com/AlenniBoris/NBA_APP/blob/main/screenshots/main%20%20screen%20games.jpg)

