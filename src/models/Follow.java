package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
            name = "checkFollowsEmployees",
            query = "SELECT COUNT(f) FROM Follow AS f WHERE f.employee = :employee AND f.follow_employee = :follow_employee"
    ),
    @NamedQuery(
            name = "getMyFollowsEmployees",
            query = "SELECT f FROM Follow AS f WHERE f.employee = :employee ORDER BY f.follow_employee.id DESC"
    ),
    @NamedQuery(
            name = "getMyFollowsEmployeesCount",
            query = "SELECT COUNT(f) FROM Follow AS f WHERE f.employee = :employee"
    ),
    @NamedQuery(
            name = "getMyFollowersEmployees",
            query = "SELECT f FROM Follow AS f WHERE f.follow_employee = :employee ORDER BY f.employee.id DESC"
    ),
    @NamedQuery(
            name = "checkMyFollowersEmployees",
            query = "SELECT COUNT(f) FROM Follow AS f WHERE f.employee = :employee AND f.follow_employee = :follow_employee"
    ),
    @NamedQuery(
            name = "getMyFollowersEmployeesCount",
            query = "SELECT COUNT(f) FROM Follow AS f WHERE f.follow_employee = :employee"
    )
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "follow_employee_id", nullable = false)
    private Employee follow_employee;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employee getFollow_employee() {
        return follow_employee;
    }

    public void setFollow_employee(Employee follow_employee) {
        this.follow_employee = follow_employee;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
