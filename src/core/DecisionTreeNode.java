package core;

import models.Movie;
import models.User;

/**
 * Represents a node in the recommendation decision tree.
 * Handles both attribute evaluation nodes and leaf nodes (final recommendations).
 */
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
     * Recursively traverses the tree based on user attributes to find a recommendation.
     * @param usuario The user profile to evaluate.
     * @return The recommended Movie object.
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