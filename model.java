import java.util.List;

enum Rol {
    COMPRADOR, VENDEDOR,
}

class Pais {
    String nombre;
}

class Provincia {
    String nombre;
    Pais pais;
}

class Localidad {
    String nombre;
    Provincia provincia;
}

class Direccion {
    String nombre;
    String calle;
    Integer altura;
    String piso;
    String departamento;
    Localidad localidad;
}

class Usuario {
    List<Rol> roles;
    List<Direccion> direcciones;
}