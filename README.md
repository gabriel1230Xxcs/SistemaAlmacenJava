# 📦 SistemaAlmacenJava

## Descripción

Sistema de gestión de almacén desarrollado en Java utilizando Programación Orientada a Objetos (POO) y una base de datos MySQL remota proporcionada por el docente.

La aplicación permite administrar productos mediante una interfaz gráfica moderna en Java Swing, integrando operaciones CRUD, búsqueda en tiempo real, visualización en tabla y gráficos dinámicos.

El sistema fue diseñado aplicando buenas prácticas de desarrollo, separación por capas y un módulo de seguridad para la protección de credenciales.

---

## Objetivo

Desarrollar una aplicación de escritorio que permita gestionar productos de forma eficiente, segura y organizada, utilizando POO, conexión a base de datos real y una interfaz gráfica amigable.

---

## Programación Orientada a Objetos (POO)

El sistema implementa los 4 pilares fundamentales:

### Abstracción

Separación en capas:

* Modelo (`Producto`)
* DAO (acceso a datos)
* Vista (interfaz gráfica)

### Encapsulamiento

Uso de atributos privados con métodos públicos en:

* `Producto`
* `Usuario`

### Herencia

Uso de clases como `JFrame` para crear interfaces personalizadas.

### Polimorfismo

Uso de métodos como `toString()` para representar objetos.

---

## ⚙️ Tecnologías Utilizadas

* ☕ Java (Swing)
* 🛢 MySQL (Base de datos remota)
* 🔗 JDBC
* 💻 Eclipse IDE
* 🌐 GitHub

---

## Funcionalidades del Sistema

### Gestión de Productos

* Agregar productos (Nombre, Marca, Categoría, Precio, Cantidad disponible)
* Editar productos
* Eliminar productos
* Búsqueda en tiempo real
* Visualización en tabla

### Dashboard

* Total de productos
* Cálculo automático del inventario
* Subtotal por producto seleccionado

### Gráficos

* Gráfico por cantidad de productos
* Gráfico por valor monetario
* Visualización dinámica con colores y leyendas

### Interfaz

* Diseño oscuro moderno
* Botones personalizados
* Navegación entre ventanas
* Pantalla de inicio (Splash Screen)
* Pantalla de cierre

---

## 🔐 Seguridad del Sistema

El sistema cuenta con un módulo de seguridad implementado en la clase `Seguridad`.

### 🔒 Encriptación de Contraseñas

Las contraseñas no se almacenan en texto plano.

Se utiliza el algoritmo **SHA-256** para generar un hash seguro antes de guardarlas en la base de datos.

Esto garantiza:

* Protección de datos sensibles
* Mayor seguridad del sistema

---

### alidación de Contraseñas

El sistema valida que la contraseña cumpla con:

* Mínimo 8 caracteres
* Al menos una letra mayúscula
* Al menos un número
* Al menos un símbolo especial (`@#$%&*!`)
* No permitir contraseñas débiles

---

### 🔑 Validación en Login

Durante el inicio de sesión:

1. Se encripta la contraseña ingresada
2. Se compara con la almacenada en la base de datos
3. Se permite acceso solo si coinciden

---

### 🚫 Prevención de Claves Débiles

El sistema bloquea contraseñas comunes como:

* 1234
* password
* admin

---

## Estructura del Proyecto

SistemaAlmacenJava/
│
├── src/
│   ├── vista/
│   ├── modelo/
│   ├── dao/
│   ├── conexion/
│   └── principal/

---

## Cómo ejecutar el proyecto

### 1. Clonar repositorio

```bash id="clone123"
git clone https://github.com/gabriel1230Xxcs/SistemaAlmacenJava.git
```

---

### 2. Abrir en Eclipse

* File → Import → Existing Projects
* Seleccionar la carpeta del proyecto

---

### 3. Configurar base de datos

Ir a:

```java id="conexionfile"
Conexion.java
```

Agregar la contraseña:

```java id="conexionpass"
private static final String PASSWORD = "TU_PASSWORD_AQUI";
```

⚠️ La contraseña no está incluida por seguridad y por el tema de Github

---

### 4. Ejecutar el sistema

Ejecutar desde:

```java id="mainfile"
Main.java
```

Esto permite:

* Visualizar la pantalla de inicio (Splash Screen)
* Ejecutar correctamente todas las funcionalidades
* Cargar el sistema completo

---

## Base de Datos (IMPORTANTE)

Este proyecto utiliza una **base de datos remota proporcionada por el maestro**.

### Características:

* Base de datos en la nube (Aiven)
* Acceso mediante JDBC
* No requiere instalación local

---

### ⚠️ Configuración necesaria

Para ejecutar el sistema correctamente:

1. Abrir el archivo:

```java id="conexionfile2"
Conexion.java
```

2. Ubicar:

```java id="conexionpass2"
private static final String PASSWORD = "";
```

3. Reemplazar por la contraseña del docente:

```java id="conexionpass3"
private static final String PASSWORD = "TU_PASSWORD_DEL_PROFESOR";
```

---

### Requisitos

* Conexión a internet
* Driver JDBC MySQL agregado

---

### Nota importante

* No se incluye base de datos local
* No es necesario importar archivos `.sql`
* El sistema trabaja directamente con la base remota

---
### Beneficio

Permite trabajar en un entorno real, simulando aplicaciones profesionales conectadas a servidores en la nube.

---

## 👨‍💻 Autor

**Gabriel Rodríguez Vásquez**

---

## Estado del Proyecto

✔ Completo
✔ Funcional
✔ Conectado a base de datos real
✔ Implementa seguridad
✔ Interfaz gráfica profesional

---

## 🏁 Conclusión

Este proyecto integra Java, MySQL y Programación Orientada a Objetos para desarrollar un sistema completo de gestión de inventario.

Incluye seguridad, conexión remota, gráficos y una interfaz moderna, logrando un resultado de nivel profesional listo para evaluación académica.
