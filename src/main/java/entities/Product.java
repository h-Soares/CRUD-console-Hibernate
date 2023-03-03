package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal value;
    @ManyToMany(mappedBy = "products", cascade = CascadeType.PERSIST)
    private Set<Person> persons = new HashSet<>();

    public Product() {
    }

    public Product(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public void insertPerson(Person person) {
        if(person != null) {
            persons.add(person);
            person.insertProduct(this);
        }
        else
            throw new IllegalArgumentException("Product can't be null");
    }

    public void removePerson(Person person) {
        if(person != null) {
            persons.remove(person);
            person.getProducts().remove(this);
        }
        else
            throw new IllegalArgumentException("Product can't be null");
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Set<Person> getPersons() {
        return persons;
    }
}