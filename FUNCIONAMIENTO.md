# Para importar el jdl:

- jhipster import-jdl expenses.jdl

# Para correr el proyecto:

- ./mvwn

##### TESTS

Ruta: /src/test/java/com/jhipster/demo/expenses/service/ExpensesServiceTest.java

# Para Ejecutar los Tests

- mvnw test -Dtest=ExpensesServiceTest

## O de forma individual

- ./mvnw test -Dtest=ExpensesServiceTest#shouldSaveExpenseSuccessfullyWhenValidData
- ./mvnw test -Dtest=ExpensesServiceTest#shouldNotSaveExpenseWhenDescriptionIsNull
- ./mvnw test -Dtest=ExpensesServiceTest#shouldNotSaveExpenseWhenDescriptionIsEmpty
