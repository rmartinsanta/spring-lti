package rmartin.lti.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import rmartin.lti.api.exception.InvalidParameterException;

import javax.persistence.*;
import java.util.*;

@Entity
public class LTILaunchRequest {

    @Id
    @GeneratedValue
    private long id;

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    private String publicId;

    @JsonIgnore
    @ManyToOne
    private LTIContext myContext;

    public LTIContext getMyContext() {
        return myContext;
    }

    public void setMyContext(LTIContext myContext) {
        this.myContext = myContext;
    }

    @JsonProperty("lti_version")
    private String ltiVersion;

    @JsonProperty("lti_message_type")
    private String ltiMessageType;

    @JsonProperty("oauth_version")
    private float oauthVersion;

    @JsonProperty("oauth_nonce")
    private String oauthNonce;

    @JsonProperty("oauth_timestamp")
    private long oauthTimestamp;

    @JsonProperty("oauth_callback")
    private String oauthCallback;

    @JsonProperty("oauth_signature_method")
    private String oauthSignatureMethod;

    @JsonProperty("oauth_signature")
    private String oauthSignature;

    @JsonProperty("oauth_consumer_key")
    private String oauthConsumerKey;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("lis_person_sourcedid")
    private String lisPersonSourceId;

    @JsonProperty("roles")
    private String roles;

    @JsonProperty("context_id")
    private String contextId;

    @JsonProperty("context_label")
    private String context_label;

    @JsonProperty("context_title")
    private String context_title;

    @JsonProperty("resource_link_title")
    private String activityName;

    @JsonProperty("resource_link_description")
    private String activityDescription;

    @JsonProperty("resource_link_id")
    private String activityId;

    @JsonProperty("context_type")
    private String contextType;

    @JsonProperty("lis_course_section_sourcedid")
    private String courseSectionSourceId;

    @JsonProperty("lis_course_offering_sourcedid")
    private String lisCourseOfferingSourceId;

    @JsonProperty("lis_result_sourcedid")
    private String resultSourceId;

    @JsonProperty("lis_outcome_service_url")
    private String outcomeURL;

    @JsonProperty("lis_person_name_given")
    private String personName;

    @JsonProperty("lis_person_name_family")
    private String personSurname;

    @JsonProperty("lis_person_name_full")
    private String personFullName;

    @JsonProperty("ext_user_username")
    private String personUsername;

    @JsonProperty("lis_person_contact_email_primary")
    private String personEmail;

    @JsonProperty("launch_presentation_locale")
    private String lang;

    @JsonProperty("ext_lms")
    private String lmsName;

    @JsonProperty("tool_consumer_info_product_family_code")
    private String lmsFamilyName;

    @JsonProperty("tool_consumer_info_version")
    private String lmsVersion;

    @JsonProperty("tool_consumer_instance_guid")
    private String lmsInstanceGuid;

    @JsonProperty("tool_consumer_instance_name")
    private String lmsInstanceName;

    @JsonProperty("tool_consumer_instance_description")
    private String lmsInstanceDescription;

    /**
     * iframe, new window wtc
     */
    @JsonProperty("launch_presentation_document_target")
    private String launchPresentationTarget;

    @JsonProperty("launch_presentation_return_url")
    private String returnUrl;

    @JsonProperty("instructor")
    private boolean instructor;

    @JsonIgnore
    @ElementCollection
    @Column(length = 170)
    private Map<String, String> customParams;

    protected LTILaunchRequest() {}

    @Override
    public String toString() {
        return "LTILaunchRequest{" +
                "userId='" + userId + '\'' +
                ", lisPersonSourceId='" + lisPersonSourceId + '\'' +
                ", roles=" + roles +
                ", contextId='" + contextId + '\'' +
                ", context_label='" + context_label + '\'' +
                ", context_title='" + context_title + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityDescription='" + activityDescription + '\'' +
                ", activityId='" + activityId + '\'' +
                ", contextType='" + contextType + '\'' +
                ", courseSectionSourceId='" + courseSectionSourceId + '\'' +
                ", resultSourceId='" + resultSourceId + '\'' +
                ", outcomeURL='" + outcomeURL + '\'' +
                ", personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", personFullName='" + personFullName + '\'' +
                ", personUsername='" + personUsername + '\'' +
                ", personEmail='" + personEmail + '\'' +
                ", lang='" + lang + '\'' +
                ", lmsName='" + lmsName + '\'' +
                ", lmsFamilyName='" + lmsFamilyName + '\'' +
                ", lmsVersion='" + lmsVersion + '\'' +
                ", lmsInstanceGuid='" + lmsInstanceGuid + '\'' +
                ", lmsInstanceName='" + lmsInstanceName + '\'' +
                ", lmsInstanceDescription='" + lmsInstanceDescription + '\'' +
                ", launchPresentationTarget='" + launchPresentationTarget + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                '}';
    }

