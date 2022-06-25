package Processing.Utilits;

import java.io.Serializable;
import java.util.LinkedList;

public class Tag implements Serializable {
    static final long serialVersionUID = 31L;
    //public static HashMap<String, TypeOfFlora> Tags = new LinkedHashMap<>();
    public LinkedList<String> tags = new LinkedList<>();

    public static final String small = "small";
    public static final String big = "big";

}
