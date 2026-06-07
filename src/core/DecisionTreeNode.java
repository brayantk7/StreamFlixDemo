package core;

import models.Movie;
import models.User;

public class DecisionTreeNode {

    private String atributoPrueba;
    private String valorEsperado;
    private DecisionTreeNode nodoSiCumple;
    private DecisionTreeNode nodoNoCumple;
    
    private Movie recomendacionFinal;

    public DecisionTreeNode(String atributoPrueba, String valorEsperado, DecisionTreeNode nodoSiCumple, DecisionTreeNode nodoNoCumple) {
        this.atributoPrueba = atributoPrueba;
        this.valorEsperado = valorEsperado;
        this.nodoSiCumple = nodoSiCumple;
        this.nodoNoCumple = nodoNoCumple;
    }

    public DecisionTreeNode(Movie recomendacionFinal) {
        this.recomendacionFinal = recomendacionFinal;
    }

    /**
     * MÉTODO RECURSIVO: Este es el corazón del algoritmo.
     * Se llama a sí mismo navegando por las ramas hasta encontrar una película.
     */
    public Movie evaluar(User usuario) {
        if (this.recomendacionFinal != null) {
            return this.recomendacionFinal;
        }

        boolean cumpleCondicion = false;
        
        if (atributoPrueba.equals("generoFavorito")) {
            cumpleCondicion = usuario.generoFavorito.equalsIgnoreCase(valorEsperado);
        } else if (atributoPrueba.equals("esMayorDeEdad")) {
            boolean mayorDeEdad = usuario.edad >= 18;
            cumpleCondicion = (valorEsperado.equals("true") && mayorDeEdad);
        }

        if (cumpleCondicion) {
            return nodoSiCumple.evaluar(usuario);
        } else {
            return nodoNoCumple.evaluar(usuario);
        }
    }
}