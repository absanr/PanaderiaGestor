# Panadería Gestor

#### Desarrollado como un Proyecto para la Universidad UTP - Perú

## Descripción
Panadería Gestor es una aplicación diseñada para la gestión integral de una panadería. Ofrece funcionalidades para administrar empleados, turnos, asistencia, ventas, productos y finanzas del negocio. Con esta herramienta, se facilita la operación diaria y se mejora el control y la eficiencia del negocio.

## Funcionalidades

- **Gestión de Empleados:**
    - Crear, ver, actualizar y eliminar empleados.
    - Registrar y actualizar la asistencia de los empleados.
    - Asignar turnos de trabajo a los empleados.

- **Gestión de Turnos:**
    - Crear, ver y gestionar turnos de trabajo.
    - Asignar empleados a turnos específicos.

- **Gestión de Asistencia:**
    - Registrar la asistencia diaria de los empleados.
    - Ver y actualizar registros de asistencia.

- **Gestión de Zonas:**
    - Administrar las diferentes zonas de trabajo dentro de la panadería.

- **Gestión de Ventas:**
    - Registrar nuevas ventas.
    - Ver el historial de ventas.
    - Generar reportes de ventas diarias y por rangos de fecha.

- **Gestión de Inventario:**
    - Agregar, ver y gestionar productos.
    - Control de stock y actualización de inventario.

- **Gestión de Capital y Pagos:**
    - Ver el capital actual del negocio.
    - Inyectar capital adicional.
    - Registrar pagos a empleados.
    - Ver historial de pagos realizados.

- **Reportes:**
    - Generar reportes de asistencia.
    - Generar reportes de turnos.
    - Generar reportes de ventas.
    - Generar reportes de gastos vs. ganancias.

## Configuración Inicial

Al iniciar la aplicación por primera vez, se realiza una configuración inicial donde se crean los archivos necesarios para el funcionamiento del sistema. Se solicita la creación de un usuario administrador para el acceso inicial.

## Archivos de Datos

Los datos del sistema se almacenan en los siguientes archivos dentro de la carpeta `src/main/resources/`:

- `usuarios.txt` - Datos de los usuarios del sistema.
- `empleados.txt` - Datos de los empleados.
- `turnos.txt` - Datos de los turnos asignados a los empleados.
- `asistencia.txt` - Registros de asistencia de los empleados.
- `zonas.txt` - Información de las diferentes zonas de trabajo.
- `negocio.txt` - Información financiera del negocio.
- `productos.txt` - Información de los productos y stock.
- `ventas.txt` - Historial de ventas realizadas.
- `pagos.txt` - Registros de pagos realizados a los empleados.

## Uso

1. **Inicio de Sesión:**
    - Los usuarios deben autenticarse con su nombre de usuario y contraseña para acceder al sistema.

2. **Navegación por el Menú Principal:**
    - Dependiendo del rol del usuario, tendrá acceso a diferentes secciones del menú principal:
        - Negocio
        - Ventas
        - Inventario
        - Reportes
        - Capital y Pagos

3. **Gestión y Reportes:**
    - Los usuarios pueden gestionar empleados, turnos, asistencia, productos y ventas. También pueden generar y visualizar reportes detallados según sus permisos.

## Roles y Permisos

- **Administrador:**
    - Acceso completo a todas las funcionalidades del sistema.

- **Manager:**
    - Acceso a la gestión de empleados, turnos, asistencia, productos, ventas y reportes.

- **Cajero:**
    - Acceso a la gestión de ventas y generación de reportes de ventas.

- **Repostero:**
    - Acceso a la gestión de productos e inventario.

- **Limpieza:**
    - Acceso limitado, solo para registrar su propia asistencia.

## Requisitos

- Java SDK 22 o superior.
- IntelliJ IDEA u otro IDE de Java.
- Configuración del entorno con las rutas adecuadas para los archivos de datos.

## Instalación

1. Clona el repositorio del proyecto.
2. Abre el proyecto en tu IDE.
3. Configura el entorno de ejecución asegurándote de que Java SDK 22 o superior esté instalado.
4. Ejecuta la aplicación desde la clase principal `MenuPrincipal`.

## Contribución

Si deseas contribuir a este proyecto, por favor sigue los siguientes pasos:

1. Realiza un fork del repositorio.
2. Crea una nueva rama para tus cambios.
3. Realiza los cambios necesarios y realiza commits con mensajes claros.
4. Envía un pull request describiendo tus cambios en detalle.

## Licencia

Este proyecto está bajo la Licencia MIT. Para más detalles, consulta el archivo `LICENSE`.

---

**Desarrollado por [ABSANR](https://github.com/absanr/)**
