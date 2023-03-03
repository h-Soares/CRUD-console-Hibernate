package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    //no caso de, se adicionar um novo produto, ele ser inserido (persistido) na tabela de produtos.
    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable(name = "person_product",
               joinColumns = @JoinColumn(name = "persons_id"),
               inverseJoinColumns = @JoinColumn(name = "products_id"))
    private Set<Product> products = new HashSet<>();
    
    public Person() {
    }
    
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void insertProduct(Product product) {
        if(product != null) {
            products.add(product);
            product.getPersons().add(this); //funciona sem (adiciona no outro) mas é recomendado usar!
        }
        else
            throw new IllegalArgumentException("Product can't be null");
    }

    public void removeProduct(Product product) {
        if(product != null) {
            products.remove(product);
            product.getPersons().remove(this); //funciona sem (remove no outro) mas é recomendado usar!
        }
        else
            throw new IllegalArgumentException("Product can't be null");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Person{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
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
        Person other = (Person) obj;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Product> getProducts() {
        return products;
    }
}