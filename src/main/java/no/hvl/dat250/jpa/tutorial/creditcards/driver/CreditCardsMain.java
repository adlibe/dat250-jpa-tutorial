package no.hvl.dat250.jpa.tutorial.creditcards.driver;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import no.hvl.dat250.jpa.tutorial.creditcards.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CreditCardsMain {

  static final String PERSISTENCE_UNIT_NAME = "jpa-tutorial";

  public static void main(String[] args) {
    try (EntityManagerFactory factory = Persistence.createEntityManagerFactory(
            PERSISTENCE_UNIT_NAME); EntityManager em = factory.createEntityManager()) {
      em.getTransaction().begin();
      createObjects(em);
      em.getTransaction().commit();
    }
  }

  private static void createObjects(EntityManager em) {
    Customer customer = new Customer();
    customer.setName("Max Mustermann");

    Address address = new Address();
    address.setStreet("Inndalsveien");
    address.setNumber(28);

    address.getOwners().add(customer);
    customer.getAddresses().add(address);

    CreditCard card1 = new CreditCard();
    card1.setNumber(12345);
    card1.setBalance(-5000);
    card1.setCreditLimit(-10000);

    CreditCard card2 = new CreditCard();
    card2.setNumber(123);
    card2.setBalance(1);
    card2.setCreditLimit(2000);

    List<CreditCard> creditCards = new ArrayList<>();
    creditCards.add(card1);
    creditCards.add(card2);
    creditCards.sort(Comparator.comparing(CreditCard::getNumber));

    customer.getCreditCards().addAll(creditCards);
    card1.setCustomer(customer);
    card2.setCustomer(customer);

    Pincode pincode = new Pincode();
    pincode.setCode("123");
    pincode.setCount(1);
    em.persist(pincode);

    card1.setPincode(pincode);
    card2.setPincode(pincode);

    Bank bank = new Bank();
    bank.setName("Pengebank");

    card1.setOwningBank(bank);
    card2.setOwningBank(bank);
    bank.getOwnedCards().add(card1);
    bank.getOwnedCards().add(card2);

    em.persist(bank);
    em.persist(customer);
  }
}
