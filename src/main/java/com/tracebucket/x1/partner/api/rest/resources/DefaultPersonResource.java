package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.dictionary.api.domain.PersonType;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultPersonResource extends BaseResource {
    @NotNull
    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z]*$")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z]*$")
    private String lastName;

    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z]*$")
    private String middleName;

    private Date birthDay;

    private Gender gender;

    @Size(min = 1, max = 250)
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)")
    private String image;

    private Map<String, EmailType> emails = new HashMap<String, EmailType>(0);

    private Map<String, PhoneType> phones = new HashMap<String, PhoneType>(0);

    private Set<PersonType> personTypes = new HashSet<PersonType>();
    private boolean defaultContactPerson;
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, EmailType> getEmails() {
        return emails;
    }

    public void setEmails(Map<String, EmailType> emails) {
        this.emails = emails;
    }

    public Map<String, PhoneType> getPhones() {
        return phones;
    }

    public void setPhones(Map<String, PhoneType> phones) {
        this.phones = phones;
    }

    public Set<PersonType> getPersonTypes() {
        return personTypes;
    }

    public void setPersonTypes(Set<PersonType> personTypes) {
        this.personTypes = personTypes;
    }

    public boolean isDefaultContactPerson() {
        return defaultContactPerson;
    }

    public void setDefaultContactPerson(boolean defaultContactPerson) {
        this.defaultContactPerson = defaultContactPerson;
    }
}