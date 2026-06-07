package models;

public class Movie {
    public String id;
    public String titulo;
    public String genero;
    public double calificacion;

    public Movie(String id, String titulo, String genero, double calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.calificacion = calificacion;
    }
}