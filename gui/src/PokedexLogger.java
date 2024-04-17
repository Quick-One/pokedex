import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class PokedexLogger {
    private static final Logger logger = LogManager.getLogger(PokedexLogger.class);

    public static void signIn(){
        User user = User.getInstance();
        logger.info("'%s' signed in".formatted(user));
    }

    public static void signUp(String username, String first_name, String last_name) {
        logger.info("'%s(%s %s)' signed up".formatted(username, first_name, last_name));
    }

    public static void signOut(){
        User user = User.getInstance();
        logger.info("'%s' signed out".formatted(user));
    }

    public static void searchPokemon(Pokemon pokemon){
        User user = User.getInstance();
        logger.info("'%s' searched for '%s'".formatted(user, pokemon));
    }

    public static void deleteRoster(RosterQuery rosterQuery){
        User user = User.getInstance();
        logger.info("'%s' deleted roster '%s'".formatted(user, rosterQuery));
    }

    public static void createRoster(String rosterName){
        User user = User.getInstance();
        logger.info("'%s' created roster '%s'".formatted(user, rosterName));
    }

    public static void updateRoster(RosterQuery rosterQuery, String oldName){
        User user = User.getInstance();
        logger.info("'%s' updated roster '%s' to '%s'".formatted(user, oldName, rosterQuery));
    }

    public static void addPokemonToRoster(Pokemon pokemon, RosterQuery rosterQuery){
        User user = User.getInstance();
        logger.info("'%s' added %s to roster %s".formatted(user, pokemon, rosterQuery));
    }
}
