package cz.mendelu.kalas;

import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.models.PostOffice;
import cz.mendelu.kalas.models.Service;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {



        PostOffice posta = new PostOffice();
        posta.addService(800,new Service(ServiceType.BALIK,new int[]{25,24,40,3}));
        posta.addService(2000,new Service(ServiceType.DOPIS,new int[]{1254,500,20,330}));
        posta.normalize();
        //System.out.println(posta.toString());


        while(true){
            sleep(3000);
            System.out.println("Start ---->");

            Service service = posta.pickService();
            System.out.println("Zakaznik si vybral službu ["+service.getName()+"], doba vyřízení ["+service.pickDelay().name()+"]");

        }








    }
}
