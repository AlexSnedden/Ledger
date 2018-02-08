package net.pinaz993.ledger;

/**
 * A singleton to provide access to all databases and their respective Daos.
 * Created by A.J. on 2/8/18.
 */

public class DataHandler {
    private static DataHandler instance;
    private DataHandler() {}
    public static DataHandler getDataHandler() {
        if(instance == null) {
            instance = new DataHandler();
        }
        return instance;
    }

    StudentDatabase studentDatabase;
    StudentDao studentDao;
    BehaviorDatabase behaviorDatabase;
    BehaviorDao behaviorDao;
    StudentClassMappingDatabase studentClassMappingDatabase;
    StudentClassMappingDao studentClassMappingDao;

}
