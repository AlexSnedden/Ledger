package net.pinaz993.ledger;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by A.J. on 2/21/18.
 */

@Entity
public class Classes {
    @PrimaryKey
    @NonNull
    String id;
}
