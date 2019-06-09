package com.santg.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.santg.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create a query ... sort by last name
		Query<Customer> query = currentSession.createQuery("from Customer order by lastName", Customer.class);
		
		// execute query and get result list
		List<Customer> customers = query.getResultList();
		
		// return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/update the customer
		currentSession.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(int id) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// get customer by id
		Customer customer = currentSession.get(Customer.class, id);
		
		// return customer
		return customer;
	}

	@Override
	public void deleteCustomer(int id) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// get customer from session
		Customer customer = currentSession.get(Customer.class, id);
		
		// delete customer
		currentSession.delete(customer);
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		// search by name if searchName is not empty
		if(searchName != null && searchName.trim().length() > 0) {
			
			// search for firstName or lastName ... case insensitive
			query = currentSession.createQuery("from Customer where lower(firstName) like :name"
					+ " or lower(lastName) like :name", Customer.class);
			
			query.setParameter("name", "%" + searchName.toLowerCase() + "%");
			
		} else {
			
			//searchName is empty ... get all customers
			query = currentSession.createQuery("from Customer", Customer.class);			
		}
		
		List<Customer> customers = query.getResultList();
		
		return customers;
	}
}
