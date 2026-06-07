package core;

import java.util.List;
import models.Movie;
import models.User;

/**
 * Implements collaborative filtering to recommend movies based on user similarity.
 */
public class CollaborativeFiltering {
    
    /**
     * Finds a similar user based on genre preferences and recommends a high-rated movie.
     * @param usuarioObjetivo The user requesting the recommendation.
     * @param baseUsuarios The list of available users.
     * @param catalogo The list of available movies.
     * @return A recommended Movie, or null if no correlation is found.
     */
    public Movie recomendarPorSimilitud(User usuarioObjetivo, List<User> baseUsuarios, List<Movie> catalogo) {
        User usuarioSimilar = null;
        
        for (User otroUsuario : baseUsuarios) {
            if (!otroUsuario.id.equals(usuarioObjetivo.id) && 
                 otroUsuario.generoFavorito.equalsIgnoreCase(usuarioObjetivo.generoFavorito)) {
                usuarioSimilar = otroUsuario;
                break;
            }
        }

        if (usuarioSimilar != null) {
            for (Movie peli : catalogo) {
                if (peli.genero.equalsIgnoreCase(usuarioSimilar.generoFavorito) && peli.calificacion > 4.5) {
                    return peli;
                }
            }
        }
        
        return null;
    }
}