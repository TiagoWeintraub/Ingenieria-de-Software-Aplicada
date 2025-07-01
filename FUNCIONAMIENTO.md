# RUTA DEL PROYECTO

- cd /Users/tiago/Documents/GitHub/Final-Ing-Sof-Aplicada/Ingenieria-de-Software-Aplicada/


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

- cd /Users/tiago/Documents/GitHub/Final-Ing-Sof-Aplicada/Ingenieria-de-Software-Aplicada/

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


### JENKINS

Primero intente en Mac con el Jenkinsfile generado por el comando: (No funcionó por incompatibilidades de la arq del procesador ARM con la imagen de docker para sistemas X86)

- Jhipster ci-cd     Sumado a las lineas de la guía de la cátedra

Después en Mac con mi propio contenedor jenkins arm: (No funcionó por falta de ram, se me cancelaban procesos y tuve otras incompatibilidades)

- docker build -t jenkins-arm-java17 .
- docker run -d --name jenkins -p 8090:8080 -p 50000:50000 jenkins-arm-java17

Después en Windows:

- docker container run -d --name jenkins -p 8090:8080 -p 50000:50000 jenkins/jenkins


Pasos: 
1. docker exec -it jenkins bash
2. cd /var/jenkins_home/secrets/
3. cat initialAdminPassword
La pass es: b66bc0866fdf471c8caa3db878ded648

Ingresar a localhost:8090 y colocar la password.

4. Configuramos la tarea y ejecutamos

# Imagen en Dockerhub: https://hub.docker.com/repositories/tiagoweintraub
# En buscador: https://hub.docker.com/search?q=tiagoweintraub
# Ahora cualquier persona puede descargar y ejecutar tu aplicación con:
1. Descargar la imagen

- docker pull tiagoweintraub/isa-final:latest

2. Ejecutar la aplicación

- docker run -p 8080:8080 tiagoweintraub/isa-final:latest