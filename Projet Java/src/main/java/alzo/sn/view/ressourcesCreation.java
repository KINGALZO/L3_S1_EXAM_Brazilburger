package alzo.sn.view;

import java.util.Scanner;


import alzo.sn.config.factory.services.ServicesFactory;
import alzo.sn.entity.*;

import alzo.sn.services.*;
import java.util.List;

public class ressourcesCreation {
     private static Scanner scanner = new Scanner(System.in);
    private ressourcesCreation(){
    }
    private static BurgerServices burgerServices=(BurgerServices)ServicesFactory.createServices(entityName.BURGER);
    private static List<Burger> existingBurgers = burgerServices.selectAll();
    private static MenuServices menuServices=(MenuServices)ServicesFactory.createServices(entityName.MENU);
    private static ClientServices clientServices=(ClientServices)ServicesFactory.createServices(entityName.CLIENT);
    private static List<Client> existingClients = clientServices.selectAll();
    private static ZoneServices zoneServices=(ZoneServices)ServicesFactory.createServices(entityName.ZONE);
    private static List<Zone> existingZones = zoneServices.selectAll();
    private static ComplementServices complementServices=(ComplementServices)ServicesFactory.createServices(entityName.COMPLEMENT);
    private static List<Complement> existingComplements = complementServices.selectAll();
    public static int menu(){
        System.out.println("MENU");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Voir liste d\'une ressource");
        System.out.println("3. Quitter");
        System.out.println("Faites votre choix: ");
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }
    public static entityName selectEntity() {
    
    int choix;

    do {
        System.out.println("Choisissez une ressource :");
        for (int i = 0; i < entityName.values().length; i++) {
            System.out.println((i + 1) + ". " + entityName.values()[i]);
        }

        System.out.print("Votre choix : ");

        while (!scanner.hasNextInt()) {
            System.out.println("Entrée invalide. Entrez un numéro.");
            scanner.next();  
        }

        choix = scanner.nextInt();

        if (choix < 1 || choix > entityName.values().length) {
            System.out.println("Numéro hors plage. Réessayez.\n");
        }

    } while (choix < 1 || choix > entityName.values().length);

    return entityName.values()[choix - 1];
}
    public static Burger createBurger(){
        Burger burger = new Burger();
        do {
            burger.setName(saisieChaine("Entrez le nom du burger : "));
            
            existingBurgers.stream()
                .filter(b -> b.getName().equalsIgnoreCase(burger.getName()))
                .findFirst()
                .ifPresent(b -> {
                    System.out.println("Un burger avec ce nom existe déjà. Veuillez en choisir un autre.");
                    burger.setName(null); 
                });
        } while (burger.getName() == null);
        burger.setPrice(saisiePrix("Entrez le prix du burger : "));
        System.out.println("Entrez la description du burger : ");
        burger.setDescription(scanner.nextLine());
        System.out.println("Entrez le chemin de l'image du burger : ");
        burger.setImagepath(scanner.nextLine());
        return burger;
    }

    public static Complement createComplement(){
        Complement complement = new Complement();
        complement.setName(selectComplement().toString());
        complement.setPrice(saisiePrix("Entrez le prix du complement : "));
        complement.setImagepath(saisieChaine("Entrez le chemin de l'image du complement : "));
        return complement;
    }
    public static Menu createMenu(){
        Menu menu = new Menu();
        menu.setNom(saisieChaine("Entrez le nom du menu : "));
        menu.setDescription(saisieChaine("Entrez la description du menu : "));
        menu.setImagepath(saisieChaine("Entrez le chemin de l'image du menu : "));
        Burger burger;
        Complement complement;
        String nom;
        do {
            nom = saisieChaine("Entrez le nom du burger : ");
           burger=findBurgerByName(nom);
           if(burger == null){
            System.out.println("Aucun burger trouvé avec ce nom. Veuillez réessayer.");
           }
        } while (burger == null);
        menu.setBurger(burger);
       
        do{ nom = saisieChaine("Entrez le nom du complement : ");
            complement = findComplementByName(nom);
        }while(complement == null);
        menu.setComplement(complement);
        Double prix =burger.getPrice() + complement.getPrice();
        menu.setPrice(prix);
        return menu;
    }

