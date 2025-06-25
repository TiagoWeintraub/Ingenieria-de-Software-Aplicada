# Para importar el jdl:

- jhipster import-jdl expenses.jdl

# Para correr el proyecto:

- ./mvwn

##### TESTS

Ruta: /src/test/java/com/jhipster/demo/expenses/service/ExpensesServiceTest.java

# Para Ejecutar los Tests

- ./mvnw test -Dtest=ExpensesServiceTest

## O de forma individual

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
