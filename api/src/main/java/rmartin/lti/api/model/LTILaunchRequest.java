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
    private String publicId;
    @JsonIgnore
    @ManyToOne
    private LTIContext myContext;
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
    @JsonProperty("user_image")
    private String userImage;
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
    @JsonProperty("tool_consumer_instance_url")
    private String toolConsumerInstanceUrl;
    @JsonProperty("tool_consumer_instance_contact_email")
    private String toolConsumerInstanceContactEmail;
    /**
     * iframe, new window wtc
     */
    @JsonProperty("launch_presentation_document_target")
    private String launchPresentationTarget;
    @JsonProperty("launch_presentation_return_url")
    private String returnUrl;
    @JsonProperty("launch_presentation_css_url")
    private String launchPresentationCssUrl;
    @JsonProperty("instructor")
    private boolean instructor;
    @JsonIgnore
    @ElementCollection
    @Column(length = 170)
    private Map<String, String> customParams;

    protected LTILaunchRequest() {
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public LTIContext getMyContext() {
        return myContext;
    }

    public void setMyContext(LTIContext myContext) {
        this.myContext = myContext;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getToolConsumerInstanceUrl() {
        return toolConsumerInstanceUrl;
    }

    public void setToolConsumerInstanceUrl(String toolConsumerInstanceUrl) {
        this.toolConsumerInstanceUrl = toolConsumerInstanceUrl;
    }

    public String getToolConsumerInstanceContactEmail() {
        return toolConsumerInstanceContactEmail;
    }

    public void setToolConsumerInstanceContactEmail(String toolConsumerInstanceContactEmail) {
        this.toolConsumerInstanceContactEmail = toolConsumerInstanceContactEmail;
    }

    public String getLaunchPresentationCssUrl() {
        return launchPresentationCssUrl;
    }

    public void setLaunchPresentationCssUrl(String launchPresentationCssUrl) {
        this.launchPresentationCssUrl = launchPresentationCssUrl;
    }

    @Override
    public String toString() {
        return "LTILaunchRequest{" +
                "id=" + id +
                ", publicId='" + publicId + '\'' +
                ", myContext=" + myContext +
                ", ltiVersion='" + ltiVersion + '\'' +
                ", ltiMessageType='" + ltiMessageType + '\'' +
                ", oauthVersion=" + oauthVersion +
                ", oauthNonce='" + oauthNonce + '\'' +
                ", oauthTimestamp=" + oauthTimestamp +
                ", oauthCallback='" + oauthCallback + '\'' +
                ", oauthSignatureMethod='" + oauthSignatureMethod + '\'' +
                ", oauthSignature='" + oauthSignature + '\'' +
                ", oauthConsumerKey='" + oauthConsumerKey + '\'' +
                ", userId='" + userId + '\'' +
                ", userImage='" + userImage + '\'' +
                ", lisPersonSourceId='" + lisPersonSourceId + '\'' +
                ", roles='" + roles + '\'' +
                ", contextId='" + contextId + '\'' +
                ", context_label='" + context_label + '\'' +
                ", context_title='" + context_title + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityDescription='" + activityDescription + '\'' +
                ", activityId='" + activityId + '\'' +
                ", contextType='" + contextType + '\'' +
                ", courseSectionSourceId='" + courseSectionSourceId + '\'' +
                ", lisCourseOfferingSourceId='" + lisCourseOfferingSourceId + '\'' +
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
                ", toolConsumerInstanceUrl='" + toolConsumerInstanceUrl + '\'' +
                ", toolConsumerInstanceContactEmail='" + toolConsumerInstanceContactEmail + '\'' +
                ", launchPresentationTarget='" + launchPresentationTarget + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", launchPresentationCssUrl='" + launchPresentationCssUrl + '\'' +
                ", instructor=" + instructor +
                ", customParams=" + customParams +
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

    public void setCourseSectionSourceId(String courseSectionSourceId) {
        this.courseSectionSourceId = courseSectionSourceId;
    }

    public String getLisCourseOfferingSourceId() {
        return lisCourseOfferingSourceId;
    }

    public void setLisCourseOfferingSourceId(String lisCourseOfferingSourceId) {
        this.lisCourseOfferingSourceId = lisCourseOfferingSourceId;
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

    private void checkHost(String s, String name) {
        if (s.toLowerCase().contains("://localhost")) {
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
                Objects.equals(publicId, that.publicId) &&
                Objects.equals(myContext, that.myContext) &&
                Objects.equals(ltiVersion, that.ltiVersion) &&
                Objects.equals(ltiMessageType, that.ltiMessageType) &&
                Objects.equals(oauthNonce, that.oauthNonce) &&
                Objects.equals(oauthCallback, that.oauthCallback) &&
                Objects.equals(oauthSignatureMethod, that.oauthSignatureMethod) &&
                Objects.equals(oauthSignature, that.oauthSignature) &&
                Objects.equals(oauthConsumerKey, that.oauthConsumerKey) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(userImage, that.userImage) &&
                Objects.equals(lisPersonSourceId, that.lisPersonSourceId) &&
                Objects.equals(roles, that.roles) &&
                Objects.equals(contextId, that.contextId) &&
                Objects.equals(context_label, that.context_label) &&
                Objects.equals(context_title, that.context_title) &&
                Objects.equals(activityName, that.activityName) &&
                Objects.equals(activityDescription, that.activityDescription) &&
                Objects.equals(activityId, that.activityId) &&
                Objects.equals(contextType, that.contextType) &&
                Objects.equals(courseSectionSourceId, that.courseSectionSourceId) &&
                Objects.equals(lisCourseOfferingSourceId, that.lisCourseOfferingSourceId) &&
                Objects.equals(resultSourceId, that.resultSourceId) &&
                Objects.equals(outcomeURL, that.outcomeURL) &&
                Objects.equals(personName, that.personName) &&
                Objects.equals(personSurname, that.personSurname) &&
                Objects.equals(personFullName, that.personFullName) &&
                Objects.equals(personUsername, that.personUsername) &&
                Objects.equals(personEmail, that.personEmail) &&
                Objects.equals(lang, that.lang) &&
                Objects.equals(lmsName, that.lmsName) &&
                Objects.equals(lmsFamilyName, that.lmsFamilyName) &&
                Objects.equals(lmsVersion, that.lmsVersion) &&
                Objects.equals(lmsInstanceGuid, that.lmsInstanceGuid) &&
                Objects.equals(lmsInstanceName, that.lmsInstanceName) &&
                Objects.equals(lmsInstanceDescription, that.lmsInstanceDescription) &&
                Objects.equals(toolConsumerInstanceUrl, that.toolConsumerInstanceUrl) &&
                Objects.equals(toolConsumerInstanceContactEmail, that.toolConsumerInstanceContactEmail) &&
                Objects.equals(launchPresentationTarget, that.launchPresentationTarget) &&
                Objects.equals(returnUrl, that.returnUrl) &&
                Objects.equals(launchPresentationCssUrl, that.launchPresentationCssUrl) &&
                Objects.equals(customParams, that.customParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publicId, myContext, ltiVersion, ltiMessageType, oauthVersion, oauthNonce, oauthTimestamp, oauthCallback, oauthSignatureMethod, oauthSignature, oauthConsumerKey, userId, userImage, lisPersonSourceId, roles, contextId, context_label, context_title, activityName, activityDescription, activityId, contextType, courseSectionSourceId, lisCourseOfferingSourceId, resultSourceId, outcomeURL, personName, personSurname, personFullName, personUsername, personEmail, lang, lmsName, lmsFamilyName, lmsVersion, lmsInstanceGuid, lmsInstanceName, lmsInstanceDescription, toolConsumerInstanceUrl, toolConsumerInstanceContactEmail, launchPresentationTarget, returnUrl, launchPresentationCssUrl, instructor, customParams);
    }
}


