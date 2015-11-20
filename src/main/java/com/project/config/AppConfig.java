package com.project.config;

import akka.actor.*;
import com.project.AppVariables;
import com.project.actor.Master;
import com.project.message.Job;
import com.project.message.Proceed;
import com.project.service.ProductPersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.LinkedList;
import java.util.List;

@Import(HibernateConfig.class)
@Configuration
@ComponentScan({"com.project.controller"})
@EnableTransactionManagement
public class AppConfig {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ProductPersistenceService productPersistenceService;

    private List<Job> jobs;

    @PostConstruct
    private void init() throws Exception {
        String path = servletContext.getRealPath(AppVariables.Site1_Path);
        Job job1 = new Job(path);
        jobs = new LinkedList<Job>();
        jobs.add(job1);
        runAgent();
    }

    private void runAgent(){

        ActorSystem actorSystem = ActorSystem.create("AkkaActorSystemAgent");
        final ProductPersistenceService persistenceService = this.productPersistenceService;
        UntypedActorFactory untypedActorFactory = new UntypedActorFactory(){
            public UntypedActor create(){
                return new Master(4,jobs,persistenceService);
            }
        };

        ActorRef master = actorSystem.actorOf(new Props(untypedActorFactory),"MasterActor");
        master.tell(new Proceed());

    }
}
