package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by A.J. on 2/21/18.
 */

@Entity(tableName = "Classes")
public class Class {
    @PrimaryKey
    @NonNull
    String id;

    public void setId(String id) {
        this.id = id;
    }
}
