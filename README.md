# MoviesApp

![App screenshot](https://drive.google.com/uc?export=view&id=1zGcvxQuaqWfrWndwl3DTrwQmUuaHMBff)
![App screenshot](https://drive.google.com/uc?export=view&id=1CaeOiZ-kFjgu7WgyGT9QUTknAdMls0RI)

Select your language:
1. [I want to see this in English](#english)
2. [Quiero leer esto en español](#spanish)

Note: Translations vary a little bit because I have been requested to add extra info to the Spanish version

# SPANISH

Aplicación en la que se puede visualizar la información de tres categorías (popular, top rated y upcoming) de la API de The Movie DB.

Permite visualizar el detalle de cada película

La información descargada es cacheada internamente


## Nota importante

- Para ejectuar la app se debe seleccionar el buildvariant realApiDebug
- Para ejectuar los tests se debe seleccionar el buildvariant mockApiDebug


## Arquitectura

La arquitectura de la app está basada en una arquitectura MVI con flujo de datos unidireccional. Se ha tomado el siguiente proyecto como ejemplo: (https://github.com/oldergod/android-architecture/tree/todo-mvi-rxjava-kotlin)
En esta arquitectura, el usuario es considerado una función y es parte del flujo de datos. Él/ella recibe el input de los eventos y cambios de vista, y a su vez, emite eventos (hace click, hace scroll, etc...)

![Imagen arquitectura](https://raw.githubusercontent.com/oldergod/android-architecture/todo-mvi-rxjava-kotlin/art/MVI_detail.png)

## Elementos de la arquitectura

### Intent

El Intent representa la intención del usuario (por ejemplo, hacer click en un botón). Pertenece a la capa de la vista

### Action desde el Intent

Los Intents se traducen en su respectiva Action. Por ejemplo, un botón de recarga se traducirá en una acción de recarga de la información. Traduce caso de uso a capa de negocio.

### Action

Las acciones definen la lógica de negocio que deberá ser ejecutada por el Processor. Pertenece a la capa de negocio.

### Processor

El processor ejecuta una acción. Dentro del ViewModel es el único lugar donde pueden tener lugar side-effects: escritura de datos, lectura de datos, network, etc...

### Result

Son los resultados de lo que se ha ejecutado en el Processor. Pertenece a la capa de negocio.

### Reducer

El Reducer es el responsable de generar el ViewState que luego la vista usará para renderizarse. La vista no debe tener estado en el sentido de que solo el ViewState debería ser suficiente para renderizarla. El Reducer toma la última versión del ViewState disponible, aplica el último Result y devuelve un nuevo ViewState.
Traduce capa de negocio a vista.

###  ViewState

El ViewState contiene todo lo necesario para renderizar la vista. Pertenece a la capa de la vista.
 

## Preguntas

### 1. En qué consiste el principio de responsabilidad única? Cuál es su propósito?

El principio de responsabilidad única es el primero de los principios SOLID, publicados por Robert C. Martin en su libro Clean Code. Viene a decir que una clase solo debe tener una razón para ser modificada, en otras palabras, una clase solo debe realizar una única función (tener un sólo cometido).

### 2. Qué características tiene, según su opinión, un “buen” código o código limpio?

En mi opinión, un código limpio es un código en el que es muy sencillo realizar modificaciones, testear y refactorizar cuando es necesario. Para ello existen una serie de recomendaciones como por ejemplo evitar duplicación de código, desacoplar las dependencias al máximo, inyectar las dependencias para que sea más sencillo mockearlas y testearlas, etc...
Mis libros favoritos al respecto son: Clean Code (Robert C. Martin), The Pragmatic Programmer (Andy Hunt y David Thomas), Refactoring: Improving the Design of Existing Code (Martin Fowler) y Working Effectively With Legacy Code (Michael C. Feathers)


# ENGLISH

Movies app that consumes the Movie Database API (using a MVI architecture)


## Important note

- To execute the app use the realApiDebug
- To execute the tests use the mockApiDebug


## Architecture

The app architecture is based in the MVI architecture proposed by the project (https://github.com/oldergod/android-architecture/tree/todo-mvi-rxjava-kotlin)

In this architecture, the user is considered a function and he/she is part of the data flow. He/she takes input from previous events and emits events.

![Architecture image](https://raw.githubusercontent.com/oldergod/android-architecture/todo-mvi-rxjava-kotlin/art/MVI_detail.png)


## Architecture elements

### Intent

Intents represents, as their name goes, intents from the user (for example tap a button or fill an input)

### Action from Intent

Intents are in this step translated into their respecting logic Action. For instance, the refresh button will translate into the refresh action.

### Action

Actions defines the logic that should be executed by the Processor.

### Processor

Processor simply executes an Action. Inside the ViewModel, this is the only place where side-effects should happen: data writing, data reading, etc.

### Result

Results are the result of what have been executed inside the Processor

### Reducer

The Reducer is responsible to generate the ViewState which the View will use to render itself. The View should be stateless in the sense that the ViewState should be sufficient for the rendering. The Reducer takes the latest ViewState available, apply the latest Result to it and return a whole new ViewState.

### ViewState

The State contains all the information the View needs to render itself.



# Bibliography:

Redux-in UI Bugs - (https://www.youtube.com/watch?v=UsuzhTlccRk)

Reactive apps with model view intent - Post series - (http://hannesdorfmann.com/android/mosby3-mvi-1)

MVI architecture blueprint - (https://github.com/oldergod/android-architecture/tree/todo-mvi-rxjava-kotlin)