    public static Client createClient() {
        Client client = new Client();
        client.setFirstName(saisieChaine("Entrez le nom du client : "));
        client.setLastName(saisieChaine("Entrez le prenom du client : "));
        String phone;
        Client client1;
        do {
            phone = saisieChaine("Entrez le numero de telephone du client : ");
            client1 = findClientByPhone(phone);
            if(client1 != null){
                System.out.println("Un client avec ce numéro de téléphone existe déjà. Veuillez en choisir un autre.");
            }
        } while (client1 == null);
        client.setPhone(phone);
        client.setEmail(saisieChaine("Entrez l'email du client : "));
        client.setPasswordHash(generatePassword());
        return client;
    }
    public static Zone createZone(){
        Zone zone = new Zone();
        zone.setNom(saisieChaine("Entrez le nom de la zone : "));
        zone.setPrixLivraison(saisiePrix("Entrez le prix de la livraison :"));
        return zone;
    }
    public static Quartier createQuartier(){
        Quartier quartier = new Quartier();
        quartier.setNom(saisieChaine("Entrez le nom du quartier : "));
        Zone zone;
        String nom;
        do {
            nom = saisieChaine("Entrez le nom de la zone : ");
           zone=findZoneByName(nom);
        } while (zone == null);
        quartier.setZone(zone);
        return quartier;
    }

