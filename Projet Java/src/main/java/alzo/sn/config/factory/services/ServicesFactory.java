package alzo.sn.config.factory.services;

import alzo.sn.config.factory.repository.RepositoryFactory;
import alzo.sn.entity.entityName;
import alzo.sn.services.impl.*;
import alzo.sn.repository.*;
public final  class ServicesFactory {
    private ServicesFactory(){}
    public static Object createServices(entityName entity){
        switch (entity) {
           case BURGER:
                BurgerRepository burgerRepo =
                        (BurgerRepository) RepositoryFactory.createRepository(entity);
                return BurgerServicesImpl.getInstance(burgerRepo);

            case COMPLEMENT:
                ComplementRepository complementRepo =
                        (ComplementRepository) RepositoryFactory.createRepository(entity);
                return ComplementServicesImpl.getInstance(complementRepo);

            case MENU:
                MenuRepository menuRepo =
                        (MenuRepository) RepositoryFactory.createRepository(entity);
                return MenuServicesImpl.getInstance(menuRepo);

            case COMMANDE:
                CommandeRepository commandeRepo =
                        (CommandeRepository) RepositoryFactory.createRepository(entity);
                return CommandeServicesImpl.getInstance(commandeRepo);

            case CLIENT:
                ClientRepository clientRepo =
                        (ClientRepository) RepositoryFactory.createRepository(entity);
                return ClientServicesImpl.getInstance(clientRepo);

            case ZONE:
                ZoneRepository zoneRepo =
                        (ZoneRepository) RepositoryFactory.createRepository(entity);
                return ZoneServicesImpl.getInstance(zoneRepo);

            case QUARTIER:
                QuartierRepository quartierRepo =
                        (QuartierRepository) RepositoryFactory.createRepository(entity);
                return QuartierServicesImpl.getInstance(quartierRepo);

            default:
            throw new IllegalArgumentException("Unknow entity: "+ entity);
        }
    }
}
