# Para importar el jdl:

- jhipster import-jdl expenses.jdl

# Para correr el proyecto:

- ./mvwn

##### TESTS

Ruta: /src/test/java/com/jhipster/demo/expenses/service/ExpensesServiceTest.java

# Para Ejecutar los Tests
 Tests locales (con Docker corriendo en paralelo)

- ./mvnw test -Dtest=ExpensesServiceTest

## Tests individuales

Test para verificar que se crea correctamente un gasto

- ./mvnw test -Dtest=ExpensesServiceTest#shouldSaveExpenseSuccessfullyWhenValidData

Test para verificar que no se crea el gasto si la descripción es nula

- ./mvnw test -Dtest=ExpensesServiceTest#shouldNotSaveExpenseWhenDescriptionIsNull

Test para verificar que no se crea el gasto si la descripción está vacía

- ./mvnw test -Dtest=ExpensesServiceTest#shouldNotSaveExpenseWhenDescriptionIsEmpty

##### CYPRESS

Ir a la carpeta

- cd /Users/tiago/Documents/GitHub/Ingenieria-de-Software-Aplicada

Ejecutarlo con interfaz grafica:

- npx cypress open

Ejecutarlo en consola:

- npm run e2e:headless -- --spec "src/test/javascript/cypress/e2e/expenses-e2e-custom.cy.ts"

#### DEPLOY EN DOCKER

1. Primero debo compilar el proyecto y generar el JAR

- ./mvnw clean package -DskipTests

2. Creamos el Dockerfile y le hacemos el build:

- docker build -t expenses:latest .

3. Para levantar el compose:

- docker compose up

Para tirarlo:

- docker-compose down

4. Para verificar el estado de los contenedores:

- docker ps

5. Para

### Logstasb

Ver logs de la aplicación desde la app como admin

### Kibana

Acceder a kibana:

- http://localhost:5601/status
