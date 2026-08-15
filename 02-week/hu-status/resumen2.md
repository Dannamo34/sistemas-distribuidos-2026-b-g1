# Resumen de la Sesión 2

## 1. Flujo de trabajo con Git

Durante la sesión se trabajó en la organización del repositorio y en el uso correcto de ramas para evitar cambios directos en las ramas principales.

Las ramas principales del proyecto son:

* `develop`: integración del desarrollo.
* `qa`: ambiente destinado a pruebas y validación.
* `main`: versión estable del proyecto.

### Regla importante

En las ramas:

```text
develop
qa
main
```

**NO se deben realizar cambios directamente.**

Para realizar modificaciones se debe crear una rama de trabajo a partir de la rama correspondiente y posteriormente realizar un Pull Request (PR).

---

## 2. Ramas de trabajo

Las ramas de trabajo permiten desarrollar nuevas funcionalidades o corregir errores sin afectar directamente las ramas principales.

Por ejemplo:

```text
develop
   ↓
HU-01
   ↓
Desarrollo
   ↓
Commit
   ↓
Pull Request
   ↓
develop
```

Las ramas pueden utilizarse para:

* Historias de usuario (HU).
* Nuevas funcionalidades (`feat`).
* Correcciones (`fix`).

Ejemplo:

```text
HU-01
feat/nombre-funcionalidad
fix/nombre-correccion
```

---

## 3. Historia de usuario

Las historias de usuario se identifican mediante códigos como:

* `HU-01`
* `HU-02`

La documentación relacionada con las historias de usuario se organiza en la carpeta:

```text
docs/
```

Esto permite mantener separada la documentación del código del proyecto.

---

## 4. Flujo de trabajo del Backend

Para trabajar una historia de usuario en el backend, se sigue un flujo como el siguiente:

### Paso 1: Actualizar `develop`

```bash
git pull origin develop
```

### Paso 2: Cambiar a `develop`

```bash
git switch develop
```

### Paso 3: Crear una rama para la historia de usuario

```bash
git switch -c HU-01
```

### Paso 4: Realizar los cambios

Se desarrolla la funcionalidad correspondiente a la historia de usuario.

### Paso 5: Crear un commit

```bash
git add .
git commit -m "Implementar HU-01"
```

### Paso 6: Crear un Pull Request

La rama de trabajo se integra mediante un Pull Request hacia `develop`.

```text
HU-01 → develop
```

---

## 5. Flujo hacia QA

Después de integrar los cambios en `develop`, se puede continuar con el ambiente de pruebas.

Primero se actualiza el repositorio:

```bash
git pull origin develop
```

Luego se cambia a `qa`:

```bash
git switch qa
```

Se crea una rama de trabajo para QA:

```bash
git switch -c HU-01-qa
```

Después de realizar las modificaciones o ajustes necesarios, se realiza el commit y posteriormente el Pull Request correspondiente.

```text
HU-01-qa → qa
```

---

## 6. Frontend, Backend, Database e Infraestructura

El proyecto se divide en diferentes áreas de trabajo:

* **Backend:** lógica del servidor y servicios.
* **Frontend:** interfaz y experiencia del usuario.
* **Database:** estructura y gestión de los datos.
* **Infraestructura:** configuración y recursos necesarios para ejecutar el sistema.

Cada área debe trabajar mediante ramas propias y respetar el flujo establecido para `develop`, `qa` y `main`.

---

## 7. Pull Request y Cherry-Pick

Durante la sesión también se revisó el concepto de **Pull Request (PR)** y el uso de **Cherry-Pick**.

### Pull Request

Un Pull Request permite solicitar que los cambios realizados en una rama sean revisados antes de integrarlos en otra rama.

Ejemplo:

```text
HU-01 → develop
```

Esto permite revisar el código y mantener un proceso controlado de integración.

### Cherry-Pick

`git cherry-pick` permite seleccionar un commit específico de una rama y aplicarlo sobre otra rama.

Se debe utilizar con cuidado y únicamente cuando el flujo del proyecto lo requiera.

La idea principal es evitar realizar cambios manualmente directamente sobre las ramas principales.

---

## 8. Flujo recomendado

El flujo general trabajado durante la sesión puede representarse así:

```text
                develop
                   │
                   ↓
              HU-01 / feat
                   │
              Desarrollo
                   │
                Commit
                   │
             Pull Request
                   │
                   ↓
                develop
                   │
                   ↓
                  QA
                   │
                   ↓
                 main
```

De esta manera se mantiene un proceso ordenado y se reducen los riesgos de afectar las versiones estables del proyecto.

---

## 9. Scrum aplicado al proyecto

Además del flujo de Git, se habló sobre la utilización de **Scrum** para organizar el trabajo del equipo.

En las reuniones o seguimientos se pueden responder tres preguntas principales:

### ¿Qué hice?

Se explica el trabajo realizado desde la última reunión.

### ¿Qué voy a hacer?

Se indican las actividades que se realizarán a continuación.

### ¿Qué bloqueantes tengo?

Se mencionan los problemas o dificultades que impiden continuar con el trabajo.

Este seguimiento permite conocer el avance del equipo, identificar problemas rápidamente y organizar mejor las actividades.

---

## 10. Semana 2

Durante la segunda semana se trabajó principalmente en:

* Crear y organizar el repositorio.
* Comprender el flujo de ramas.
* Replicar el ejemplo de Cherry-Pick.
* Replicar el flujo de trabajo utilizando historias de usuario.
* Comprender el uso de `develop`, `qa` y `main`.
* Aplicar buenas prácticas para trabajar con Git.
* Relacionar el flujo de trabajo con Scrum.

### Objetivo

El objetivo es establecer un flujo de trabajo organizado donde cada integrante pueda desarrollar sus actividades sin modificar directamente las ramas principales y donde los cambios puedan ser revisados e integrados de manera controlada.
