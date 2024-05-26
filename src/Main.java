import service.PubSubService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        PubSubService pubSubService = new PubSubService();
        pubSubService.addTopic("t1");
        pubSubService.addTopic("t2");
        pubSubService.addSubscriber("s1");
        pubSubService.addSubscriber("s2");
        pubSubService.addSubscriber("s3");
        pubSubService.subscribeTopic("t1","s1");
        pubSubService.subscribeTopic("t1","s2");
        pubSubService.subscribeTopic("t1","s3");
        pubSubService.subscribeTopic("t2","s1");
        pubSubService.subscribeTopic("t2","s2");

        pubSubService.publishMessage("t2","Hello1!");
        pubSubService.publishMessage("t1","Hello2!");
        pubSubService.publishMessage("t2","Hello3!");
        pubSubService.publishMessage("t1","Hello4!");
        pubSubService.publishMessage("t2","Hello5!");
        pubSubService.resetOffset("s1","t1");
        //Thread.sleep(1000);
        System.out.println("Here");

    }
}