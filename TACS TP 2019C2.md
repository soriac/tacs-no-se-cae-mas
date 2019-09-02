# TACS TP 2019C2

# Trabajo práctico TACS 2019 - 1er Cuatrimestre

El objetivo del TP es desarrollar una aplicación que permita a nuestros usuarios realizar métricas sobre repositorios de Github. Como fuente para los mismos se utilizará la API de [Github](https://www.google.com/url?q=https://developer.github.com/v3/&sa=D&ust=1567156738711000).

La aplicación funcionará de modo stand alone, y estará publicada en la nube para ser accedida.

El TP constará de 5 entregas en las cuales de forma iterativa e incremental se irán agregando funcionalidades a la aplicación.

Consejos de la cátedra para aprobar el TP:

- Enfocarse en los requerimientos de cada entrega. (Se puede hacer de más pero no de menos)
- Utilizar al ayudante para validar decisiones de diseño y consultar arquitectura, frameworks, etc.
- Dividir en forma clara en el equipo las historias de cada entrega para atacarlas en paralelo donde sea posible.
- Comunicarse fluidamente dentro del equipo, aprovechar los días de cursada para hablar de status, problemas, decisiones técnicas y explicar soluciones complejas a todos los integrantes.
- Utilizar alguna herramienta para la gestión de tareas
  - [https://scrumy.com/](https://www.google.com/url?q=https://scrumy.com/&sa=D&ust=1567156738713000)
  - [https://trello.com/](https://www.google.com/url?q=https://trello.com/&sa=D&ust=1567156738713000)


- Crear una lista de mail para el equipo
- Para bajar el riesgo de las futuras entregas aprovechar el tiempo de entregas anteriores para investigar las tecnologías.
- De ser necesario utilizar al ayudante como facilitador, en cuestiones técnicas y de organización → El rol del ayudante NO es simplemente el de corregir, sino dar soporte al equipo durante todo el proceso en cuestiones técnicas y metodológicas.



# Restricciones y consideraciones

## Requerimientos no funcionales

1. La aplicación debe estar hosteada en la nube, en futuras entregas se acordará con el ayudante que utilizar.
2. Todas las llamadas al servidor deben ser asincrónicas (desde el frontend, no desde el backend).
3. Si bien se espera algo sencillo. La aplicación debe tener un frontend amigable a los usuarios. (Usar la creatividad y los recursos que brinda la API)
4. Se debe utilizar maven para gestionar el life-cycle de la aplicación.
5. Se debe utilizar GIT como SCM.
6. El nivel de cobertura de tests debe ser superior al 70%.
7. Es tan importante el hecho de que la aplicación funcione como se espera como aplicar un buen diseño para la construcción de la misma.
8. Todos los métodos no triviales deben tener su correspondiente javadoc explicando su función, forma de uso y cualquier otra información relevante.
9. Cualquier decisión respecto del código o las soluciones utilizadas debe estar documentada, así como un howto.txt o README.md para levantar la aplicación incluído en el repositorio.
10. La aplicación debe ser capaz de correrse utilizando el comando mvn jetty:run o similar, a definir por el equipo y especificar en el documento README.md o wiki.
11. La aplicación debe tener persistencia en algún tipo de base. La elección de la misma debe estar justificada y será acordada previamente con el ayudante.
12. La APP tiene que cumplir con requerimientos mínimos de seguridad (Manejo de contraseñas, recursos externos, etc.)


1. Las claves deben ser guardadas de forma [correcta](https://www.google.com/url?q=https://es.wikipedia.org/wiki/Sal_(criptograf%25C3%25ADa)&sa=D&ust=1567156738716000).
2. En caso de existir, las API keys NUNCA debe ser expuestas al usuario

## Administrativas

1. Las entregas deberán realizarse el día pactado para la misma antes de las 19 Hs. con un tag llamado Entrega_XX correspondiente al número de entrega.
2. Las entregas se realizarán indicando el link al repositorio GIT y el tag designado para la entrega.
3. Todo retraso en una entrega que no haya sido correctamente comunicado y justificado tendrá como penalización el agregado de nuevos requisitos para la aprobación final del TP.
4. Los requerimientos no funcionales no solo son importantes para aprobar el TP sino que están directamente relacionados con la filosofía y objetivos de la materia. La calidad no se negocia.

# User Stories:

- Como usuario quiero poder crear una cuenta con user y pass. Nota: La cuenta es de nuestra aplicación, no de Github. La aplicación manejará una única cuenta de Github para acceder a los recursos necesarios.
- Como usuario quiero hacer log in en la página
- Como usuario quiero hacer log out en la página
- Desde la aplicación deberán autenticarse en Github para obtener incrementar el límite de solicitudes por hora.
- En el caso que se excedan las solicitudes se deberá devolver un mensaje indicando cuando podrá volver a usar la aplicación.
- Como usuario poder buscar un repositorio y que me muestre información relevante del mismo por ejemplo
  - Nro de forks
  - Issues abiertos
  - Cantidad de estrellas
  - Lenguaje que utiliza


- Como usuario poder definir un filtro de búsqueda sobre repos de github, al menos tiene que tener 5 criterios.
- Poder agregar esos repositorios a una lista de favoritos
- Permitir editar y eliminar esta lista  
- Como administrador quiero poder ver los siguientes datos de un usuario:
  - Usuario
  - Cantidad de repositorios favoritos
  - Último acceso
  - Que lenguajes tiende a utilizar el usuario


- Como administrador quiero seleccionar 2 listas de usuarios diferentes y ver si tienen algún repositorio y lenguaje en común.
- Como administrador quiero seleccionar un repositorio y ver la cantidad de usuarios que lo agregaron a favoritos.
- Como administrador quiero conocer la cantidad total de repositorios registrados en el sistema
  - En el día de hoy
  - En los últimos 3 días
  - En la última semana
  - En el último mes
  - Desde el inicio de los tiempos

# Entregas:

## Entrega 1 - Basis

Esqueleto de la aplicación WEB.

Se debe definir un primer approach hacia los recursos y URLs REST que se utilizarán para cumplir con las historias propuestas. Para esta entrega no es necesario que las historias funcionen sino que los recursos devuelvan respuestas ficticias estáticas. No es necesario tener un frontend en esta instancia.

## Entrega 2 - App volátil + API externa

Funcionalidad principal sin diferenciar usuarios y persistiendo en memoria.

Se debe definir el comportamiento de los principales servicios relacionados al dominio y cumplir con la funcionalidad persistiendo en memoria. La integración principal con la API externa debe estar disponible en esta entrega así como la integración con Telegram.

## Entrega 3 - Interfaz de usuario: Frontend / Telegram

Primera versión del front end o de la API de Telegram. A elegir uno de los 2.

Opcional: Para aplicar a la promoción implementar tanto Frontend como Telegram.

Entrega 4 - Persistencia + Cloud

Persistencia utilizado una base de datos

Se debe modificar la aplicación para que en vez de almacenar los datos en memoria, la misma lo haga utilizando alguna base a definir por el equipo. Para esta entrega la aplicación debe estar deployada en la nube.

Entrega 5 - Individual

Entrega final del TP. Cerrar historias que no hayan sido atacadas.

Se coordinará con el ayudante el desarrollo de una historia individual por cada integrante del equipo.

