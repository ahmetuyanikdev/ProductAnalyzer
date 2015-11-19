package com.project.config;

import akka.actor.*;
import com.project.agent.Master;
import com.project.message.Job;
import com.project.message.Proceed;
import com.project.model.Product;
import com.project.service.ProductPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Import(HibernateConfig.class)
@Configuration
@ComponentScan({"com.project.model","com.project.controller"})
public class AppConfig {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Autowired
    ServletContext servletContext;

    List<Job> jobs;

    AppConfig(){

    }

    @PostConstruct
    void init() throws Exception{
        String path = servletContext.getRealPath("/WEB-INF/site1.xml");
        Job job1 = new Job(path);
        jobs = new LinkedList<Job>();
        jobs.add(job1);
    }

    @Bean
    ProductPersistenceService productPersistenceService(){
        ProductPersistenceService productPersistenceService = new ProductPersistenceService();
        hibernateTemplate.setCheckWriteOperations(false);
        productPersistenceService.setHibernateTemplate(hibernateTemplate);
        runAgent(productPersistenceService);
        return productPersistenceService;
    }

    private void runAgent(final ProductPersistenceService productPersistenceService){
        ActorSystem actorSystem = ActorSystem.create("ProductPersistenceAgent");

        UntypedActorFactory untypedActorFactory = new UntypedActorFactory(){

            public UntypedActor create(){
                return new Master(4,jobs,productPersistenceService);
            }
        };

        ActorRef master = actorSystem.actorOf(new Props(untypedActorFactory),"master");

        master.tell(new Proceed());

    }


}
