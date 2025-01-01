
package com.github.phanikb.nvd.api2.cve;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * JSON Schema for Common Vulnerability Scoring System version 4.0
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "version",
    "vectorString",
    "attackVector",
    "attackComplexity",
    "attackRequirements",
    "privilegesRequired",
    "userInteraction",
    "vulnConfidentialityImpact",
    "vulnIntegrityImpact",
    "vulnAvailabilityImpact",
    "subConfidentialityImpact",
    "subIntegrityImpact",
    "subAvailabilityImpact",
    "exploitMaturity",
    "confidentialityRequirement",
    "integrityRequirement",
    "availabilityRequirement",
    "modifiedAttackVector",
    "modifiedAttackComplexity",
    "modifiedAttackRequirements",
    "modifiedPrivilegesRequired",
    "modifiedUserInteraction",
    "modifiedVulnConfidentialityImpact",
    "modifiedVulnIntegrityImpact",
    "modifiedVulnAvailabilityImpact",
    "modifiedSubConfidentialityImpact",
    "modifiedSubIntegrityImpact",
    "modifiedSubAvailabilityImpact",
    "Safety",
    "Automatable",
    "Recovery",
    "valueDensity",
    "vulnerabilityResponseEffort",
    "providerUrgency"
})
public class CvssV40__1 {

    /**
     * CVSS Version
     * (Required)
     * 
     */
    @JsonProperty("version")
    @JsonPropertyDescription("CVSS Version")
    private CvssV40__1 .Version version;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vectorString")
    private String vectorString;
    @JsonProperty("attackVector")
    private CvssV40__1 .AttackVectorType attackVector;
    @JsonProperty("attackComplexity")
    private CvssV40__1 .AttackComplexityType attackComplexity;
    @JsonProperty("attackRequirements")
    private CvssV40__1 .AttackRequirementsType attackRequirements;
    @JsonProperty("privilegesRequired")
    private CvssV40__1 .PrivilegesRequiredType privilegesRequired;
    @JsonProperty("userInteraction")
    private CvssV40__1 .UserInteractionType userInteraction;
    @JsonProperty("vulnConfidentialityImpact")
    private CvssV40__1 .VulnCiaType vulnConfidentialityImpact;
    @JsonProperty("vulnIntegrityImpact")
    private CvssV40__1 .VulnCiaType vulnIntegrityImpact;
    @JsonProperty("vulnAvailabilityImpact")
    private CvssV40__1 .VulnCiaType vulnAvailabilityImpact;
    @JsonProperty("subConfidentialityImpact")
    private CvssV40__1 .SubCiaType subConfidentialityImpact;
    @JsonProperty("subIntegrityImpact")
    private CvssV40__1 .SubCiaType subIntegrityImpact;
    @JsonProperty("subAvailabilityImpact")
    private CvssV40__1 .SubCiaType subAvailabilityImpact;
    @JsonProperty("exploitMaturity")
    private CvssV40__1 .ExploitMaturityType exploitMaturity = CvssV40__1 .ExploitMaturityType.fromValue("NOT_DEFINED");
    @JsonProperty("confidentialityRequirement")
    private CvssV40__1 .CiaRequirementType confidentialityRequirement = CvssV40__1 .CiaRequirementType.fromValue("NOT_DEFINED");
    @JsonProperty("integrityRequirement")
    private CvssV40__1 .CiaRequirementType integrityRequirement = CvssV40__1 .CiaRequirementType.fromValue("NOT_DEFINED");
    @JsonProperty("availabilityRequirement")
    private CvssV40__1 .CiaRequirementType availabilityRequirement = CvssV40__1 .CiaRequirementType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedAttackVector")
    private CvssV40__1 .ModifiedAttackVectorType modifiedAttackVector = CvssV40__1 .ModifiedAttackVectorType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedAttackComplexity")
    private CvssV40__1 .ModifiedAttackComplexityType modifiedAttackComplexity = CvssV40__1 .ModifiedAttackComplexityType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedAttackRequirements")
    private CvssV40__1 .ModifiedAttackRequirementsType modifiedAttackRequirements = CvssV40__1 .ModifiedAttackRequirementsType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedPrivilegesRequired")
    private CvssV40__1 .ModifiedPrivilegesRequiredType modifiedPrivilegesRequired = CvssV40__1 .ModifiedPrivilegesRequiredType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedUserInteraction")
    private CvssV40__1 .ModifiedUserInteractionType modifiedUserInteraction = CvssV40__1 .ModifiedUserInteractionType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedVulnConfidentialityImpact")
    private CvssV40__1 .ModifiedVulnCiaType modifiedVulnConfidentialityImpact = CvssV40__1 .ModifiedVulnCiaType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedVulnIntegrityImpact")
    private CvssV40__1 .ModifiedVulnCiaType modifiedVulnIntegrityImpact = CvssV40__1 .ModifiedVulnCiaType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedVulnAvailabilityImpact")
    private CvssV40__1 .ModifiedVulnCiaType modifiedVulnAvailabilityImpact = CvssV40__1 .ModifiedVulnCiaType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedSubConfidentialityImpact")
    private CvssV40__1 .ModifiedSubCType modifiedSubConfidentialityImpact = CvssV40__1 .ModifiedSubCType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedSubIntegrityImpact")
    private CvssV40__1 .ModifiedSubIaType modifiedSubIntegrityImpact = CvssV40__1 .ModifiedSubIaType.fromValue("NOT_DEFINED");
    @JsonProperty("modifiedSubAvailabilityImpact")
    private CvssV40__1 .ModifiedSubIaType modifiedSubAvailabilityImpact = CvssV40__1 .ModifiedSubIaType.fromValue("NOT_DEFINED");
    @JsonProperty("Safety")
    private CvssV40__1 .SafetyType safety = CvssV40__1 .SafetyType.fromValue("NOT_DEFINED");
    @JsonProperty("Automatable")
    private CvssV40__1 .AutomatableType automatable = CvssV40__1 .AutomatableType.fromValue("NOT_DEFINED");
    @JsonProperty("Recovery")
    private CvssV40__1 .RecoveryType recovery = CvssV40__1 .RecoveryType.fromValue("NOT_DEFINED");
    @JsonProperty("valueDensity")
    private CvssV40__1 .ValueDensityType valueDensity = CvssV40__1 .ValueDensityType.fromValue("NOT_DEFINED");
    @JsonProperty("vulnerabilityResponseEffort")
    private CvssV40__1 .VulnerabilityResponseEffortType vulnerabilityResponseEffort = CvssV40__1 .VulnerabilityResponseEffortType.fromValue("NOT_DEFINED");
    @JsonProperty("providerUrgency")
    private CvssV40__1 .ProviderUrgencyType providerUrgency = CvssV40__1 .ProviderUrgencyType.fromValue("NOT_DEFINED");
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * CVSS Version
     * (Required)
     * 
     */
    @JsonProperty("version")
    public CvssV40__1 .Version getVersion() {
        return version;
    }

