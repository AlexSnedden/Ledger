package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**Room entity object that represents a behavior. Positivity should be in {-1, 0, 1} to represent
 * negative, neutral, and positive behaviors.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(tableName = "Behaviors")
public class Behavior {
    @PrimaryKey
    public int id;

    public String name;

    // -1 for negative, 0 for neutral, 1 for positive
    public int positivity;

    public int getPositivity() {
        return positivity;
    }

}
