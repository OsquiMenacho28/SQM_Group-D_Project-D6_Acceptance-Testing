# SQM Group D — Project D6: Acceptance Testing

Pruebas de aceptación automatizadas con Selenium WebDriver + TestNG para el proyecto **Zoo Connect Web**.

## Stack

- **Java 8+**
- **Maven** — gestión de dependencias y ejecución
- **TestNG 7.10.2** — framework de pruebas
- **Selenium 3.141.59** — automatización de navegador
- **WebDriverManager 5.5.2** — gestión automática de ChromeDriver
- **Chrome** — navegador para ejecución

## Requisitos

- Java 8 o superior
- Maven 3+
- Google Chrome instalado

## Ejecutar tests

```bash
# Todos los tests (usa testng.xml)
mvn test

# Tests de un miembro específico
mvn -Dtest="edu.bo.ucb.jesusvelasco.*" test

# Un solo test
mvn -Dtest=RegistroAnimalTest test
```

El archivo `testng.xml` define qué tests se ejecutan con `mvn test`. Por defecto:

1. **jesusvelasco** — 3 tests en orden: Especie → Hábitat → Animal
2. **Otros tests** — cualquier clase en `edu.bo.ucb.*` excepto `ejemplos/` y `jesusvelasco/`

Para excluir un test del `mvn test` general, agregar su paquete en `<exclude>` dentro de `testng.xml`.

## Estructura

```
src/test/java/edu/bo/ucb/
├── ejemplos/              # Tests de ejemplo (no se ejecutan)
├── jesusvelasco/          # Tests de Jesús Velasco
│   ├── BaseTest.java      # Setup compartido (login, driver, waits)
│   ├── RegistroEspecieTest.java
│   ├── RegistroHabitatTest.java
│   └── RegistroAnimalTest.java
├── luzticona/             # Tests de integrante
├── manueldelgadillo/      # Tests de integrante
├── manueljimenez/         # Tests de integrante
└── oscarmenacho/          # Tests de integrante
```

Cada miembro crea sus tests dentro de su carpeta. Los tests deben extender `BaseTest` si necesitan login y setup de ChromeDriver.

## Tests que pasan actualmente

- ✅ **RegistroEspecieTest** — crear especie con datos taxonómicos
- ✅ **RegistroHabitatTest** — crear hábitat con datos completos
- ✅ **RegistroAnimalTest** — crear animal con nombre, especie, hábitat y fechas
