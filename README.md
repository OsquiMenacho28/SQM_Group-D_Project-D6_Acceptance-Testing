# SQM Group D — Project D6: Acceptance Testing

Pruebas de aceptación automatizadas con Selenium WebDriver + TestNG para el proyecto **Zoo Connect Web**.

## Stack

- **Java 17+**
- **Maven** — gestión de dependencias y ejecución
- **TestNG 7.10.2** — framework de pruebas
- **Selenium 3.141.59** — automatización de navegador
- **WebDriverManager 5.5.2** — gestión automática de ChromeDriver
- **Chrome** — navegador para ejecución

## Requisitos

- Java 17 o superior
- Maven 3+
- Google Chrome instalado
- Backend de Zoo Connect corriendo en `http://localhost:4200`

## Ejecutar tests

```bash
# Todos los tests (usa testng.xml)
mvn test

# Tests de un paquete específico
mvn -Dtest="edu.bo.ucb.miembro.*" test

# Un solo test
mvn -Dtest=RegistroAnimalTest test
```

El archivo `testng.xml` define qué tests se ejecutan con `mvn test`:

1. **Paquetes con orden explícito** — los tests que requieren orden específico se listan en `<classes>`
2. **Otros paquetes** — cualquier clase en `edu.bo.ucb.*` excepto `ejemplos/`

Para excluir un paquete del `mvn test` general, agregarlo en `<exclude>` dentro de `testng.xml`.

## Estructura

```
src/test/java/edu/bo/ucb/
├── ejemplos/              # Tests de ejemplo (no se ejecutan en suite)
├── jesusvelasco/
│   ├── BaseTest.java      # Setup compartido (login, driver, waits)
│   ├── RegistroEspecieTest.java
│   ├── RegistroHabitatTest.java
│   └── RegistroAnimalTest.java
├── luzticona/
├── manueldelgadillo/
├── manueljimenez/
└── oscarmenacho/
```

Cada miembro del equipo trabaja en su propia carpeta. Los tests pueden usar `BaseTest` como clase base para login y setup automático de ChromeDriver.

## Tests

- ✅ **RegistroEspecieTest** — crear especie con datos taxonómicos
- ✅ **RegistroHabitatTest** — crear hábitat con datos completos
- ✅ **RegistroAnimalTest** — crear animal con nombre, especie, hábitat y fechas
