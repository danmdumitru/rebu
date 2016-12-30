package rebu.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Result.
 */
@Entity
@Table(name = "result")
public class Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "rep")
    private Integer rep;

    @Column(name = "sets")
    private Integer sets;

    @Column(name = "due_date")
    private ZonedDateTime dueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Result description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRep() {
        return rep;
    }

    public Result rep(Integer rep) {
        this.rep = rep;
        return this;
    }

    public void setRep(Integer rep) {
        this.rep = rep;
    }

    public Integer getSets() {
        return sets;
    }

    public Result sets(Integer sets) {
        this.sets = sets;
        return this;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public ZonedDateTime getDueDate() {
        return dueDate;
    }

    public Result dueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(ZonedDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result result = (Result) o;
        if(result.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, result.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Result{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", rep='" + rep + "'" +
            ", sets='" + sets + "'" +
            ", dueDate='" + dueDate + "'" +
            '}';
    }
}
