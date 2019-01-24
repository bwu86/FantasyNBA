package models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface Saveable {
    void save() throws IOException;
}
