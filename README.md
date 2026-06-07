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
- Frontend y Backend de Zoo Connect corriendo en `http://localhost:4200` y `http://localhost:8000` respectivamente

## Ejecutar tests

```bash
# Todos los tests
mvn test

# Tests de un paquete específico
mvn test -Dtest="edu.bo.ucb.miembro.*"

# Un solo test
mvn test -Dtest=RegistroTareaTest
```

Los tests se auto-descubren. Solo crea tu archivo `*Test.java` en tu carpeta y maven lo agarra solo. Tests con dependencias usan `dependsOnGroups` en lugar de orden explícito.

## Estructura

```bash
src/test/java/edu/bo/ucb/
├── ejemplos/              # Tests de ejemplo (no se ejecutan en suite)
├── jesusvelasco/
│   ├── BaseTest.java      # Setup compartido (login, driver, waits)
│   ├── RegistroEspecieTest.java
│   ├── RegistroHabitatTest.java
│   └── RegistroAnimalTest.java
├── luzticona/
├── manueldelgadillo/
│   ├── BaseTest.java
│   ├── Cp31TipoAtencionTest.java
│   ├── Cp32ConsultaClinicaTest.java
│   ├── Cp45CerrarHistorialTest.java
│   └── README.md
└── oscarmenacho/
```

Cada miembro del equipo trabaja en su propia carpeta. Los tests pueden usar `BaseTest` como clase base para login y setup automático de ChromeDriver.

## Tests

### manueldelgadillo (Gestión Clínica — Módulo 3)

Requisitos: ZooConnect en `:4200` / `:8000`, vet `vet@zconnect.com` / `vetABC123!` con `role_id=4`, al menos un animal.

```bash
mvn test -Dtest="edu.bo.ucb.manueldelgadillo.*"
```

- ✅ **Cp31TipoAtencionTest** — CP31: crear tipo de atención
- ✅ **Cp32ConsultaClinicaTest** — CP32: registrar consulta clínica
- ✅ **Cp45CerrarHistorialTest** — CP45: cerrar historial clínico

Instrucciones detalladas: [`src/test/java/edu/bo/ucb/manueldelgadillo/README.md`](src/test/java/edu/bo/ucb/manueldelgadillo/README.md)

### jesusvelasco

- ✅ **RegistroEspecieTest** — crear especie con datos taxonómicos
- ✅ **RegistroHabitatTest** — crear hábitat con datos completos
- ✅ **RegistroAnimalTest** — crear animal con nombre, especie, hábitat y fechas
