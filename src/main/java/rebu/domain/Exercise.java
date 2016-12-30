package rebu.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "rep")
    private Integer rep;

    @Column(name = "sets")
    private Integer sets;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Training training;

    @ManyToMany
    @JoinTable(name = "exercise_name",
               joinColumns = @JoinColumn(name="exercises_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="names_id", referencedColumnName="ID"))
    private Set<Result> names = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Exercise name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRep() {
        return rep;
    }

    public Exercise rep(Integer rep) {
        this.rep = rep;
        return this;
    }

    public void setRep(Integer rep) {
        this.rep = rep;
    }

    public Integer getSets() {
        return sets;
    }

    public Exercise sets(Integer sets) {
        this.sets = sets;
        return this;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public String getDescription() {
        return description;
    }

    public Exercise description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Training getTraining() {
        return training;
    }

    public Exercise training(Training training) {
        this.training = training;
        return this;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Set<Result> getNames() {
        return names;
    }

    public Exercise names(Set<Result> results) {
        this.names = results;
        return this;
    }

    public Exercise addName(Result result) {
        names.add(result);
        return this;
    }

    public Exercise removeName(Result result) {
        names.remove(result);
        return this;
    }

    public void setNames(Set<Result> results) {
        this.names = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        if(exercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", rep='" + rep + "'" +
            ", sets='" + sets + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
