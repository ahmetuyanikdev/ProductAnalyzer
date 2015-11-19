package com.project.agent;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;
import com.project.message.Job;
import com.project.message.Proceed;
import com.project.service.ProductPersistenceService;

import java.util.List;

public class Master extends UntypedActor {

    private List<Job> jobs;
    private final ActorRef workerRouter;
    ProductPersistenceService productPersistenceService;

    public Master(int nrOfWorkers,List<Job> jobs,ProductPersistenceService persistenceService){
        this.jobs = jobs;
        this.productPersistenceService = persistenceService;
        workerRouter = this.getContext().actorOf(new Props(Worker.class).withRouter(new RoundRobinRouter(nrOfWorkers)),
                "WorkerRouter");

    }

    @Override
    public void onReceive(Object message) throws Exception {
        if(message instanceof Proceed){
            for (int i = 0; i < jobs.size(); i++) {
                workerRouter.tell(jobs.get(i),getSelf());
            }
        }

        if(message instanceof Job){
            Job job = (Job)message;
            productPersistenceService.saveProducts(job.getProducts());
        }
        else {
            unhandled(message);
        }
    }
}
