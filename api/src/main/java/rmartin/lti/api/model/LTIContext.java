package rmartin.lti.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import rmartin.lti.api.model.enums.ContextStatus;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class LTIContext {

    @Id
    @GeneratedValue
    private long id;

    @Transient
    @JsonManagedReference
    private ActivityConfig config;

    @Transient
    public ActivityConfig getConfig() {
        return config;
    }

    @Transient
    public void setConfig(ActivityConfig config) {
        this.config = config;
    }

    private long creat;

    private long modif;

    private String client;

    private String userId;

    private String roles;

    private ContextStatus status = ContextStatus.NOT_SET;

    @OneToMany(cascade = CascadeType.ALL)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<ContextResult> results = new ArrayList<>();

    /**
     * Activity, context or resource id assigned by the consumer, unique in the consumer context
     */
    private String resourceId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "myContext")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<LTILaunchRequest> launchRequests = new ArrayList<>();

    protected LTIContext() {
    }

    public LTIContext(LTILaunchRequest launchRequest) {
        this.launchRequests.add(launchRequest);
        launchRequest.setMyContext(this);
        this.creat = Instant.now().toEpochMilli();
        this.modif = Instant.now().toEpochMilli();
        this.resourceId = launchRequest.getActivityId();
        this.client = launchRequest.getOauthConsumerKey();
        this.roles = launchRequest.getRoles();
        this.userId = launchRequest.getUserId();
    }


    public ContextStatus getStatus() {
        return status;
    }

    public void setStatus(ContextStatus status) {
        this.status = status;
    }

    public void addResult(ContextResult result) {
        this.results.add(result);
        this.status = ContextStatus.SCORE_SUBMITTED;
    }

    public List<ContextResult> getResults() {
        return List.copyOf(results);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreat() {
        return creat;
    }

    public void setCreat(long creat) {
        this.creat = creat;
    }

    public long getModified() {
        return modif;
    }

    public void setModified(long modified) {
        this.modif = modified;
    }

    public List<LTILaunchRequest> getLaunchRequests() {
        return new ArrayList<>(launchRequests);
    }

    public void setLaunchRequests(Collection<LTILaunchRequest> requests) {
        this.launchRequests = new ArrayList<>(requests);
        for (LTILaunchRequest launchRequest : this.launchRequests) {
            launchRequest.setMyContext(this);
        }
    }

    public void addLaunchRequest(LTILaunchRequest launchRequests) {
        this.launchRequests.add(launchRequests);
        launchRequests.setMyContext(this);
    }

    @JsonIgnore
    public LTILaunchRequest getLastRequest() {
        return this.launchRequests.get(this.launchRequests.size() - 1);
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @JsonIgnore
    public boolean isTeacher() {
        return this.roles.toLowerCase().contains("instructor");
    }

    @JsonIgnore
    public boolean isAdmin() {
        return this.roles.toLowerCase().contains("administrator");
    }

    @JsonIgnore
    public boolean isPrivileged() {
        return isAdmin() || isTeacher();
    }

    @Override
    public String toString() {
        return "LaunchContext{" +
                "id=" + id +
                ", creat=" + creat +
                ", modif=" + modif +
                ", client='" + client + '\'' +
                ", userId='" + userId + '\'' +
                ", roles='" + roles + '\'' +
                ", status=" + status +
                ", results=" + results +
                ", resourceId='" + resourceId + '\'' +
                ", launchRequests=\n\n" + launchRequests + "\n\n" +
                ", teacher=" + isTeacher() +
                ", admin=" + isAdmin() +
                ", privileged=" + isPrivileged() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LTIContext that = (LTIContext) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
