package alzo.sn.config.factory.repository;
import alzo.sn.config.database.Database;
import alzo.sn.config.factory.database.DatabaseFactory;
import alzo.sn.entity.*;
import alzo.sn.repository.Impl.*;


public final class RepositoryFactory {
    
    private RepositoryFactory(){
    }
    
    
    public static Object createRepository(entityName entity){
        Database database = DatabaseFactory.getInstance();
        switch (entity) {
            
            case BURGER:
                return BurgerRepositoryImpl.getInstance(database);

        case COMPLEMENT:
            return ComplementRepositoryImpl.getInstance(database);
        
        case MENU:
            return MenuRepositoryImpl.getInstance(database);
        
        case CLIENT:
            return ClientRepositoryImpl.getInstance(database);
        case ZONE:
            return ZoneRepositoryImpl.getInstance(database);

        case QUARTIER:
            return QuartierRepositoryImpl.getInstance(database);

        case COMMANDE:
            return CommandeRepositoryImpl.getInstance(database);
       

            default:
            throw new IllegalArgumentException("Unknon Storage: ");
        }
    }
}
