# Pruebas de aceptación — Manuel Delgadillo (Gestión Clínica)

Módulo 3 — Casos **CP31, CP32 y CP45** con Selenium + TestNG.

## Requisitos previos

1. **Java 17+** y **Maven 3+**
2. **Google Chrome** instalado
3. **ZooConnect en ejecución:**
   - Frontend: `http://localhost:4200`
   - Backend: `http://localhost:8000`
   - En el repo principal: `docker compose up -d`
4. Usuario veterinario en la base de datos:
   - Email: `vet@zconnect.com`
   - Contraseña: `vetABC123!`
   - Rol: `role_id = 4`
5. Al menos **un animal** registrado (necesario para CP32 y CP45)

## Estructura

```text
manueldelgadillo/
├── BaseTest.java              # Login vet, navegación y helpers PrimeNG
├── Cp31TipoAtencionTest.java  # CP31 — Crear tipo de atención
├── Cp32ConsultaClinicaTest.java  # CP32 — Registrar consulta clínica
├── Cp45CerrarHistorialTest.java  # CP45 — Cerrar historial clínico
└── README.md
```

## Ejecutar (desde la raíz del repositorio)

```powershell
cd SQM_Group-D_Project-D6_Acceptance-Testing

# Las tres pruebas del paquete
mvn test -Dtest="edu.bo.ucb.manueldelgadillo.*"

# Una por una
mvn test -Dtest=edu.bo.ucb.manueldelgadillo.Cp31TipoAtencionTest
mvn test -Dtest=edu.bo.ucb.manueldelgadillo.Cp32ConsultaClinicaTest
mvn test -Dtest=edu.bo.ucb.manueldelgadillo.Cp45CerrarHistorialTest
```

### URLs y credenciales (opcional)

```powershell
mvn test -Dtest=edu.bo.ucb.manueldelgadillo.Cp31TipoAtencionTest `
  -Dzooconnect.baseUrl=http://localhost:4200 `
  -Dzooconnect.apiUrl=http://localhost:8000/zooconnect `
  -Dzooconnect.vetEmail=vet@zconnect.com `
  -Dzooconnect.vetPassword=vetABC123!
```

## Resultado esperado

Cada prueba debe reportar **Tests run: 1, Failures: 0** y `BUILD SUCCESS`.

## Problemas frecuentes

| Síntoma | Solución |
|---------|----------|
| No aparece "Panel Veterinario" | Verificar `role_id = 4` para el veterinario en BD |
| Login no avanza de `/login` | El formulario usa `@defer`; `BaseTest` hace hover en el header |
| Falla al abrir `p-select` | Esperar el formulario en `/vet/historiales/crear` |
| "Diagnósticos Iniciales" no encontrado | Es un `p-accordion-header`, no un `button` |
