package cz.mendelu.kalas;

import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.models.PostOffice;
import cz.mendelu.kalas.models.Service;
import cz.mendelu.kalas.models.WorkDay;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {



        /*
        Faze 1
            - vytvoreni posty (s poctem prepazek)
            - vytvoreni sluzeb
                -   kazda sluzba v konstruktoru absolutni cislo provedenych za testovaci den
                -   nasledne pak 4 cisla vyjadrujici kolik z tech provedenych sluzeb se stalo v dane rychlostni kategorii (do 2 minut, do 5 minut..)
         */
        PostOffice posta = new PostOffice(4);
        posta.addService(800,new Service(ServiceType.BALIK,new int[]{25,24,40,3}));
        posta.addService(2000,new Service(ServiceType.DOPIS,new int[]{1254,500,20,330}));

        WorkDay w = new WorkDay(new int[]{30,15,15,10,8,20,16,30,40});

        //System.out.println(w.toString());



        /*
        Faze 2
            - normalizace
            - z tech absolutnich cisel se vypocita (v jiz hezky vytvorene strukture) pravdepodobnosti
         */
        posta.normalize();






        /*
        Faze 3 (in progress)
            - simulace pracovniho dne (540 minut), kdy chodi zakaznici (podle pravdepdobnosti), vybiraj si sluzby a tim obsazuji prepazky
            - nyni pro ucely odhadovani vstupnich cisel (musi nam z te prace vypadnou nejake rozumne vysledky) to mam jako
                - 1 sekunda = 1 minuta
                - az to bude davat nejak smysl, tak to samozrejme nepojede takhle po sekundach ale probehne to davkove
         */



        while(!w.isEnd()){
            sleep(1000);

            // Vypocet prichodu zakaznika podle pravdepodobnosti prichodu v danem case
            Boolean customer = Utils.getRandom(0.001,1.0,3)< +w.getProbabilityForTime(w.howLong());


            if(customer) {
                Service service = posta.pickService();
                System.out.println("Zakaznik -  si vybral službu ["+service.getName()+"], doba vyřízení ["+service.pickDelay().name()+"]");
            }else {
                System.out.println("Zakaznik neprisel");
            }



        }







        /*
        Faze 4 (TODO)
            - tady si predstavuji ze to vyhodi nejake vysledky
                - napr. prepazka 4 byla po vetsinu dne prázdná a proto je k hovnu, svoji poštu můžete zredukovat na 3 přepážky
         */



    }
}
