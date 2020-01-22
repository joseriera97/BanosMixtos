/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baño;

/**
 *
 * @author jarie
 */

import java.util.concurrent.Semaphore;

public class BañosMixtos {
    public static int cola = 7;
    public static Semaphore sem = new Semaphore(1);
    // el limite de los banos y el sexo que los usa
    public static int capacidad = 4;
    // La capacidad de los banos
    public static int enuso = 0;
    
    public static void main(String[] args) {
        //Info de el baño
        System.out.println("----INFO Baño----");
        System.out.println("Capacidad Baño:"+ capacidad);
        System.out.println("Cantidad de cola:"+ cola +"\n");

        ///* Personas limitadas
        Persona[] personas = new Persona[cola];
        for (int i = 0; i < cola; i++) {
            int r = (int) (Math.random()*2);
            if (r > 0) personas[i] = new Persona(-1);
            else personas[i] = new Persona(1);
        }
        for (int i = 0; i < cola; i++) personas[i].start();
        //*/
        /* Personas ilimitadas
        boolean llegan = true;
        while (llegan) {
            int r = (int) (Math.random()*2);
            if (r > 0) new Persona(-1);
            else new Persona(1);
        }
        */
    }
}