    /**
     * CVSS Version
     * (Required)
     * 
     */
    @JsonProperty("version")
    public void setVersion(CvssV40__1 .Version version) {
        this.version = version;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vectorString")
    public String getVectorString() {
        return vectorString;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("vectorString")
    public void setVectorString(String vectorString) {
        this.vectorString = vectorString;
    }

    @JsonProperty("attackVector")
    public CvssV40__1 .AttackVectorType getAttackVector() {
        return attackVector;
    }

    @JsonProperty("attackVector")
    public void setAttackVector(CvssV40__1 .AttackVectorType attackVector) {
        this.attackVector = attackVector;
    }

    @JsonProperty("attackComplexity")
    public CvssV40__1 .AttackComplexityType getAttackComplexity() {
        return attackComplexity;
    }

    @JsonProperty("attackComplexity")
    public void setAttackComplexity(CvssV40__1 .AttackComplexityType attackComplexity) {
        this.attackComplexity = attackComplexity;
    }

    @JsonProperty("attackRequirements")
    public CvssV40__1 .AttackRequirementsType getAttackRequirements() {
        return attackRequirements;
    }

    @JsonProperty("attackRequirements")
    public void setAttackRequirements(CvssV40__1 .AttackRequirementsType attackRequirements) {
        this.attackRequirements = attackRequirements;
    }

    @JsonProperty("privilegesRequired")
    public CvssV40__1 .PrivilegesRequiredType getPrivilegesRequired() {
        return privilegesRequired;
    }

    @JsonProperty("privilegesRequired")
    public void setPrivilegesRequired(CvssV40__1 .PrivilegesRequiredType privilegesRequired) {
        this.privilegesRequired = privilegesRequired;
    }

    @JsonProperty("userInteraction")
    public CvssV40__1 .UserInteractionType getUserInteraction() {
        return userInteraction;
    }

    @JsonProperty("userInteraction")
    public void setUserInteraction(CvssV40__1 .UserInteractionType userInteraction) {
        this.userInteraction = userInteraction;
    }

    @JsonProperty("vulnConfidentialityImpact")
    public CvssV40__1 .VulnCiaType getVulnConfidentialityImpact() {
        return vulnConfidentialityImpact;
    }

    @JsonProperty("vulnConfidentialityImpact")
    public void setVulnConfidentialityImpact(CvssV40__1 .VulnCiaType vulnConfidentialityImpact) {
        this.vulnConfidentialityImpact = vulnConfidentialityImpact;
    }

    @JsonProperty("vulnIntegrityImpact")
    public CvssV40__1 .VulnCiaType getVulnIntegrityImpact() {
        return vulnIntegrityImpact;
    }

    @JsonProperty("vulnIntegrityImpact")
    public void setVulnIntegrityImpact(CvssV40__1 .VulnCiaType vulnIntegrityImpact) {
        this.vulnIntegrityImpact = vulnIntegrityImpact;
    }

    @JsonProperty("vulnAvailabilityImpact")
    public CvssV40__1 .VulnCiaType getVulnAvailabilityImpact() {
        return vulnAvailabilityImpact;
    }

    @JsonProperty("vulnAvailabilityImpact")
    public void setVulnAvailabilityImpact(CvssV40__1 .VulnCiaType vulnAvailabilityImpact) {
        this.vulnAvailabilityImpact = vulnAvailabilityImpact;
    }

    @JsonProperty("subConfidentialityImpact")
    public CvssV40__1 .SubCiaType getSubConfidentialityImpact() {
        return subConfidentialityImpact;
    }

    @JsonProperty("subConfidentialityImpact")
    public void setSubConfidentialityImpact(CvssV40__1 .SubCiaType subConfidentialityImpact) {
        this.subConfidentialityImpact = subConfidentialityImpact;
    }

    @JsonProperty("subIntegrityImpact")
    public CvssV40__1 .SubCiaType getSubIntegrityImpact() {
        return subIntegrityImpact;
    }

    @JsonProperty("subIntegrityImpact")
    public void setSubIntegrityImpact(CvssV40__1 .SubCiaType subIntegrityImpact) {
        this.subIntegrityImpact = subIntegrityImpact;
    }

    @JsonProperty("subAvailabilityImpact")
    public CvssV40__1 .SubCiaType getSubAvailabilityImpact() {
        return subAvailabilityImpact;
    }

    @JsonProperty("subAvailabilityImpact")
    public void setSubAvailabilityImpact(CvssV40__1 .SubCiaType subAvailabilityImpact) {
        this.subAvailabilityImpact = subAvailabilityImpact;
    }

    @JsonProperty("exploitMaturity")
    public CvssV40__1 .ExploitMaturityType getExploitMaturity() {
        return exploitMaturity;
    }

    @JsonProperty("exploitMaturity")
    public void setExploitMaturity(CvssV40__1 .ExploitMaturityType exploitMaturity) {
        this.exploitMaturity = exploitMaturity;
    }

    @JsonProperty("confidentialityRequirement")
    public CvssV40__1 .CiaRequirementType getConfidentialityRequirement() {
        return confidentialityRequirement;
    }

    @JsonProperty("confidentialityRequirement")
    public void setConfidentialityRequirement(CvssV40__1 .CiaRequirementType confidentialityRequirement) {
        this.confidentialityRequirement = confidentialityRequirement;
    }

    @JsonProperty("integrityRequirement")
    public CvssV40__1 .CiaRequirementType getIntegrityRequirement() {
        return integrityRequirement;
    }

    @JsonProperty("integrityRequirement")
    public void setIntegrityRequirement(CvssV40__1 .CiaRequirementType integrityRequirement) {
        this.integrityRequirement = integrityRequirement;
    }

    @JsonProperty("availabilityRequirement")
    public CvssV40__1 .CiaRequirementType getAvailabilityRequirement() {
        return availabilityRequirement;
    }

    @JsonProperty("availabilityRequirement")
    public void setAvailabilityRequirement(CvssV40__1 .CiaRequirementType availabilityRequirement) {
        this.availabilityRequirement = availabilityRequirement;
    }

    @JsonProperty("modifiedAttackVector")
    public CvssV40__1 .ModifiedAttackVectorType getModifiedAttackVector() {
        return modifiedAttackVector;
    }

    @JsonProperty("modifiedAttackVector")
    public void setModifiedAttackVector(CvssV40__1 .ModifiedAttackVectorType modifiedAttackVector) {
        this.modifiedAttackVector = modifiedAttackVector;
    }

    @JsonProperty("modifiedAttackComplexity")
    public CvssV40__1 .ModifiedAttackComplexityType getModifiedAttackComplexity() {
        return modifiedAttackComplexity;
    }

    @JsonProperty("modifiedAttackComplexity")
    public void setModifiedAttackComplexity(CvssV40__1 .ModifiedAttackComplexityType modifiedAttackComplexity) {
        this.modifiedAttackComplexity = modifiedAttackComplexity;
    }

    @JsonProperty("modifiedAttackRequirements")
    public CvssV40__1 .ModifiedAttackRequirementsType getModifiedAttackRequirements() {
        return modifiedAttackRequirements;
    }

    @JsonProperty("modifiedAttackRequirements")
    public void setModifiedAttackRequirements(CvssV40__1 .ModifiedAttackRequirementsType modifiedAttackRequirements) {
        this.modifiedAttackRequirements = modifiedAttackRequirements;
    }

    @JsonProperty("modifiedPrivilegesRequired")
    public CvssV40__1 .ModifiedPrivilegesRequiredType getModifiedPrivilegesRequired() {
        return modifiedPrivilegesRequired;
    }

    @JsonProperty("modifiedPrivilegesRequired")
    public void setModifiedPrivilegesRequired(CvssV40__1 .ModifiedPrivilegesRequiredType modifiedPrivilegesRequired) {
        this.modifiedPrivilegesRequired = modifiedPrivilegesRequired;
    }

    @JsonProperty("modifiedUserInteraction")
    public CvssV40__1 .ModifiedUserInteractionType getModifiedUserInteraction() {
        return modifiedUserInteraction;
    }

    @JsonProperty("modifiedUserInteraction")
    public void setModifiedUserInteraction(CvssV40__1 .ModifiedUserInteractionType modifiedUserInteraction) {
        this.modifiedUserInteraction = modifiedUserInteraction;
    }

    @JsonProperty("modifiedVulnConfidentialityImpact")
    public CvssV40__1 .ModifiedVulnCiaType getModifiedVulnConfidentialityImpact() {
        return modifiedVulnConfidentialityImpact;
    }

    @JsonProperty("modifiedVulnConfidentialityImpact")
    public void setModifiedVulnConfidentialityImpact(CvssV40__1 .ModifiedVulnCiaType modifiedVulnConfidentialityImpact) {
        this.modifiedVulnConfidentialityImpact = modifiedVulnConfidentialityImpact;
    }

    @JsonProperty("modifiedVulnIntegrityImpact")
    public CvssV40__1 .ModifiedVulnCiaType getModifiedVulnIntegrityImpact() {
        return modifiedVulnIntegrityImpact;
    }

    @JsonProperty("modifiedVulnIntegrityImpact")
    public void setModifiedVulnIntegrityImpact(CvssV40__1 .ModifiedVulnCiaType modifiedVulnIntegrityImpact) {
        this.modifiedVulnIntegrityImpact = modifiedVulnIntegrityImpact;
    }

    @JsonProperty("modifiedVulnAvailabilityImpact")
    public CvssV40__1 .ModifiedVulnCiaType getModifiedVulnAvailabilityImpact() {
        return modifiedVulnAvailabilityImpact;
    }

    @JsonProperty("modifiedVulnAvailabilityImpact")
    public void setModifiedVulnAvailabilityImpact(CvssV40__1 .ModifiedVulnCiaType modifiedVulnAvailabilityImpact) {
        this.modifiedVulnAvailabilityImpact = modifiedVulnAvailabilityImpact;
    }

    @JsonProperty("modifiedSubConfidentialityImpact")
    public CvssV40__1 .ModifiedSubCType getModifiedSubConfidentialityImpact() {
        return modifiedSubConfidentialityImpact;
    }

    @JsonProperty("modifiedSubConfidentialityImpact")
    public void setModifiedSubConfidentialityImpact(CvssV40__1 .ModifiedSubCType modifiedSubConfidentialityImpact) {
        this.modifiedSubConfidentialityImpact = modifiedSubConfidentialityImpact;
    }

    @JsonProperty("modifiedSubIntegrityImpact")
    public CvssV40__1 .ModifiedSubIaType getModifiedSubIntegrityImpact() {
        return modifiedSubIntegrityImpact;
    }

    @JsonProperty("modifiedSubIntegrityImpact")
    public void setModifiedSubIntegrityImpact(CvssV40__1 .ModifiedSubIaType modifiedSubIntegrityImpact) {
        this.modifiedSubIntegrityImpact = modifiedSubIntegrityImpact;
    }

    @JsonProperty("modifiedSubAvailabilityImpact")
    public CvssV40__1 .ModifiedSubIaType getModifiedSubAvailabilityImpact() {
        return modifiedSubAvailabilityImpact;
    }

    @JsonProperty("modifiedSubAvailabilityImpact")
    public void setModifiedSubAvailabilityImpact(CvssV40__1 .ModifiedSubIaType modifiedSubAvailabilityImpact) {
        this.modifiedSubAvailabilityImpact = modifiedSubAvailabilityImpact;
    }

    @JsonProperty("Safety")
    public CvssV40__1 .SafetyType getSafety() {
        return safety;
    }

    @JsonProperty("Safety")
    public void setSafety(CvssV40__1 .SafetyType safety) {
        this.safety = safety;
    }

    @JsonProperty("Automatable")
    public CvssV40__1 .AutomatableType getAutomatable() {
        return automatable;
    }

    @JsonProperty("Automatable")
    public void setAutomatable(CvssV40__1 .AutomatableType automatable) {
        this.automatable = automatable;
    }

    @JsonProperty("Recovery")
    public CvssV40__1 .RecoveryType getRecovery() {
        return recovery;
    }

    @JsonProperty("Recovery")
    public void setRecovery(CvssV40__1 .RecoveryType recovery) {
        this.recovery = recovery;
    }

    @JsonProperty("valueDensity")
    public CvssV40__1 .ValueDensityType getValueDensity() {
        return valueDensity;
    }

    @JsonProperty("valueDensity")
    public void setValueDensity(CvssV40__1 .ValueDensityType valueDensity) {
        this.valueDensity = valueDensity;
    }

    @JsonProperty("vulnerabilityResponseEffort")
    public CvssV40__1 .VulnerabilityResponseEffortType getVulnerabilityResponseEffort() {
        return vulnerabilityResponseEffort;
    }

    @JsonProperty("vulnerabilityResponseEffort")
    public void setVulnerabilityResponseEffort(CvssV40__1 .VulnerabilityResponseEffortType vulnerabilityResponseEffort) {
        this.vulnerabilityResponseEffort = vulnerabilityResponseEffort;
    }

    @JsonProperty("providerUrgency")
    public CvssV40__1 .ProviderUrgencyType getProviderUrgency() {
        return providerUrgency;
    }

    @JsonProperty("providerUrgency")
    public void setProviderUrgency(CvssV40__1 .ProviderUrgencyType providerUrgency) {
        this.providerUrgency = providerUrgency;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CvssV40__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
        sb.append(',');
        sb.append("vectorString");
        sb.append('=');
        sb.append(((this.vectorString == null)?"<null>":this.vectorString));
        sb.append(',');
        sb.append("attackVector");
        sb.append('=');
        sb.append(((this.attackVector == null)?"<null>":this.attackVector));
        sb.append(',');
        sb.append("attackComplexity");
        sb.append('=');
        sb.append(((this.attackComplexity == null)?"<null>":this.attackComplexity));
        sb.append(',');
        sb.append("attackRequirements");
        sb.append('=');
        sb.append(((this.attackRequirements == null)?"<null>":this.attackRequirements));
        sb.append(',');
        sb.append("privilegesRequired");
        sb.append('=');
        sb.append(((this.privilegesRequired == null)?"<null>":this.privilegesRequired));
        sb.append(',');
        sb.append("userInteraction");
        sb.append('=');
        sb.append(((this.userInteraction == null)?"<null>":this.userInteraction));
        sb.append(',');
        sb.append("vulnConfidentialityImpact");
        sb.append('=');
        sb.append(((this.vulnConfidentialityImpact == null)?"<null>":this.vulnConfidentialityImpact));
        sb.append(',');
        sb.append("vulnIntegrityImpact");
        sb.append('=');
        sb.append(((this.vulnIntegrityImpact == null)?"<null>":this.vulnIntegrityImpact));
        sb.append(',');
        sb.append("vulnAvailabilityImpact");
        sb.append('=');
        sb.append(((this.vulnAvailabilityImpact == null)?"<null>":this.vulnAvailabilityImpact));
        sb.append(',');
        sb.append("subConfidentialityImpact");
        sb.append('=');
        sb.append(((this.subConfidentialityImpact == null)?"<null>":this.subConfidentialityImpact));
        sb.append(',');
        sb.append("subIntegrityImpact");
        sb.append('=');
        sb.append(((this.subIntegrityImpact == null)?"<null>":this.subIntegrityImpact));
        sb.append(',');
        sb.append("subAvailabilityImpact");
        sb.append('=');
        sb.append(((this.subAvailabilityImpact == null)?"<null>":this.subAvailabilityImpact));
        sb.append(',');
        sb.append("exploitMaturity");
        sb.append('=');
        sb.append(((this.exploitMaturity == null)?"<null>":this.exploitMaturity));
        sb.append(',');
        sb.append("confidentialityRequirement");
        sb.append('=');
        sb.append(((this.confidentialityRequirement == null)?"<null>":this.confidentialityRequirement));
        sb.append(',');
        sb.append("integrityRequirement");
        sb.append('=');
        sb.append(((this.integrityRequirement == null)?"<null>":this.integrityRequirement));
        sb.append(',');
        sb.append("availabilityRequirement");
        sb.append('=');
        sb.append(((this.availabilityRequirement == null)?"<null>":this.availabilityRequirement));
        sb.append(',');
        sb.append("modifiedAttackVector");
        sb.append('=');
        sb.append(((this.modifiedAttackVector == null)?"<null>":this.modifiedAttackVector));
        sb.append(',');
        sb.append("modifiedAttackComplexity");
        sb.append('=');
        sb.append(((this.modifiedAttackComplexity == null)?"<null>":this.modifiedAttackComplexity));
        sb.append(',');
        sb.append("modifiedAttackRequirements");
        sb.append('=');
        sb.append(((this.modifiedAttackRequirements == null)?"<null>":this.modifiedAttackRequirements));
        sb.append(',');
        sb.append("modifiedPrivilegesRequired");
        sb.append('=');
        sb.append(((this.modifiedPrivilegesRequired == null)?"<null>":this.modifiedPrivilegesRequired));
        sb.append(',');
        sb.append("modifiedUserInteraction");
        sb.append('=');
        sb.append(((this.modifiedUserInteraction == null)?"<null>":this.modifiedUserInteraction));
        sb.append(',');
        sb.append("modifiedVulnConfidentialityImpact");
        sb.append('=');
        sb.append(((this.modifiedVulnConfidentialityImpact == null)?"<null>":this.modifiedVulnConfidentialityImpact));
        sb.append(',');
        sb.append("modifiedVulnIntegrityImpact");
        sb.append('=');
        sb.append(((this.modifiedVulnIntegrityImpact == null)?"<null>":this.modifiedVulnIntegrityImpact));
        sb.append(',');
        sb.append("modifiedVulnAvailabilityImpact");
        sb.append('=');
        sb.append(((this.modifiedVulnAvailabilityImpact == null)?"<null>":this.modifiedVulnAvailabilityImpact));
        sb.append(',');
        sb.append("modifiedSubConfidentialityImpact");
        sb.append('=');
        sb.append(((this.modifiedSubConfidentialityImpact == null)?"<null>":this.modifiedSubConfidentialityImpact));
        sb.append(',');
        sb.append("modifiedSubIntegrityImpact");
        sb.append('=');
        sb.append(((this.modifiedSubIntegrityImpact == null)?"<null>":this.modifiedSubIntegrityImpact));
        sb.append(',');
        sb.append("modifiedSubAvailabilityImpact");
        sb.append('=');
        sb.append(((this.modifiedSubAvailabilityImpact == null)?"<null>":this.modifiedSubAvailabilityImpact));
        sb.append(',');
        sb.append("safety");
        sb.append('=');
        sb.append(((this.safety == null)?"<null>":this.safety));
        sb.append(',');
        sb.append("automatable");
        sb.append('=');
        sb.append(((this.automatable == null)?"<null>":this.automatable));
        sb.append(',');
        sb.append("recovery");
        sb.append('=');
        sb.append(((this.recovery == null)?"<null>":this.recovery));
        sb.append(',');
        sb.append("valueDensity");
        sb.append('=');
        sb.append(((this.valueDensity == null)?"<null>":this.valueDensity));
        sb.append(',');
        sb.append("vulnerabilityResponseEffort");
        sb.append('=');
        sb.append(((this.vulnerabilityResponseEffort == null)?"<null>":this.vulnerabilityResponseEffort));
        sb.append(',');
        sb.append("providerUrgency");
        sb.append('=');
        sb.append(((this.providerUrgency == null)?"<null>":this.providerUrgency));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.modifiedSubConfidentialityImpact == null)? 0 :this.modifiedSubConfidentialityImpact.hashCode()));
        result = ((result* 31)+((this.modifiedPrivilegesRequired == null)? 0 :this.modifiedPrivilegesRequired.hashCode()));
        result = ((result* 31)+((this.modifiedVulnIntegrityImpact == null)? 0 :this.modifiedVulnIntegrityImpact.hashCode()));
        result = ((result* 31)+((this.subConfidentialityImpact == null)? 0 :this.subConfidentialityImpact.hashCode()));
        result = ((result* 31)+((this.privilegesRequired == null)? 0 :this.privilegesRequired.hashCode()));
        result = ((result* 31)+((this.recovery == null)? 0 :this.recovery.hashCode()));
        result = ((result* 31)+((this.userInteraction == null)? 0 :this.userInteraction.hashCode()));
        result = ((result* 31)+((this.integrityRequirement == null)? 0 :this.integrityRequirement.hashCode()));
        result = ((result* 31)+((this.availabilityRequirement == null)? 0 :this.availabilityRequirement.hashCode()));
        result = ((result* 31)+((this.automatable == null)? 0 :this.automatable.hashCode()));
        result = ((result* 31)+((this.subAvailabilityImpact == null)? 0 :this.subAvailabilityImpact.hashCode()));
        result = ((result* 31)+((this.providerUrgency == null)? 0 :this.providerUrgency.hashCode()));
        result = ((result* 31)+((this.safety == null)? 0 :this.safety.hashCode()));
        result = ((result* 31)+((this.attackComplexity == null)? 0 :this.attackComplexity.hashCode()));
        result = ((result* 31)+((this.attackVector == null)? 0 :this.attackVector.hashCode()));
        result = ((result* 31)+((this.modifiedVulnAvailabilityImpact == null)? 0 :this.modifiedVulnAvailabilityImpact.hashCode()));
        result = ((result* 31)+((this.exploitMaturity == null)? 0 :this.exploitMaturity.hashCode()));
        result = ((result* 31)+((this.vectorString == null)? 0 :this.vectorString.hashCode()));
        result = ((result* 31)+((this.valueDensity == null)? 0 :this.valueDensity.hashCode()));
        result = ((result* 31)+((this.modifiedSubIntegrityImpact == null)? 0 :this.modifiedSubIntegrityImpact.hashCode()));
        result = ((result* 31)+((this.modifiedSubAvailabilityImpact == null)? 0 :this.modifiedSubAvailabilityImpact.hashCode()));
        result = ((result* 31)+((this.vulnerabilityResponseEffort == null)? 0 :this.vulnerabilityResponseEffort.hashCode()));
        result = ((result* 31)+((this.modifiedAttackVector == null)? 0 :this.modifiedAttackVector.hashCode()));
        result = ((result* 31)+((this.modifiedVulnConfidentialityImpact == null)? 0 :this.modifiedVulnConfidentialityImpact.hashCode()));
        result = ((result* 31)+((this.subIntegrityImpact == null)? 0 :this.subIntegrityImpact.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.confidentialityRequirement == null)? 0 :this.confidentialityRequirement.hashCode()));
        result = ((result* 31)+((this.modifiedUserInteraction == null)? 0 :this.modifiedUserInteraction.hashCode()));
        result = ((result* 31)+((this.modifiedAttackComplexity == null)? 0 :this.modifiedAttackComplexity.hashCode()));
        result = ((result* 31)+((this.vulnConfidentialityImpact == null)? 0 :this.vulnConfidentialityImpact.hashCode()));
        result = ((result* 31)+((this.vulnAvailabilityImpact == null)? 0 :this.vulnAvailabilityImpact.hashCode()));
        result = ((result* 31)+((this.attackRequirements == null)? 0 :this.attackRequirements.hashCode()));
        result = ((result* 31)+((this.vulnIntegrityImpact == null)? 0 :this.vulnIntegrityImpact.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.modifiedAttackRequirements == null)? 0 :this.modifiedAttackRequirements.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CvssV40__1) == false) {
            return false;
        }
        CvssV40__1 rhs = ((CvssV40__1) other);
        return ((((((((((((((((((((((((((((((((((((this.modifiedSubConfidentialityImpact == rhs.modifiedSubConfidentialityImpact)||((this.modifiedSubConfidentialityImpact!= null)&&this.modifiedSubConfidentialityImpact.equals(rhs.modifiedSubConfidentialityImpact)))&&((this.modifiedPrivilegesRequired == rhs.modifiedPrivilegesRequired)||((this.modifiedPrivilegesRequired!= null)&&this.modifiedPrivilegesRequired.equals(rhs.modifiedPrivilegesRequired))))&&((this.modifiedVulnIntegrityImpact == rhs.modifiedVulnIntegrityImpact)||((this.modifiedVulnIntegrityImpact!= null)&&this.modifiedVulnIntegrityImpact.equals(rhs.modifiedVulnIntegrityImpact))))&&((this.subConfidentialityImpact == rhs.subConfidentialityImpact)||((this.subConfidentialityImpact!= null)&&this.subConfidentialityImpact.equals(rhs.subConfidentialityImpact))))&&((this.privilegesRequired == rhs.privilegesRequired)||((this.privilegesRequired!= null)&&this.privilegesRequired.equals(rhs.privilegesRequired))))&&((this.recovery == rhs.recovery)||((this.recovery!= null)&&this.recovery.equals(rhs.recovery))))&&((this.userInteraction == rhs.userInteraction)||((this.userInteraction!= null)&&this.userInteraction.equals(rhs.userInteraction))))&&((this.integrityRequirement == rhs.integrityRequirement)||((this.integrityRequirement!= null)&&this.integrityRequirement.equals(rhs.integrityRequirement))))&&((this.availabilityRequirement == rhs.availabilityRequirement)||((this.availabilityRequirement!= null)&&this.availabilityRequirement.equals(rhs.availabilityRequirement))))&&((this.automatable == rhs.automatable)||((this.automatable!= null)&&this.automatable.equals(rhs.automatable))))&&((this.subAvailabilityImpact == rhs.subAvailabilityImpact)||((this.subAvailabilityImpact!= null)&&this.subAvailabilityImpact.equals(rhs.subAvailabilityImpact))))&&((this.providerUrgency == rhs.providerUrgency)||((this.providerUrgency!= null)&&this.providerUrgency.equals(rhs.providerUrgency))))&&((this.safety == rhs.safety)||((this.safety!= null)&&this.safety.equals(rhs.safety))))&&((this.attackComplexity == rhs.attackComplexity)||((this.attackComplexity!= null)&&this.attackComplexity.equals(rhs.attackComplexity))))&&((this.attackVector == rhs.attackVector)||((this.attackVector!= null)&&this.attackVector.equals(rhs.attackVector))))&&((this.modifiedVulnAvailabilityImpact == rhs.modifiedVulnAvailabilityImpact)||((this.modifiedVulnAvailabilityImpact!= null)&&this.modifiedVulnAvailabilityImpact.equals(rhs.modifiedVulnAvailabilityImpact))))&&((this.exploitMaturity == rhs.exploitMaturity)||((this.exploitMaturity!= null)&&this.exploitMaturity.equals(rhs.exploitMaturity))))&&((this.vectorString == rhs.vectorString)||((this.vectorString!= null)&&this.vectorString.equals(rhs.vectorString))))&&((this.valueDensity == rhs.valueDensity)||((this.valueDensity!= null)&&this.valueDensity.equals(rhs.valueDensity))))&&((this.modifiedSubIntegrityImpact == rhs.modifiedSubIntegrityImpact)||((this.modifiedSubIntegrityImpact!= null)&&this.modifiedSubIntegrityImpact.equals(rhs.modifiedSubIntegrityImpact))))&&((this.modifiedSubAvailabilityImpact == rhs.modifiedSubAvailabilityImpact)||((this.modifiedSubAvailabilityImpact!= null)&&this.modifiedSubAvailabilityImpact.equals(rhs.modifiedSubAvailabilityImpact))))&&((this.vulnerabilityResponseEffort == rhs.vulnerabilityResponseEffort)||((this.vulnerabilityResponseEffort!= null)&&this.vulnerabilityResponseEffort.equals(rhs.vulnerabilityResponseEffort))))&&((this.modifiedAttackVector == rhs.modifiedAttackVector)||((this.modifiedAttackVector!= null)&&this.modifiedAttackVector.equals(rhs.modifiedAttackVector))))&&((this.modifiedVulnConfidentialityImpact == rhs.modifiedVulnConfidentialityImpact)||((this.modifiedVulnConfidentialityImpact!= null)&&this.modifiedVulnConfidentialityImpact.equals(rhs.modifiedVulnConfidentialityImpact))))&&((this.subIntegrityImpact == rhs.subIntegrityImpact)||((this.subIntegrityImpact!= null)&&this.subIntegrityImpact.equals(rhs.subIntegrityImpact))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.confidentialityRequirement == rhs.confidentialityRequirement)||((this.confidentialityRequirement!= null)&&this.confidentialityRequirement.equals(rhs.confidentialityRequirement))))&&((this.modifiedUserInteraction == rhs.modifiedUserInteraction)||((this.modifiedUserInteraction!= null)&&this.modifiedUserInteraction.equals(rhs.modifiedUserInteraction))))&&((this.modifiedAttackComplexity == rhs.modifiedAttackComplexity)||((this.modifiedAttackComplexity!= null)&&this.modifiedAttackComplexity.equals(rhs.modifiedAttackComplexity))))&&((this.vulnConfidentialityImpact == rhs.vulnConfidentialityImpact)||((this.vulnConfidentialityImpact!= null)&&this.vulnConfidentialityImpact.equals(rhs.vulnConfidentialityImpact))))&&((this.vulnAvailabilityImpact == rhs.vulnAvailabilityImpact)||((this.vulnAvailabilityImpact!= null)&&this.vulnAvailabilityImpact.equals(rhs.vulnAvailabilityImpact))))&&((this.attackRequirements == rhs.attackRequirements)||((this.attackRequirements!= null)&&this.attackRequirements.equals(rhs.attackRequirements))))&&((this.vulnIntegrityImpact == rhs.vulnIntegrityImpact)||((this.vulnIntegrityImpact!= null)&&this.vulnIntegrityImpact.equals(rhs.vulnIntegrityImpact))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.modifiedAttackRequirements == rhs.modifiedAttackRequirements)||((this.modifiedAttackRequirements!= null)&&this.modifiedAttackRequirements.equals(rhs.modifiedAttackRequirements))));
    }

    public enum AttackComplexityType {

        HIGH("HIGH"),
        LOW("LOW");
        private final String value;
        private final static Map<String, CvssV40__1 .AttackComplexityType> CONSTANTS = new HashMap<String, CvssV40__1 .AttackComplexityType>();

        static {
            for (CvssV40__1 .AttackComplexityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AttackComplexityType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .AttackComplexityType fromValue(String value) {
            CvssV40__1 .AttackComplexityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum AttackRequirementsType {

        NONE("NONE"),
        PRESENT("PRESENT");
        private final String value;
        private final static Map<String, CvssV40__1 .AttackRequirementsType> CONSTANTS = new HashMap<String, CvssV40__1 .AttackRequirementsType>();

        static {
            for (CvssV40__1 .AttackRequirementsType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AttackRequirementsType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .AttackRequirementsType fromValue(String value) {
            CvssV40__1 .AttackRequirementsType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum AttackVectorType {

        NETWORK("NETWORK"),
        ADJACENT("ADJACENT"),
        LOCAL("LOCAL"),
        PHYSICAL("PHYSICAL");
        private final String value;
        private final static Map<String, CvssV40__1 .AttackVectorType> CONSTANTS = new HashMap<String, CvssV40__1 .AttackVectorType>();

        static {
            for (CvssV40__1 .AttackVectorType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AttackVectorType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .AttackVectorType fromValue(String value) {
            CvssV40__1 .AttackVectorType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum AutomatableType {

        NO("NO"),
        YES("YES"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .AutomatableType> CONSTANTS = new HashMap<String, CvssV40__1 .AutomatableType>();

        static {
            for (CvssV40__1 .AutomatableType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        AutomatableType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .AutomatableType fromValue(String value) {
            CvssV40__1 .AutomatableType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum CiaRequirementType {

        LOW("LOW"),
        MEDIUM("MEDIUM"),
        HIGH("HIGH"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .CiaRequirementType> CONSTANTS = new HashMap<String, CvssV40__1 .CiaRequirementType>();

        static {
            for (CvssV40__1 .CiaRequirementType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        CiaRequirementType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .CiaRequirementType fromValue(String value) {
            CvssV40__1 .CiaRequirementType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ExploitMaturityType {

        UNREPORTED("UNREPORTED"),
        PROOF_OF_CONCEPT("PROOF_OF_CONCEPT"),
        ATTACKED("ATTACKED"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ExploitMaturityType> CONSTANTS = new HashMap<String, CvssV40__1 .ExploitMaturityType>();

        static {
            for (CvssV40__1 .ExploitMaturityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ExploitMaturityType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ExploitMaturityType fromValue(String value) {
            CvssV40__1 .ExploitMaturityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedAttackComplexityType {

        HIGH("HIGH"),
        LOW("LOW"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedAttackComplexityType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedAttackComplexityType>();

        static {
            for (CvssV40__1 .ModifiedAttackComplexityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedAttackComplexityType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedAttackComplexityType fromValue(String value) {
            CvssV40__1 .ModifiedAttackComplexityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedAttackRequirementsType {

        NONE("NONE"),
        PRESENT("PRESENT"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedAttackRequirementsType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedAttackRequirementsType>();

        static {
            for (CvssV40__1 .ModifiedAttackRequirementsType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedAttackRequirementsType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedAttackRequirementsType fromValue(String value) {
            CvssV40__1 .ModifiedAttackRequirementsType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedAttackVectorType {

        NETWORK("NETWORK"),
        ADJACENT("ADJACENT"),
        LOCAL("LOCAL"),
        PHYSICAL("PHYSICAL"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedAttackVectorType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedAttackVectorType>();

        static {
            for (CvssV40__1 .ModifiedAttackVectorType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedAttackVectorType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedAttackVectorType fromValue(String value) {
            CvssV40__1 .ModifiedAttackVectorType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedPrivilegesRequiredType {

        HIGH("HIGH"),
        LOW("LOW"),
        NONE("NONE"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedPrivilegesRequiredType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedPrivilegesRequiredType>();

        static {
            for (CvssV40__1 .ModifiedPrivilegesRequiredType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedPrivilegesRequiredType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedPrivilegesRequiredType fromValue(String value) {
            CvssV40__1 .ModifiedPrivilegesRequiredType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedSubCType {

        NEGLIGIBLE("NEGLIGIBLE"),
        LOW("LOW"),
        HIGH("HIGH"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedSubCType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedSubCType>();

        static {
            for (CvssV40__1 .ModifiedSubCType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedSubCType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedSubCType fromValue(String value) {
            CvssV40__1 .ModifiedSubCType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedSubIaType {

        NEGLIGIBLE("NEGLIGIBLE"),
        LOW("LOW"),
        HIGH("HIGH"),
        SAFETY("SAFETY"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedSubIaType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedSubIaType>();

        static {
            for (CvssV40__1 .ModifiedSubIaType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedSubIaType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedSubIaType fromValue(String value) {
            CvssV40__1 .ModifiedSubIaType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedUserInteractionType {

        NONE("NONE"),
        PASSIVE("PASSIVE"),
        ACTIVE("ACTIVE"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedUserInteractionType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedUserInteractionType>();

        static {
            for (CvssV40__1 .ModifiedUserInteractionType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedUserInteractionType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedUserInteractionType fromValue(String value) {
            CvssV40__1 .ModifiedUserInteractionType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ModifiedVulnCiaType {

        NONE("NONE"),
        LOW("LOW"),
        HIGH("HIGH"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ModifiedVulnCiaType> CONSTANTS = new HashMap<String, CvssV40__1 .ModifiedVulnCiaType>();

        static {
            for (CvssV40__1 .ModifiedVulnCiaType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ModifiedVulnCiaType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ModifiedVulnCiaType fromValue(String value) {
            CvssV40__1 .ModifiedVulnCiaType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum PrivilegesRequiredType {

        HIGH("HIGH"),
        LOW("LOW"),
        NONE("NONE");
        private final String value;
        private final static Map<String, CvssV40__1 .PrivilegesRequiredType> CONSTANTS = new HashMap<String, CvssV40__1 .PrivilegesRequiredType>();

        static {
            for (CvssV40__1 .PrivilegesRequiredType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        PrivilegesRequiredType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .PrivilegesRequiredType fromValue(String value) {
            CvssV40__1 .PrivilegesRequiredType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ProviderUrgencyType {

        CLEAR("CLEAR"),
        GREEN("GREEN"),
        AMBER("AMBER"),
        RED("RED"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ProviderUrgencyType> CONSTANTS = new HashMap<String, CvssV40__1 .ProviderUrgencyType>();

        static {
            for (CvssV40__1 .ProviderUrgencyType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ProviderUrgencyType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ProviderUrgencyType fromValue(String value) {
            CvssV40__1 .ProviderUrgencyType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum RecoveryType {

        AUTOMATIC("AUTOMATIC"),
        USER("USER"),
        IRRECOVERABLE("IRRECOVERABLE"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .RecoveryType> CONSTANTS = new HashMap<String, CvssV40__1 .RecoveryType>();

        static {
            for (CvssV40__1 .RecoveryType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        RecoveryType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .RecoveryType fromValue(String value) {
            CvssV40__1 .RecoveryType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum SafetyType {

        NEGLIGIBLE("NEGLIGIBLE"),
        PRESENT("PRESENT"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .SafetyType> CONSTANTS = new HashMap<String, CvssV40__1 .SafetyType>();

        static {
            for (CvssV40__1 .SafetyType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        SafetyType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .SafetyType fromValue(String value) {
            CvssV40__1 .SafetyType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum SubCiaType {

        NONE("NONE"),
        LOW("LOW"),
        HIGH("HIGH");
        private final String value;
        private final static Map<String, CvssV40__1 .SubCiaType> CONSTANTS = new HashMap<String, CvssV40__1 .SubCiaType>();

        static {
            for (CvssV40__1 .SubCiaType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        SubCiaType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .SubCiaType fromValue(String value) {
            CvssV40__1 .SubCiaType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum UserInteractionType {

        NONE("NONE"),
        PASSIVE("PASSIVE"),
        ACTIVE("ACTIVE");
        private final String value;
        private final static Map<String, CvssV40__1 .UserInteractionType> CONSTANTS = new HashMap<String, CvssV40__1 .UserInteractionType>();

        static {
            for (CvssV40__1 .UserInteractionType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        UserInteractionType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .UserInteractionType fromValue(String value) {
            CvssV40__1 .UserInteractionType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum ValueDensityType {

        DIFFUSE("DIFFUSE"),
        CONCENTRATED("CONCENTRATED"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .ValueDensityType> CONSTANTS = new HashMap<String, CvssV40__1 .ValueDensityType>();

        static {
            for (CvssV40__1 .ValueDensityType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        ValueDensityType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .ValueDensityType fromValue(String value) {
            CvssV40__1 .ValueDensityType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * CVSS Version
     * 
     */
    public enum Version {

        _4_0("4.0");
        private final String value;
        private final static Map<String, CvssV40__1 .Version> CONSTANTS = new HashMap<String, CvssV40__1 .Version>();

        static {
            for (CvssV40__1 .Version c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Version(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .Version fromValue(String value) {
            CvssV40__1 .Version constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum VulnCiaType {

        NONE("NONE"),
        LOW("LOW"),
        HIGH("HIGH");
        private final String value;
        private final static Map<String, CvssV40__1 .VulnCiaType> CONSTANTS = new HashMap<String, CvssV40__1 .VulnCiaType>();

        static {
            for (CvssV40__1 .VulnCiaType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        VulnCiaType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .VulnCiaType fromValue(String value) {
            CvssV40__1 .VulnCiaType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    public enum VulnerabilityResponseEffortType {

        LOW("LOW"),
        MODERATE("MODERATE"),
        HIGH("HIGH"),
        NOT_DEFINED("NOT_DEFINED");
        private final String value;
        private final static Map<String, CvssV40__1 .VulnerabilityResponseEffortType> CONSTANTS = new HashMap<String, CvssV40__1 .VulnerabilityResponseEffortType>();

        static {
            for (CvssV40__1 .VulnerabilityResponseEffortType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        VulnerabilityResponseEffortType(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static CvssV40__1 .VulnerabilityResponseEffortType fromValue(String value) {
            CvssV40__1 .VulnerabilityResponseEffortType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
