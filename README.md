# Android Client

Se debe desarrollar un cliente _Android_ para la utlización por parte de los usuarios finales, es decir, pasajeros y conductores.

Este cliente se comunicará únicamente con un _Application Server_.

## Registro

En caso de que la persona que desee utilizar la aplicación no tenga un usuario registrado la aplicación debe permitir poder registrar un usuario nuevo. Este registro deberá poder ser integrado con Facebook, permitiendo ingresar las credenciales de facebook y utilizar los datos asociados de la cuenta para completar el registro del usuario dentro del sistema.

## Autenticación

Para que el cliente pueda enviar y recibir conversaciones el mismo debe autentificarse con el servidor. Para esto el cliente debe permitir al usuario ingresar usuario y contraseña. Se deberá diseñar una pantalla de ingreso a la aplicación.
Cuando la autenticación es exitosa, se almacenará un token de autenticación para realizar las operaciones con el servidor.

## Chat Pasajero-conductor

Después que un pasajero haya elegido a su conductor, y este último haya aceptado el viaje, se habilitará un chat entre ellos. Esta función esta destinada a subsanar el error del GPS a la hora de ubicar correctamente al pasajero. Así el pasajero podrá indicarle al conductor exactamente donde se encuentra u otras indicaciones para encontrarlo.

## Interfaz gráfica

Para el diseño de la interfaz deben respetarse las [guías de diseño propuestas por Google](https://www.google.com/design/spec/material-design/introduction.html).

## Notificaciónes _Push_

Para el envío y recepción de notificaciones del sistema (ej. Nuevos viajes disponibles, Viaje aceptado o mensajes de Chat) se deberá utilizar el servicio de notificaciones push de [FCM](https://firebase.google.com/products/cloud-messaging/)
