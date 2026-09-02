# Unit 1 · Corte 1
# Resumen — Building a Service + Planning MVP 1

---

# Introducción

Durante estas dos sesiones se trabajaron dos temas fundamentales para el desarrollo de un servicio:

1. **Cómo construir y organizar un servicio utilizando Arquitectura Hexagonal.**
2. **Cómo planificar el MVP 1 de un sprint de desarrollo.**

La primera sesión se concentra principalmente en la **construcción técnica del servicio**, mientras que la segunda se enfoca en la **planificación, priorización y definición del trabajo que debe realizar el equipo**.

La idea general es que un equipo no debe limitarse a escribir código que compile, sino que debe construir un servicio que pueda ejecutarse realmente, conectarse con una base de datos y responder solicitudes. Además, antes de desarrollar las funcionalidades del MVP, el equipo debe establecer claramente qué va a construir, cómo lo va a probar y qué condiciones debe cumplir para considerarlo terminado.

---

# SESIÓN 1
# Building a Service — Structure, Layers and the Walking Skeleton

## Objetivo de la sesión

El objetivo principal de esta sesión es aprender cómo transformar el diseño de un sistema en un **servicio funcional**, utilizando una estructura organizada por responsabilidades.

Se trabaja principalmente con:

- Arquitectura Hexagonal.
- Separación por capas.
- Domain.
- Application.
- Adapters.
- Ports.
- Dependency Inversion Principle (DIP).
- Composition Root.
- Walking Skeleton.
- Integración continua desde el inicio.
- Validación del servicio en tiempo de ejecución.

La idea principal es que la arquitectura no solamente debe existir en documentos o diagramas, sino que debe poder observarse directamente en la estructura del código.

---

# 1. Arquitectura Hexagonal

La **Arquitectura Hexagonal**, también conocida como **Ports and Adapters**, busca separar el núcleo de la aplicación de las tecnologías externas.

El objetivo es que las reglas de negocio no dependan directamente de:

- Bases de datos.
- Frameworks.
- APIs externas.
- HTTP.
- Sistemas de mensajería.
- Interfaces de usuario.

El núcleo de la aplicación debe permanecer lo más independiente posible.

La arquitectura se puede entender de la siguiente manera:

```text
                    ┌─────────────────────┐
                    │   Inbound Adapter   │
                    │    HTTP / REST      │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Application      │
                    │     Use Cases       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       Domain        │
                    │   Business Rules    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   Outbound Port     │
                    │    Repository       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │  Outbound Adapter   │
                    │     PostgreSQL      │
                    └─────────────────────┘

# 2. Organización de carpetas

La estructura del proyecto debe reflejar la arquitectura.

Una estructura básica puede ser:

src/
├── domain/
│   ├── model/
│   ├── services/
│   └── ports/
│
├── application/
│   └── usecases/
│
└── adapters/
    ├── inbound/
    │   └── http/
    │
    └── outbound/
        └── persistence/

Domain

El Domain contiene las reglas principales del negocio.

Aquí se encuentran los conceptos propios del sistema, por ejemplo:

Cliente
Reserva
Cancha
Pago

También se encuentran las reglas que determinan qué operaciones son válidas o inválidas.

El dominio no debería depender directamente de PostgreSQL, HTTP o cualquier otra tecnología externa.

Application

La capa Application contiene los casos de uso.

Un caso de uso representa una acción que el sistema puede realizar.

Ejemplos:

CrearReserva
CancelarReserva
ConfirmarReserva
ConsultarDisponibilidad
RegistrarPago

La aplicación coordina los pasos necesarios para ejecutar una operación, mientras que las reglas principales del negocio permanecen en el dominio.

Adapters

Los Adapters conectan la aplicación con el mundo exterior.

Se dividen principalmente en:

Inbound Adapters

Son los elementos que permiten que algo externo se comunique con nuestro sistema.

Ejemplos:

REST Controller
HTTP API
CLI
Mensajería

Un controlador HTTP puede recibir:

POST /reservas

y enviar esa solicitud al caso de uso correspondiente.

Outbound Adapters

Son los elementos que permiten que nuestra aplicación se comunique con sistemas externos.

Ejemplos:

PostgreSQL Repository
RabbitMQ Adapter
External API Client
File Storage

Por ejemplo, un repositorio puede encargarse de guardar una reserva en PostgreSQL.

3. Flujo de una solicitud

Una solicitud debe atravesar las diferentes capas de manera organizada.

Por ejemplo, si un usuario quiere crear una reserva:

Usuario
   ↓
HTTP Request
   ↓
Controller
   ↓
CreateReservation Use Case
   ↓
Domain
   ↓
Reservation Repository Port
   ↓
PostgreSQL Repository
   ↓
Base de datos

El proceso funciona de la siguiente manera:

Paso 1 — Cliente

El cliente realiza una solicitud HTTP.

Ejemplo:

POST /api/v1/reservations
Paso 2 — Inbound Adapter

El Controller recibe la solicitud.

Su responsabilidad es recibir y adaptar los datos, no implementar todas las reglas del negocio.

Paso 3 — Application

El Controller llama al caso de uso correspondiente.

Por ejemplo:

CreateReservation

El caso de uso coordina la operación.

Paso 4 — Domain

El dominio verifica las reglas del negocio.

Por ejemplo:

¿La cancha existe?
¿Está disponible?
¿El horario es válido?
¿Existe otra reserva?
Paso 5 — Port

La aplicación utiliza un puerto para solicitar la persistencia.

Por ejemplo:

reservationRepository.save(reservation);

El caso de uso conoce la interfaz, pero no necesita conocer cómo funciona PostgreSQL.

Paso 6 — Outbound Adapter

Una implementación concreta del puerto utiliza PostgreSQL para guardar la información.

ReservationRepository
        ↓
PostgresReservationRepository
        ↓
PostgreSQL
4. Dependency Inversion Principle — DIP

El Dependency Inversion Principle (DIP) indica que las partes importantes del sistema no deben depender directamente de implementaciones concretas.

Incorrecto:

public class CreateReservation {

    private PostgresReservationRepository repository =
        new PostgresReservationRepository();

}

Correcto:

public class CreateReservation {

    private final ReservationRepository repository;

    public CreateReservation(ReservationRepository repository) {
        this.repository = repository;
    }

}


