package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by sadath on 26-May-2015.
 */
public class DefaultCustomerResource extends DefaultPartnerRoleResource {
    private static final String simpleName = "Customer";

    @Size(min = 1, max = 10)
    private String initial;

    private Salutation salutation;

    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z]*$")
    private String firstName;

    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z]*$")
    private String middleName;

    @Size(min = 1, max = 250)
    @Pattern(regexp = "^[A-Za-z]*$")
    private String lastName;

    private Date birthDay;
    private Gender gender;

    @Size(min = 1, max = 250)
    @Email
    private String email;

    public static String getSimpleName() {
        return simpleName;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}