    public static Commande createCommande() {
        Commande commande = new Commande();
        Client client = new Client();
        Complement complement = new Complement();
        Menu menu = new Menu();
        Burger burger = new Burger();
        String var;
        Double prix =0.0;
         {
         do {
           var=saisieChaine("Entrez le numero de telephone du client : ");
           client=findClientByPhone(var);
           if(client == null){
            System.out.println("Aucun client trouvé avec ce numéro de téléphone. Veuillez réessayer.");
           }
        } while (client == null);
        commande.setClient(client); 
        produitType produitType=choixProduit();
        if(produitType==produitType.BURGER){
            do{
                var=saisieChaine("Entrez le nom du burger : ");
                burger = findBurgerByName(var);
                if(burger==null){
                    System.out.println("Aucun burger trouvé avec ce nom. Veuillez réessayer.");
                }
                commande.setBurger(burger);
                prix+=burger.getPrice();
            }while(burger==null);
            affirmation confirmation=choixAffirmation();
            if(confirmation==affirmation.OUI){
                do{
                    var=selectComplement().toString();
                    complement = findComplementByName(var);
                    if(complement==null){
                        System.out.println("Aucun complement trouvé avec ce nom. Veuillez réessayer.");
                    }
                }while(complement==null);
            commande.setComplement(complement);
            prix+=complement.getPrice();
            }
        else {
            do {
                var=saisieChaine("Entrez le nom du menu : ");
                menu=findMenuByName(var);
                if(menu == null){
                    System.out.println("Aucun menu trouvé avec ce nom. Veuillez réessayer.");
                }
            } while (menu == null);     
            commande.setMenu(menu);
            prix+=menu.getPrice();
           }
        commande.setMontantTotal(prix);
        }
         return commande;}
    }
    public static String saisieChaine(String message) {
        String nom;
        while (true) {
            System.out.print(message);
            nom = scanner.nextLine().trim();

            if (nom.isEmpty()) {
                System.out.println("Le nom ne peut pas être vide.");
                continue;
            }

            if (nom.length() < 3) {
                System.out.println("Le nom doit contenir au moins 3 caractères.");
                continue;
            }
            return nom;
        }
    }
    public static double saisiePrix(String message) {
        double price;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                price = scanner.nextDouble();
                scanner.nextLine(); 
                if (price < 0) {
                    System.out.println("Le prix ne peut pas être négatif.");
                    continue;
                }
                return price;
            } else {
                System.out.println("Veuillez entrer un nombre valide pour le prix.");
                scanner.next(); 
            }
        }
    }
    public static nomComplement selectComplement() {
        int choix;

        do {
            System.out.println("Choisissez un complément :");
            for (int i = 0; i < nomComplement.values().length; i++) {
                System.out.println((i + 1) + ". " + nomComplement.values()[i]);
            }

            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrée invalide. Entrez un numéro.");
                scanner.next();  
            }

            choix = scanner.nextInt();

            if (choix < 1 || choix > nomComplement.values().length) {
                System.out.println("Numéro hors plage. Réessayez.\n");
            }

        } while (choix < 1 || choix > nomComplement.values().length);

        return nomComplement.values()[choix - 1];
    }
   public static Burger findBurgerByName(String name) {
    

    for (Burger burger : existingBurgers) {
        if (burger.getName() != null &&
            burger.getName().equalsIgnoreCase(name.trim())) {
            return burger;
        }
    }

    return null;
    }
    public static Zone findZoneByName(String name) {
    

        for (Zone zone : existingZones) {
            if (zone.getNom() != null &&
                zone.getNom().equalsIgnoreCase(name)) {
                return zone;
            }
        }

        return null;
    }
    public static Complement findComplementByName(String name) {
    

        for (Complement complement : existingComplements) {
            if (complement.getName() != null &&
                complement.getName().equalsIgnoreCase(name.trim())) {
                return complement;
            }
        }

        return null;
    }
    public static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder pwd = new StringBuilder();
        java.security.SecureRandom r = new java.security.SecureRandom();

        for (int i = 0; i < 8; i++) {
            pwd.append(chars.charAt(r.nextInt(chars.length())));
        }
        return pwd.toString();
    }
    public String saisirTelephone(Scanner scanner) {
        String telephone;
        String regex = "^(77|76)[0-9]{7}$";

        while (true) {
            System.out.print("Entrez le numéro de téléphone : ");
            telephone = scanner.nextLine().trim();

            if (telephone.matches(regex)) {
                return telephone;
            }

            System.out.println("Numéro invalide. Exemple valide : 771234567");
        }
    
    }

    public static Client findClientByPhone(String phone) {
    

        for (Client client : existingClients) {
            if (client.getPhone() != null &&
                client.getPhone().equalsIgnoreCase(phone.trim())) {
                return client;
            }
        }
        return null;
    }
    
    public static Menu findMenuByName(String name) {
    

        for (Menu menu : menuServices.selectAll()) {
            if (menu.getNom() != null &&
                menu.getNom().equalsIgnoreCase(name.trim())) {
                return menu;
            }
        }
        return null;
    }
    public static produitType choixProduit() {
        int choix;

        do {
            System.out.println("Faites votre choix  :");
            for (int i = 0; i < produitType.values().length; i++) {
                System.out.println((i + 1) + ". " + produitType.values()[i]);
            }
            while (!scanner.hasNextInt()) {
                System.out.println("Entrée invalide. Entrez un numéro.");
                scanner.next();  
            }

            choix = scanner.nextInt();

            if (choix < 1 || choix > produitType.values().length) {
                System.out.println("Numéro hors plage. Réessayez.\n");
            }

        } while (choix < 1 || choix > produitType.values().length);

        return produitType.values()[choix - 1];
    }

    public static affirmation choixAffirmation() {
        int choix;

        do {
            System.out.println("Voulez vous ajouter un complement :");
            for (int i = 0; i < affirmation.values().length; i++) {
                System.out.println((i + 1) + ". " + affirmation.values()[i]);
            }

            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrée invalide. Entrez un numéro.");
                scanner.next();  
            }

            choix = scanner.nextInt();

            if (choix < 1 || choix > affirmation.values().length) {
                System.out.println("Numéro hors plage. Réessayez.\n");
            }

        } while (choix < 1 || choix > affirmation.values().length);

        return affirmation.values()[choix - 1];
    }

    public static void afficherRessource(entityName entity) {
        switch (entity) {
            case BURGER:
                List<Burger> burgers = burgerServices.selectAll();
                System.out.println("Liste des Burgers :");
                for (Burger burger : burgers) {
                    System.out.println(burger);
                }
                break;

            case CLIENT:
                List<Client> clients = clientServices.selectAll();
                System.out.println("Liste des Clients :");
                for (Client client : clients) {
                    System.out.println(client);
                }
                break;

            case ZONE:
                List<Zone> zones = zoneServices.selectAll();
                System.out.println("Liste des Zones :");
                for (Zone zone : zones) {
                    System.out.println(zone);
                }
                break;

            case COMPLEMENT:
                List<Complement> complements = complementServices.selectAll();
                System.out.println("Liste des Complements :");
                for (Complement complement : complements) {
                    System.out.println(complement);
                }
                break;

            default:
                System.out.println("Affichage non implémenté pour cette ressource.");
                break;
        }
    }

    public static entityName choixRessource() {
        int choix;

        do {
            System.out.println("Choisissez une ressource à afficher :");
            for (int i = 0; i < entityName.values().length; i++) {
                System.out.println((i + 1) + ". " + entityName.values()[i]);
            }

            System.out.print("Votre choix : ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrée invalide. Entrez un numéro.");
                scanner.next();  
            }

            choix = scanner.nextInt();

            if (choix < 1 || choix > entityName.values().length) {
                System.out.println("Numéro hors plage. Réessayez.\n");
            }

        } while (choix < 1 || choix > entityName.values().length);

        return entityName.values()[choix - 1];
    }
}
