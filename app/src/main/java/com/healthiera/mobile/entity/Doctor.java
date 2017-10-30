package com.healthiera.mobile.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * @author Yengibar Manasyan
 * @date 01.09.2016
 */
@Table(name = "doctor")
public class Doctor extends Model {

    @Column(name = "specification", notNull = true)
    private String specification;

    @Column(name = "name", notNull = true)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    public Doctor() {
        super();
    }

    public Doctor(String name, String specification, String email, String phone) {
        super();
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.specification = specification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
