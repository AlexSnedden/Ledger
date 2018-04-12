package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**Room entity object that represents a behavior. Positivity should be in {-1, 0, 1} to represent
 * negative, neutral, and positive behaviors.
 * Created by Patrick Shannon on 2/5/2018.
 */

@Entity(tableName = "Behaviors")
public class Behavior {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPositivity(int positivity) {
        this.positivity = positivity;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @PrimaryKey
    @NonNull
    public String id;

    public String name;

    // -1 for negative, 0 for neutral, 1 for positive
    public int positivity = 0;

    boolean checked;

    public int getPositivity() {
        return positivity;
    }

    @Override
    public String toString() { return name; }
}
