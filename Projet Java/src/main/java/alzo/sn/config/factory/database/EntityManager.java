package alzo.sn.config.factory.database;
import java.util.Map;
import java.util.HashMap;
public final class EntityManager {
    private EntityManager(){}
    public static Map<String, String> persistenceUnit(SgbdName sgbdName){
        switch (sgbdName) {
           
            case SgbdName.POSTGRESQL:
                return PersistenceUnitPostgresql();
            default:
                throw new IllegalArgumentException("UNKNOW SGBD"+sgbdName);
        }
    }
   
    private static final Map<String, String> PersistenceUnitPostgresql(){
        Map<String, String> config = new HashMap<>();
        config.put("driver", "org.postgresql.Driver");
        config.put("url", "jdbc:postgresql://ep-silent-sky-a4pcdzks-pooler.us-east-1.aws.neon.tech/brasilBurger?sslmode=require&channel_binding=require\r\n");
        config.put("user", "neondb_owner");
        config.put("pwd", "npg_84wkyrJmIczi");
        return config;
    }
}
