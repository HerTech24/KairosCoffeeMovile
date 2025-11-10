# KairosCoffeeMovile
Aplicación móvil de e-commerce para venta de café y productos relacionados.
📱 Descripción del Proyecto
Kairos Coffee es una aplicación Android nativa desarrollada en Kotlin con Jetpack Compose que implementa un sistema completo de e-commerce con las siguientes capacidades:

🔐 Sistema de autenticación de usuarios
📦 Catálogo de productos organizado por categorías
🛒 Carrito de compras con cálculo automático de totales e IVA (19%)
⚙️ Panel de administración con CRUD completo de productos
🌐 Sincronización con API REST
💾 Persistencia local con Room Database
📡 Modo offline-first con sincronización en background


✨ Features
🔑 Autenticación

Login con validación de credenciales
Manejo de sesión persistente
Navegación protegida según rol de usuario

🏪 Catálogo de Productos

Lista de productos con imágenes y descripciones
Filtros por categoría (Café, Té, Accesorios, etc.)
Búsqueda en tiempo real
Visualización de stock disponible

🛒 Carrito de Compras

Agregar/eliminar productos
Ajuste de cantidad con botones +/-
Cálculo automático:

Subtotal: Σ(precio × cantidad)
IVA: Subtotal × 19%
Total: Subtotal + IVA


Persistencia del carrito entre sesiones

👨‍💼 Panel de Administración

Crear nuevos productos
Editar productos existentes
Eliminar productos
Actualizar stock y precios
Sincronización bidireccional con API

🔄 Sincronización

Cache-first strategy: Datos locales como fuente primaria

Estructura de Paquetes
com.android.kairoscoffeemovile/
├── data/
│   ├── local/              # Room Database
│   │   ├── dao/           # Data Access Objects
│   │   └── entities/      # Modelos de BD
│   ├── remote/            # API REST
│   │   ├── api/          # Retrofit Service
│   │   └── dto/          # Data Transfer Objects
│   └── repository/        # Repositorios (fuente de verdad)
│
├── ui/
│   ├── screens/          # Pantallas Compose
│   ├── components/       # Componentes reutilizables
│   ├── viewmodels/       # Lógica de negocio
│   └── theme/            # Temas y estilos
│
├── navigation/           # Navegación entre pantallas
└── utils/               # Utilidades y helpers

🛠️ Tecnologías y Librerías
Core

Kotlin 1.9.20 - Lenguaje de programación
Jetpack Compose 1.5.4 - UI declarativa
Material3 - Diseño moderno

Arquitectura

ViewModel 2.6.2 - Gestión de estado
Navigation Compose 2.7.5 - Navegación
Coroutines 1.7.3 - Programación asíncrona
Flow - Streams reactivos

Persistencia

Room 2.6.0 - Base de datos local
DataStore - Preferencias del usuario

Networking

Retrofit 2.11.0 - Cliente HTTP
OkHttp 4.12.0 - Interceptores y logging
Gson 2.10.1 - Serialización JSON

Testing

Kotest 5.9.1 - Framework de testing
MockK 1.13.11 - Mocking de dependencias
Coroutines-test 1.7.3 - Testing de corrutinas
JUnit4 - Tests unitarios

Build & Deploy

KSP - Procesamiento de anotaciones
ProGuard - Ofuscación de código
Gradle Version Catalog - Gestión de dependencias


📋 Requisitos del Sistema

Android Studio: Hedgehog (2023.1.1) o superior
JDK: 17 o superior
Min SDK: 24 (Android 7.0)
Target SDK: 36
Gradle: 8.2 
Sincronización automática con servidor REST
Manejo de conflictos y errores de red
Operaciones offline con cola de sincronización
