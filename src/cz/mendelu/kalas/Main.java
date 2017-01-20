package cz.mendelu.kalas;

public class Main {

    public static void main(String[] args) {



        PostOffice posta = new PostOffice();
        posta.addService(800,new Service(ServiceType.BALIK,new int[]{25,24,40,3}));
        posta.addService(2000,new Service(ServiceType.DOPIS,new int[]{1254,500,20,330}));
        posta.normalize();
        System.out.println(posta.toString());


        posta.pickService(0.1);

        posta.pickService(0.5);

        posta.pickService(0.9);





    }
}
