package com.epam.esm.entity;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@Entity
@Table(name = "tags")
@Audited
public class Tag extends RepresentationModel<Tag> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tag tag = (Tag) o;
        return id == tag.id && Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name);
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Tag{");
        result.append("id=").append(id);
        result.append(", name='").append(name).append('\'');
        result.append('}');
        return result.toString();
    }
}
