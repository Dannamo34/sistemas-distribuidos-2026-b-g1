
# CP4 — Security CI

## 1. Objetivo

Implementar comprobaciones automatizadas de seguridad para el proyecto Futbolix mediante GitHub Actions. El proceso permite detectar secretos expuestos, vulnerabilidades en dependencias y errores de configuración. También incorpora una prueba negativa para verificar que un endpoint administrativo rechace solicitudes sin autenticación.

## 2. Archivos de la entrega

La carpeta `04-security-ci` contiene:

- `security.yml`: workflow de GitHub Actions para ejecutar los controles de seguridad.
- `negative-auth-test.ps1`: script de PowerShell para comprobar el rechazo de solicitudes sin autenticación.
- `HOW-TO-RUN.md`: documentación para configurar y ejecutar las comprobaciones.

## 3. Controles de seguridad automatizados

El workflow incorpora los siguientes controles:

### 3.1. Detección de secretos

Se utiliza Gitleaks para identificar posibles credenciales, tokens, claves y otros secretos expuestos accidentalmente en el repositorio.

### 3.2. Análisis de vulnerabilidades y configuración

Se utiliza Trivy para analizar los archivos del repositorio y detectar:

- Vulnerabilidades en dependencias identificables.
- Posibles secretos expuestos.
- Configuraciones inseguras.

El análisis de dependencias depende de que los manifiestos y archivos de dependencias estén disponibles en el repositorio analizado.

### 3.3. Prueba negativa de autorización

Se incorpora un script que envía una solicitud HTTP sin credenciales a un endpoint administrativo protegido de Futbolix.

El resultado esperado es HTTP 401 (Unauthorized) o HTTP 403 (Forbidden).

La prueba permite identificar si una solicitud sin autenticación consigue acceder a un recurso que debe estar protegido.

## 4. Requisitos previos

Para ejecutar los controles se requiere:

- Git instalado.
- PowerShell disponible.
- Acceso al repositorio personal.
- GitHub Actions habilitado.
- Un endpoint administrativo real de Futbolix que requiera autenticación.
- El servicio correspondiente en ejecución y accesible para realizar la prueba.

## 5. Ejecución de la prueba negativa

Desde la raíz del repositorio personal, ejecutar:

```powershell
$endpoint = "https://DIRECCION-REAL-DEL-SERVICIO/RUTA-ADMINISTRATIVA"

.\13-week\workshop\04-security-ci\negative-auth-test.ps1 -Url $endpoint
```

La solicitud se envía sin encabezados de autenticación.

### Resultados esperados

| Código HTTP | Interpretación | Resultado |
|---|---|---|
| 401 | Solicitud no autenticada | Prueba satisfactoria |
| 403 | Acceso prohibido | Prueba satisfactoria |
| 2xx | Solicitud aceptada | Prueba fallida |
| Otro código | Resultado diferente al esperado | Requiere revisión |

La prueba no debe considerarse satisfactoria si el servicio no está disponible o si no se puede verificar la respuesta HTTP.

## 6. Configuración de GitHub Actions

En el repositorio personal se debe configurar la variable de repositorio:

`FUTBOLIX_ADMIN_ENDPOINT`

Su valor debe corresponder a la dirección real del endpoint administrativo protegido.

Ruta de configuración:

1. Abrir el repositorio personal en GitHub.
2. Entrar en Settings.
3. Seleccionar Secrets and variables.
4. Entrar en Actions.
5. Seleccionar Variables.
6. Crear `FUTBOLIX_ADMIN_ENDPOINT` con la URL real.

La dirección del endpoint no debe contener tokens ni contraseñas. Las credenciales, cuando sean necesarias para otras pruebas, deben gestionarse mediante secretos protegidos.

## 7. Ejecución del workflow

El workflow se ejecuta mediante:

- Push a la rama `main`.
- Pull Request dirigido a `main`.
- Ejecución manual mediante `workflow_dispatch`.

Los resultados se consultan en la pestaña Actions del repositorio personal.

## 8. Verificación de resultados

Para comprobar la ejecución:

1. Abrir el repositorio personal en GitHub.
2. Entrar en Actions.
3. Seleccionar el workflow Futbolix Security Checks.
4. Abrir la ejecución correspondiente.
5. Revisar los resultados de Gitleaks, Trivy y la prueba negativa de autorización.

Una ejecución satisfactoria debe mostrar los controles completados correctamente. Los controles que no se hayan ejecutado o que no dispongan de resultados no deben registrarse como aprobados.

## 9. Evidencias

Se deben conservar evidencias reales de:

- Ejecución del análisis de secretos con Gitleaks.
- Ejecución del análisis de seguridad con Trivy.
- Resultado de la prueba negativa de autorización.
- Ejecución del workflow en GitHub Actions.

Las evidencias deben corresponder a ejecuciones realizadas sobre el proyecto y no a resultados simulados.

## 10. Consideraciones

- No almacenar contraseñas, tokens ni claves privadas en el repositorio.
- No utilizar direcciones ficticias para las pruebas.
- Verificar que el endpoint seleccionado requiere autenticación.
- Investigar los resultados fallidos antes de considerar completado el control.
- Mantener los resultados y las evidencias vinculados a la entrega CP4.
