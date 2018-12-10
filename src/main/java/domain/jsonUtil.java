package domain;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class jsonUtil {

    public void parseJson(result data) throws IOException {
        JsonReader reader = new JsonReader(new StringReader(data.toString()));
        reader.beginArray();
        while(reader.hasNext()){
            String tagName = reader.nextName();
            if("toTable".equals(tagName)){
                System.out.println(reader.nextString());
            }else if("fromTables".equals(tagName)){
                System.out.println(reader.nextString());
            }
        }
        reader.endObject();
    }
}
