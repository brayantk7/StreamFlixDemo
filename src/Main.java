import core.CollaborativeFiltering;
import core.DecisionTreeNode;
import java.util.Arrays;
import java.util.List;
import models.Movie;
import models.User;

/**
 * Entry point for the StreamFlix AI Recommendation System.
 * Executes the initialization, processing, and performance benchmarking 
 * of the Decision Tree and Collaborative Filtering models.
 */
public class Main {

    /**
     * Main execution method.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("=== StreamFlix AI Recommendation System ===");

        User targetUser = new User("U001", "Brayan", 19, "Horror");
        User similarUser = new User("U002", "Carlos", 22, "Horror");
        
        List<User> userBase = Arrays.asList(targetUser, similarUser);
        
        Movie m1 = new Movie("M1", "Kingdom", "Horror", 4.8);
        Movie m2 = new Movie("M2", "Stranger Things", "Ciencia Ficcion", 4.5);
        Movie m3 = new Movie("M3", "Toy Story", "Animacion", 4.9);
        
        List<Movie> catalog = Arrays.asList(m1, m2, m3);

        DecisionTreeNode leafKingdom = new DecisionTreeNode(m1);
        DecisionTreeNode leafStranger = new DecisionTreeNode(m2);
        DecisionTreeNode leafToyStory = new DecisionTreeNode(m3);

        DecisionTreeNode genreNode = new DecisionTreeNode("generoFavorito", "Horror", leafKingdom, leafStranger);
        DecisionTreeNode rootAgeNode = new DecisionTreeNode("esMayorDeEdad", "true", genreNode, leafToyStory);

        long startTimeTree = System.nanoTime();
        Movie treeRecommendation = rootAgeNode.evaluar(targetUser);
        long endTimeTree = System.nanoTime();
        
        CollaborativeFiltering cf = new CollaborativeFiltering();
        long startTimeCF = System.nanoTime();
        Movie cfRecommendation = cf.recomendarPorSimilitud(targetUser, userBase, catalog);
        long endTimeCF = System.nanoTime();

        System.out.println("\n[Target User Profile]");
        System.out.println("ID: " + targetUser.id + " | Edad: " + targetUser.edad + " | Preferencia: " + targetUser.generoFavorito);
        
        System.out.println("\n[Decision Tree Engine Result]");
        System.out.println("Recommendation: " + treeRecommendation.titulo);
        System.out.println("Execution Time: " + (endTimeTree - startTimeTree) / 1000000.0 + " ms");
        
        System.out.println("\n[Collaborative Filtering Engine Result]");
        System.out.println("Recommendation: " + (cfRecommendation != null ? cfRecommendation.titulo : "No match found"));
        System.out.println("Execution Time: " + (endTimeCF - startTimeCF) / 1000000.0 + " ms");
    }
}