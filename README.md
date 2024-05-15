
# Ejercicio Mandatorio - Cápsula Multithreading #TRAINING		
## Virtual HorseThread - Carrera de Caballos
***
***
Se debe desarrollar una aplicación ejecutable que simule una carrera de caballos de 1000 metros. 
Cada caballo avanzará de manera independiente y simultánea, informando en cada avance su nombre 
y la cantidad de metros total en ese instante (puede ser en la consola o en una página web). 
La carrera finaliza cuando 3 de los caballos llegan a la meta, para ello se deberá utilizar 
un "Semaphore" ya visto en el curso.

- Se recibe como INPUT la cantidad de caballos que corren, y en base a esto se escoge en tiempo de ejecución si usar
Threads tradicionales o Virtual Threads para que la performance sea óptima, justificando la elección en un comment.		
Instrucción de Ayuda: "Runtime.getRuntime().availableProcessors()".
***
***
Los caballos se generan de manera aleatoria con los siguientes atributos, mostrando el listado antes de comenzar
la carrera:		

- Nombre		
- Velocidad (entre 1 y 3)		
- Resistencia (entre 1 y 3)		
***
***
### Todos los caballos arrancan al mismo tiempo y se mueven respetando la siguiente lógica:

- Etapa de Avance: Se multiplica la vel del caballo por un entero aleatorio entre 1 y 10 metros.
- Etapa de Espera: Se suspende el avance por una cantidad aleatoria de segs entre 1 y 5, restando la resistencia del caballo. Ej: Si le tocó 4 segs, con resistencia 3, espera 1 seg antes de avanzar.

***
***
### Potenciadores:
Durante la carrera aparece un área potenciadora de 50 metros en cualquier lugar de la pista que se refresca cada 15 segs. Utilizar una Thread tradicional justificando si es daemon o no. Para que cada caballo conozca la posición del área deberán aplicar los conocimientos adquiridos de comunicación inter-threading.		
Si algún caballo pisa el área, espera 7 segundos y avanza 100 metros. Luego de ese tiempo el área vuelve a estar 
disponible para todos los caballos, antes debe estar "loqueado" por dichocaballo.	
***
***