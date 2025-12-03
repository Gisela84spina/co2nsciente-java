# CO2nsciente - Backend (Spring Boot + MySQL)

Proyecto backend desarrollado para el curso de Spring Boot y SQL.  
El objetivo es gestionar productos asociados a regiones, con soporte para creación, lectura, actualización y eliminación lógica (soft delete).

---

## 🚀 Tecnologías utilizadas
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

---

## 📦 Funcionalidades
- Crear productos
- Listar productos activos (no eliminados)
- Buscar producto por ID
- Actualizar producto
- Eliminación lógica (marca `eliminado = true`)
- Filtrar productos por región

---

## 🗄️ Base de datos
Base utilizada: **tienda**

Tabla principal: **producto**

Columnas:
- `id` (BIGINT, PK, auto-increment)
- `nombre` (VARCHAR)
- `precio` (DOUBLE)
- `descripcion` (VARCHAR)
- `region` (VARCHAR)
- `eliminado` (BOOLEAN)

Configuración en `application.properties`:
