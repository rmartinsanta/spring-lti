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
public class  LTIContext {

    @Id
    @GeneratedValue
    private long id;

    @Transient
    private ActivityConfig config;

    public ActivityConfig getConfig() {
        return config;
    }

    public void setConfig(ActivityConfig config) {
        this.config = config;
    }

    private long creat;

    private long modif;

    private String client;

    private String userId;

    private String roles;

    private String activityProviderName;

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

    public LTIContext(LTILaunchRequest launchRequest, String activityProviderName) {
        this.launchRequests.add(launchRequest);
        launchRequest.setMyContext(this);
        this.creat = Instant.now().toEpochMilli();
        this.modif = Instant.now().toEpochMilli();
        this.resourceId = launchRequest.getActivityId();
        this.client = launchRequest.getOauthConsumerKey();
        this.roles = launchRequest.getRoles();
        this.userId = launchRequest.getUserId();
        this.activityProviderName = activityProviderName;
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

    /**
     * Each context has an unique ID. Unique per user/user_roles/origin/course/activity combination
     * @return current context id
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Creation date for the current context
     * @return creation date (epoch millis)
     */
    public long getCreat() {
        return creat;
    }

    public void setCreat(long creat) {
        this.creat = creat;
    }

    /**
     * Last modified date
     * @return last modified date (epoch millis)
     */
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


    /**
     * Each origin LMS has an unique ID
     * @return the client ID
     */
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    /**
     * Each user is uniquely identified by the origin LMS.
     * @return String id representing the current user. This ID is unique in any given LMS.
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get current user roles, formatted as mandated by the LTI Standard.
     * @return A string with the current user roles, created by the origin LMS.
     */
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

    /**
     * Each context is associated with an origin LMS and an activity destination. Get the current activity name.
     * @return Current activity name.
     */
    public String getActivityProviderName() {
        return activityProviderName;
    }

    public void setActivityProviderName(String activityProviderName) {
        this.activityProviderName = activityProviderName;
    }

    /**
     * Check if the user has the teacher role in the origin LMS.
     * @return True if the current user is a teacher, false otherwise
     */
    @JsonIgnore
    public boolean isTeacher() {
        return this.roles.toLowerCase().contains("instructor");
    }

    /**
     * Check if the user has admin privileges in the origin LMS.
     * @return True if the current user is an admin, false otherwise
     */
    @JsonIgnore
    public boolean isAdmin() {
        return this.roles.toLowerCase().contains("administrator");
    }

    /**
     * Check if the user has admin or teacher privileges in the origin LMS.
     * @return True if the current user is a teacher or an admin
     */
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
