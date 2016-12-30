package rebu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Training.
 */
@Entity
@Table(name = "training")
public class Training implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "training")
    @JsonIgnore
    private Set<Exercise> names = new HashSet<>();

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Training name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Exercise> getNames() {
        return names;
    }

    public Training names(Set<Exercise> exercises) {
        this.names = exercises;
        return this;
    }

    public Training addName(Exercise exercise) {
        names.add(exercise);
        exercise.setTraining(this);
        return this;
    }

    public Training removeName(Exercise exercise) {
        names.remove(exercise);
        exercise.setTraining(null);
        return this;
    }

    public void setNames(Set<Exercise> exercises) {
        this.names = exercises;
    }

    public User getUser() {
        return user;
    }

    public Training user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Training training = (Training) o;
        if(training.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, training.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Training{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
