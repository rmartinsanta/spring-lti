package rmartin.lti.api.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonProperty
    private boolean isDefault;

    @JsonProperty
    private String clientId;

    @JsonProperty
    private String activityProviderId;

    @Transient
    @JsonIgnore
    private Map<String, Object> config = new HashMap<>();

    @JsonProperty
    @JsonBackReference
    private LTIContext context;

    protected ActivityConfig() {}

    /**
     * Create a DEFAULT config that will be used as the base for the other configs
     * @param context current context
     * @param isDefault save as default config for all activities for the current client
     */
    public ActivityConfig(LTIContext context, String activityProviderId, boolean isDefault) {
        this.context = context;
        this.clientId = context.getClient();
        this.activityProviderId = activityProviderId;
        this.isDefault = isDefault;
    }


    @SuppressWarnings("unchecked")
    public <T, E extends Enum<E>> T getValue(E key){
        return (T) config.get(key.toString());
    }

    public <E extends Enum<E>> boolean getBool(E key){
        return getValue(key);
    }

    public <E extends Enum<E>> int getInt(E key) {
        return getValue(key);
    }

    public <E extends Enum<E>> String getString(E key) {
        return getValue(key);
    }

    public <E extends Enum<E>> void setValue(E key, Object value){
        config.put(key.toString(), value);
    }

    @JsonIgnore
    public Set<String> getConfigKeys(){
        return this.config.keySet();
    }

    @Access(AccessType.PROPERTY)
    @JsonProperty
    public String getSerialized(){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writer().writeValueAsString(this.config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSerialized(String serialized){
        ObjectMapper om = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
        try {
            this.config = om.reader(HashMap.class).readValue(serialized);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
