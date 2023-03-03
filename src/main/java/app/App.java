package app;

import dao.GenericDAO;
import entities.Person;
import entities.Product;

public class App {
    public static void main( String[] args ) {
        GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
        GenericDAO<Product> productDAO = new GenericDAO<>(Product.class);

        //ADICIONA EM UM ADICIONA NO OUTRO.

        //O problema era "fazer direto" estava dando repetição (ou não, talvez era apenas quando imprimia).
        //RESOLVIDO mudando de List para Set.
//        personDAO.beginTransaction();
//        Person person = personDAO.findById(7);
//        Product product = productDAO.findById(6);
//        person.insertProduct(product);
//        personDAO.update(person);
//        personDAO.commitTransaction();

//        person.getProducts().forEach(System.out::println);
//        product.getPersons().forEach(System.out::println);

////        //Depois, testar a exclusão:
//
//        personDAO.beginTransaction();
//        person.removeProduct(product);
//        personDAO.commitTransaction();

//        person.getProducts().forEach(System.out::println);
//        product.getPersons().forEach(System.out::println);

        Person person = personDAO.findById(1);
        Product product = productDAO.findById(3);
        person.getProducts().forEach(System.out::println);
        product.getPersons().forEach(System.out::println);

        personDAO.closeEntityManager();
        productDAO.closeEntityManager();
        GenericDAO.closeEntityManagerFactory();
    }
}