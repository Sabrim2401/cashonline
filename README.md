# cashonline

En esta aplicación, el usuario puede darse de alta registrandose con los siguientes datos:
  email - firstname - lastname - username - password

Una vez que el usuario fue dado de alta, se debe loguear con los siguientes datos:
 username - password

Cuando el usuario se loguea, de forma automatica devuelve un token, el cual debe ser guardado para los siguientes pasos.

Para crear un prestamo/loan primero, se debe ingresar el token en la opcion Authorization, seleccionar Type: Bearer Token y pegar el token generado. De esta forma, al crear un prestamo, esta asociado al usuario que se logueo. Volvemos nuevamente donde dice "" totalAmountLoan "" y se ingresa el valor del prestamo.

Se puede obtener la lista de todos los prestamos otorgados ingresando el page y size. Y ademas, agregando el parametro userId se puede filtrar que la lista solo sea de ese usuario con sus prestamos.

Se puede eliminar un usuario con su numero de id.
