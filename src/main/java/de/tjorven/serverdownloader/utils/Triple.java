package de.tjorven.serverdownloader.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Triple<Key, Value, Value1> {

    private Key key;
    private Value value;
    private Value1 value1;

    public Triple() {}
    public Triple(Key key, Value value, Value1 value1) {
        this.key = key;
        this.value = value;
        this.value1 = value1;
    }

}
