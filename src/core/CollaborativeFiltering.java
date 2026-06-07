package core;

import java.util.List;
import models.Movie;
import models.User;

public class CollaborativeFiltering {
    
    /**
     * Busca un usuario similar y recomienda una película basada en esa similitud.
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