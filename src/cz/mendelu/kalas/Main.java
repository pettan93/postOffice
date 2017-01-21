package cz.mendelu.kalas;

import cz.mendelu.kalas.enums.ServiceType;
import cz.mendelu.kalas.models.*;

import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        /*
        Faze 1
            - vytvoreni posty (s poctem prepazek)
            - vytvoreni sluzeb
                -   kazda sluzba v konstruktoru absolutni cislo provedenych za testovaci den
                -   nasledne pak 4 cisla vyjadrujici kolik z tech provedenych sluzeb se stalo v dane rychlostni kategorii (do 1-2 minut, do 2-5 minut..)
         */
        // Vytvoreni posty - konstruktor, pocet prepazek
        PostOffice posta = new PostOffice(2);
        // Vytvoreni sluzeb posty
        posta.addService(800, new Service(ServiceType.BALIK, new int[]{25, 24, 40, 3}));
        posta.addService(2000, new Service(ServiceType.DOPIS, new int[]{1254, 500, 20, 330}));
        // Vytvoreni pracovniho dne
        WorkDay w = new WorkDay(new int[]{30, 15, 15, 10, 8, 20, 16, 30, 40});


        /*
        Faze 2
            - normalizace
            - z tech absolutnich cisel se vypocita (v jiz hezky vytvorene strukture) pravdepodobnosti na zaklade dat z testovaciho dne
            - tedy pravdepodobnost prichodu volby zakaznika dane sluzby
            - pravdepodobnost jak dlouho provedeni sluzby bude trvat
         */
        posta.normalize();


        /*
        Faze 3
            - simulace pracovniho dne (540 minut), kdy chodi zakaznici (podle pravdepdobnosti), zarazuji se do fronty, chodi k prepazkam a vybiraj si sluzby
            - nyni pro ucely odhadovani vstupnich cisel (musi nam z te prace vypadnou nejake rozumne vysledky) to mam jako
                - 1 sekunda = 1 minuta
                - az to bude davat nejak smysl, tak to samozrejme nepojede takhle po sekundach ale probehne to davkove
        */
        // Mašina na listky pro prichozi zakaznika,
        int costumerTicket = 1;
        // cyklus simulujici pracovni den
        while (!w.isEnd()) {
            System.out.println(w.getClock());

            // Vypocet prichodu zakaznika podle pravdepodobnosti prichodu v danem case
            Costumer customer = (Utils.getRandom(0.001, 1.0, 3) < +w.getProbabilityForTime(w.howLong())) ? new Costumer(costumerTicket) : null;

            // Kontrola stavu vsech prepazek, pokud je zpracovávaný ukol na přepážce splněn, prepazka je uvolněna
            posta.geAllDesks().forEach(Desk::checkStatus);

            // Pokud prisel zakaznik
            if (customer != null) {
                System.out.println("Zakaznik prisel +");

                // Jde do fronty
                posta.costumerToQueue(customer);
                // a mašina na tickety se posouva
                costumerTicket++;

            } else { // pokud zakaznik neprisel
                System.out.println("Zakaznik neprisel");
            }


            // Je aktualne volna prepazka?
            Desk freeDesk = posta.getFirstFreeDesk();
            // Pokud je volna prepazka a nejaky zakaznik ve fronte
            if (freeDesk != null && !posta.getQueueCostumer().isEmpty()) {

                // vyberu zakaznika z fronty
                Costumer onMove = posta.getNextCostumer();

                // zakaznik si rozmysli co chce a priradi se ke prepazce
                Service service = posta.pickService();
                DispatchTime dt = service.pickDispatchTime();
                Integer dtt = dt.getTime();
                System.out.println("Zakaznik s cislem [" + onMove.getNumber() + "] - " +
                        " je obslouzen na prepazce [" + freeDesk.getDeskNumber() + "], " +
                        " vybral službu [" + service.getName() + "]," +
                        " doba vyřízení [" + dt.name() + "]" + "," +
                        " přesně minut [" + dtt + "]");
                // prepazka se stava obsazena na daný čas potřebný k provedení služby
                freeDesk.setBusy(dtt);
            }


            System.out.println("Status : pocet volnych prepazek [" + posta.geAllDesks().stream().filter(desk -> !desk.isOn()).collect(Collectors.toList()).size() + "]");
            System.out.println("Status : pocet zakazniku ve fronte [" + posta.getQueueCostumerCont() + "] + " + posta.getQueueCostumer());
            System.out.println("-------------");
            sleep(2000); //step
        }










        /*
        Faze 4 (TODO)
            - tady si predstavuji ze to vyhodi nejake vysledky
                - napr. prepazka 4 byla po vetsinu dne prázdná a proto je k hovnu, svoji poštu můžete zredukovat na 3 přepážky
         */


    }
}
