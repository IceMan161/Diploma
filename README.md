# Процедура запуска авто-тестов:

### Необходимое ПО и действия для запуска:

1. Установить "Intellij IDEA" и "Docker".
2. Склонировать [репозиторий](https://github.com/IceMan161/Diploma.git) командой "git clone".
3. Запустить контейнеры "MySQL", "PostgreSQL", "Node-app" в "Docker-compose".
4. Запустить SUT для "MySQL" или "PostgreSQL".

### Для запуска контейнеров "MySQL", "PostgreSQL", "Node-app", ввести в терминал команду:

`docker-compose up -d`

### Запуск SUT и авто-тестов "MySQL":

1. Ввести в терминал команду `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar aqa-shop.jar`.
2. Открыть новую вкладку терминала и ввести команду `./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app`.

**Терминал для запуска второй команды должен быть не Local!!!**

### Запуск SUT и авто-тестов "PostgreSQL":

1. Ввести в терминал команду `java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -jar aqa-shop.jar`.
2. Открыть новую вкладку терминала и ввести команду `./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/postgres`.

**Терминал для запуска второй команды должен быть не Local!!!**

### Для запуска и просмотра отчета по тестированию "Allure", ввести команды:

1. `./gradlew allureReport`
2. `./gradlew allureServe`

### Для остановки работы контейнеров "Docker-Compose", ввести команду:

`docker-compose down`





