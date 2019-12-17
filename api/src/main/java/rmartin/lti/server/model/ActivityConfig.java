package rmartin.lti.server.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import org.codehaus.jackson.map.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Access(AccessType.FIELD)
public final class ActivityConfig {

    @Id
    @GeneratedValue
    @JsonProperty
    private long id;

    @Transient
    @JsonIgnore
    private Map<String, Object> config = new HashMap<>();

    @JsonProperty
    private String resourceId;

    @JsonProperty
    private String clientId;

    protected ActivityConfig() {}

    public ActivityConfig(String clientId, String resourceId) {
        this.resourceId = resourceId;
        this.clientId = clientId;
        config.put(BaseConfig.CAN_RETRY, true);

    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String key){
        return (T) config.get(key);
    }

    public boolean getBool(String key){
        return getValue(key);
    }

    public int getInt(String key) {
        return getValue(key);
    }

    public String getString(String key) {
        return getValue(key);
    }

    public void setValue(String key, Object value){
        config.put(key, value);
    }

    @JsonIgnore
    public Set<String> getConfigKeys(){
        return this.config.keySet();
    }

    @Access(AccessType.PROPERTY)
    @JsonProperty
    public String getSerialized(){
        // TODO Declare an ObjectMapper as a Bean and use Dependency Inyection
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writer().writeValueAsString(this.config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSerialized(String serialized){
        // TODO Declare an ObjectMapper as a Bean and use Dependency Inyection
        ObjectMapper om = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
        try {
            this.config = om.reader(HashMap.class).readValue(serialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