    @JsonIgnore
    public Map.Entry<String, Object>[] toArray() {
        // Black Magic, ignore compiler warnings
        Map.Entry<String, Object>[] data = (Map.Entry<String, Object>[]) new ObjectMapper().convertValue(this, HashMap.class)
                .entrySet()
                .stream()
                .filter(e -> !((Map.Entry) e).getKey().equals("launchRequests"))
                .toArray(Map.Entry[]::new);
        // Standard magic
        Arrays.sort(data, Comparator.comparing(Map.Entry::getKey));
        return data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLtiVersion() {
        return ltiVersion;
    }

    public void setLtiVersion(String ltiVersion) {
        this.ltiVersion = ltiVersion;
    }

    public String getLtiMessageType() {
        return ltiMessageType;
    }

    public void setLtiMessageType(String ltiMessageType) {
        this.ltiMessageType = ltiMessageType;
    }

    public float getOauthVersion() {
        return oauthVersion;
    }

    public void setOauthVersion(float oauthVersion) {
        this.oauthVersion = oauthVersion;
    }

    public String getOauthNonce() {
        return oauthNonce;
    }

    public void setOauthNonce(String oauthNonce) {
        this.oauthNonce = oauthNonce;
    }

    public long getOauthTimestamp() {
        return oauthTimestamp;
    }

    public void setOauthTimestamp(long oauthTimestamp) {
        this.oauthTimestamp = oauthTimestamp;
    }

    public String getOauthCallback() {
        return oauthCallback;
    }

    public void setOauthCallback(String oauthCallback) {
        this.oauthCallback = oauthCallback;
    }

    public String getOauthSignatureMethod() {
        return oauthSignatureMethod;
    }

    public void setOauthSignatureMethod(String oauthSignatureMethod) {
        this.oauthSignatureMethod = oauthSignatureMethod;
    }

    public String getOauthSignature() {
        return oauthSignature;
    }

    public void setOauthSignature(String oauthSignature) {
        this.oauthSignature = oauthSignature;
    }

    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    public void setOauthConsumerKey(String oauthConsumerKey) {
        this.oauthConsumerKey = oauthConsumerKey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLisPersonSourceId() {
        return lisPersonSourceId;
    }

    public void setLisPersonSourceId(String lisPersonSourceId) {
        this.lisPersonSourceId = lisPersonSourceId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getContext_label() {
        return context_label;
    }

    public void setContext_label(String context_label) {
        this.context_label = context_label;
    }

    public String getContext_title() {
        return context_title;
    }

    public void setContext_title(String context_title) {
        this.context_title = context_title;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public String getCourseSectionSourceId() {
        return courseSectionSourceId;
    }

    public String getLisCourseOfferingSourceId() {
        return lisCourseOfferingSourceId;
    }

    public void setLisCourseOfferingSourceId(String lisCourseOfferingSourceId) {
        this.lisCourseOfferingSourceId = lisCourseOfferingSourceId;
    }

    public void setCourseSectionSourceId(String courseSectionSourceId) {
        this.courseSectionSourceId = courseSectionSourceId;
    }

    public String getResultSourceId() {
        return resultSourceId;
    }

    public void setResultSourceId(String resultSourceId) {
        this.resultSourceId = resultSourceId;
    }

    public String getOutcomeURL() {
        return outcomeURL;
    }

    public void setOutcomeURL(String outcomeURL) {
        this.outcomeURL = outcomeURL;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public String getPersonFullName() {
        return personFullName;
    }

    public void setPersonFullName(String personFullName) {
        this.personFullName = personFullName;
    }

    public String getPersonUsername() {
        return personUsername;
    }

    public void setPersonUsername(String personUsername) {
        this.personUsername = personUsername;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLmsName() {
        return lmsName;
    }

    public void setLmsName(String lmsName) {
        this.lmsName = lmsName;
    }

    public String getLmsFamilyName() {
        return lmsFamilyName;
    }

    public void setLmsFamilyName(String lmsFamilyName) {
        this.lmsFamilyName = lmsFamilyName;
    }

    public String getLmsVersion() {
        return lmsVersion;
    }

    public void setLmsVersion(String lmsVersion) {
        this.lmsVersion = lmsVersion;
    }

    public String getLmsInstanceGuid() {
        return lmsInstanceGuid;
    }

    public void setLmsInstanceGuid(String lmsInstanceGuid) {
        this.lmsInstanceGuid = lmsInstanceGuid;
    }

    public String getLmsInstanceName() {
        return lmsInstanceName;
    }

    public void setLmsInstanceName(String lmsInstanceName) {
        this.lmsInstanceName = lmsInstanceName;
    }

    public String getLmsInstanceDescription() {
        return lmsInstanceDescription;
    }

    public void setLmsInstanceDescription(String lmsInstanceDescription) {
        this.lmsInstanceDescription = lmsInstanceDescription;
    }

    public String getLaunchPresentationTarget() {
        return launchPresentationTarget;
    }

    public void setLaunchPresentationTarget(String launchPresentationTarget) {
        this.launchPresentationTarget = launchPresentationTarget;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Deprecated
    public boolean isInstructor() {
        return instructor;
    }

    @Deprecated
    public void setInstructor(boolean instructor) {
        this.instructor = instructor;
    }

    @JsonIgnore
    public boolean isTeacher(){
        return this.roles.toLowerCase().contains("instructor");
    }

    @JsonIgnore
    public boolean isAdmin(){
        return this.roles.toLowerCase().contains("administrator");
    }

    @JsonIgnore
    public boolean isPrivileged(){
        return isAdmin() || isTeacher();
    }


    public Map<String, String> getCustomParams() {
        return Map.copyOf(customParams);
    }

    public void setCustomParams(Map<String, String> customParams) {
        this.customParams = customParams;
    }

    public void validate() throws RuntimeException {
        checkHost(this.outcomeURL, "outcomeURL");
        checkHost(this.returnUrl, "returnURL");
    }

    private void checkHost(String s, String name){
        if(s.toLowerCase().contains("://localhost")){
            throw new InvalidParameterException("Field " + name + " contains /localhost/. Access the LMS using a FQDN or an IP instead of 'localhost', or cute kittens will die.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LTILaunchRequest that = (LTILaunchRequest) o;
        return id == that.id &&
                Float.compare(that.oauthVersion, oauthVersion) == 0 &&
                oauthTimestamp == that.oauthTimestamp &&
                instructor == that.instructor &&
                publicId.equals(that.publicId) &&
                myContext.equals(that.myContext) &&
                ltiVersion.equals(that.ltiVersion) &&
                ltiMessageType.equals(that.ltiMessageType) &&
                oauthNonce.equals(that.oauthNonce) &&
                oauthCallback.equals(that.oauthCallback) &&
                oauthSignatureMethod.equals(that.oauthSignatureMethod) &&
                oauthSignature.equals(that.oauthSignature) &&
                oauthConsumerKey.equals(that.oauthConsumerKey) &&
                userId.equals(that.userId) &&
                lisPersonSourceId.equals(that.lisPersonSourceId) &&
                roles.equals(that.roles) &&
                contextId.equals(that.contextId) &&
                context_label.equals(that.context_label) &&
                context_title.equals(that.context_title) &&
                activityName.equals(that.activityName) &&
                activityDescription.equals(that.activityDescription) &&
                activityId.equals(that.activityId) &&
                contextType.equals(that.contextType) &&
                courseSectionSourceId.equals(that.courseSectionSourceId) &&
                resultSourceId.equals(that.resultSourceId) &&
                outcomeURL.equals(that.outcomeURL) &&
                personName.equals(that.personName) &&
                personSurname.equals(that.personSurname) &&
                personFullName.equals(that.personFullName) &&
                personUsername.equals(that.personUsername) &&
                personEmail.equals(that.personEmail) &&
                lang.equals(that.lang) &&
                lmsName.equals(that.lmsName) &&
                lmsFamilyName.equals(that.lmsFamilyName) &&
                lmsVersion.equals(that.lmsVersion) &&
                lmsInstanceGuid.equals(that.lmsInstanceGuid) &&
                lmsInstanceName.equals(that.lmsInstanceName) &&
                lmsInstanceDescription.equals(that.lmsInstanceDescription) &&
                launchPresentationTarget.equals(that.launchPresentationTarget) &&
                returnUrl.equals(that.returnUrl) &&
                customParams.equals(that.customParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publicId, myContext,
                ltiVersion, ltiMessageType, oauthVersion,
                oauthNonce, oauthTimestamp, oauthCallback,
                oauthSignatureMethod, oauthSignature,
                oauthConsumerKey, userId, lisPersonSourceId,
                roles, contextId, context_label, context_title,
                activityName, activityDescription, activityId,
                contextType, courseSectionSourceId, resultSourceId,
                outcomeURL, personName, personSurname, personFullName,
                personUsername, personEmail, lang, lmsName,
                lmsFamilyName, lmsVersion, lmsInstanceGuid,
                lmsInstanceName, lmsInstanceDescription,
                launchPresentationTarget, returnUrl,
                instructor, customParams
        );
    }
}


