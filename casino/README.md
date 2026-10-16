# Casino (juego de dados) - Spring Boot

API REST: Jugador, Partida y Tiro. Java 21, Spring Boot, JPA/Hibernate, MySQL.

## Ejecutar
1. MySQL encendido (usuario `root`, contraseña `root`; se cambia en `application.properties`).
2. `mvn spring-boot:run` (o ejecutar `CasinoApplication` desde IntelliJ).
3. La base `casino` y las tablas se crean solas. Swagger: http://localhost:8080/swagger-ui/index.html

## Endpoints
| Servicio | Método | URL | Body |
|---|---|---|---|
| Crear jugador | POST | /jugador | `{"nombre":"Ana","saldo":500}` |
| Listar / consultar | GET | /jugador, /jugador/{id} | - |
| recargarSaldo | PATCH | /jugador/{id}/recargar | `{"monto":100}` |
| Activar / desactivar | PATCH | /jugador/{id}/activar, /desactivar | - |
| iniciarPartida | POST | /partida/iniciar | `{"jugadorId":1,"apuesta":50}` |
| realizarTiro | POST | /tiro/{partidaId} | - |
| finalizarPartidaManual | PATCH | /partida/{id}/finalizar | - |
| obtenerHistorialPartida | GET | /partida/jugador/{jugadorId} | - |

## Reglas del tiro
Dos dados (1-6). Suma 7 u 11 gana (partida FINALIZADA y el jugador recibe el doble de la apuesta).
Suma 2, 3 o 12 pierde (FINALIZADA). Cualquier otra suma: la partida continúa EN_JUEGO.

## Errores
- 400: validaciones `@Valid` (`{"campo":"mensaje"}`) y reglas de negocio (`{"error":"..."}`).
- 404: jugador o partida inexistente (`{"error":"..."}`).
