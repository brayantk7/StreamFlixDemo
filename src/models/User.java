package models;

public class User {
    public String id;
    public String nombre;
    public int edad;
    public String generoFavorito;

    public User(String id, String nombre, int edad, String generoFavorito) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.generoFavorito = generoFavorito;
    }
}