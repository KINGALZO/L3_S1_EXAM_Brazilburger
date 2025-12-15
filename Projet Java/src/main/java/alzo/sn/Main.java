package alzo.sn;

import java.sql.Connection;
import alzo.sn.config.factory.database.DatabaseFactory;
import alzo.sn.config.factory.services.ServicesFactory;
import alzo.sn.services.*;
import alzo.sn.view.ressourcesCreation;
import alzo.sn.entity.*;
//import alzo.sn.config.factory.database.EntityManager;
import alzo.sn.config.database.Database;

public class Main {
    public static void main(String[] args) {
        CommandeServices commandeServices = (CommandeServices) ServicesFactory.createServices(entityName.COMMANDE);
        ZoneServices zonesServices = (ZoneServices) ServicesFactory.createServices(entityName.ZONE);
        BurgerServices burgerServices = (BurgerServices) ServicesFactory.createServices(entityName.BURGER);
        ClientServices clientServices = (ClientServices) ServicesFactory.createServices(entityName.CLIENT);
        ComplementServices complementServices = (ComplementServices) ServicesFactory.createServices(entityName.COMPLEMENT);
        MenuServices menuServices = (MenuServices) ServicesFactory.createServices(entityName.MENU);
        QuartierServices quartierServices = (QuartierServices) ServicesFactory.createServices(entityName.QUARTIER);
        
        int choice;
        entityName ressource;
        do {
            choice = ressourcesCreation.menu();
            switch (choice) {
                case 1:
                    ressource = ressourcesCreation.choixRessource();
                    switch (ressource) {
                        case BURGER:
                            Burger burger = ressourcesCreation.createBurger();
                            burgerServices.createBurger(burger);
                            break;
                        case CLIENT:
                            Client client = ressourcesCreation.createClient();
                            clientServices.createClient(client);
                            break;
                        case COMPLEMENT:
                            Complement complement = ressourcesCreation.createComplement();
                            complementServices.createComplement(complement);
                            break;
                        case MENU:
                            Menu menu = ressourcesCreation.createMenu();
                            menuServices.insert(menu);
                            break;
                        case ZONE:
                            Zone zone = ressourcesCreation.createZone();
                            zonesServices.insert(zone);
                            break;
                        case QUARTIER:
                            Quartier quartier = ressourcesCreation.createQuartier();
                            quartierServices.insert(quartier);
                            break;
                    
                        default:
                            break;
                    }
                    break;
                case 2:
                    ressource = ressourcesCreation.choixRessource();
                    ressourcesCreation.afficherRessource(ressource);
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
    }
        }  
      while (choice != 3);
    }
}

    
