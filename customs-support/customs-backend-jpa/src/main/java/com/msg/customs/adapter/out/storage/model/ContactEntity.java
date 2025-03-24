package com.msg.customs.adapter.out.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of a contact.
 */
@Getter
@Setter
@ToString
@Embeddable
public class ContactEntity {

    @Column(name = "contact_name", nullable = false)
    private String name;

    @Column(name = "contact_email", nullable = false)
    private String email;

    @Column(name = "contact_phone", nullable = false)
    private String phone;
    
    @Column(name = "contact_job_title", nullable = false)
    private String jobTitle;

}
