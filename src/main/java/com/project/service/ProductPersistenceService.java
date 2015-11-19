package com.project.service;

import com.project.model.Product;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Service
@Transactional
public class ProductPersistenceService {
    private HibernateTemplate hibernateTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public synchronized void  saveProducts(List<Product> products){

        hibernateTemplate.save(products.get(0));
        /*for (int i = 0; i <products.size() ; i++) {
            hibernateTemplate.save(products.get(i));
        }*/
    }

}
