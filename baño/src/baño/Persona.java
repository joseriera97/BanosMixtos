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
public class Persona extends Thread{
    int sexo;
    public Persona(int s) {
        sexo = s;
    }
    //devuelve true si el homre puede entrar
    public boolean casoHombre() {
        return ((BañosMixtos.enuso > 0) && (BañosMixtos.enuso < BañosMixtos.capacidad));
    }
    //devuelve false si la mujer puede entrar
    public boolean casoMujer() {
        return ((BañosMixtos.enuso < 0) && (BañosMixtos.enuso > BañosMixtos.capacidad));
    }
    public boolean puedeEntrar() {
        // primero comprueba si su sexo usa el baño
        // segundo si esta por llegar a la capacidad
        return ((sexo > BañosMixtos.enuso * -1) && (Math.abs(BañosMixtos.capacidad)>Math.abs(BañosMixtos.enuso)));
    }
    @Override
    public void run() {
        super.run();
        boolean espera = true;
        //se mantenga en la cola hasta que entre
        while (espera) {
            try {
                BañosMixtos.sem.acquire();
                // saber si hay alguien utilizndo el baño para ocuparlo y reservarlo a su sexo
                if (BañosMixtos.enuso == 0) {
                    espera = false;
                    BañosMixtos.capacidad = (sexo>0)?Math.abs(BañosMixtos.capacidad):Math.abs(BañosMixtos.capacidad)*-1;
                }
                else {
                    // en caso de que ocupen la capacidad, cede el baño al otro sexo
                    if (BañosMixtos.enuso == BañosMixtos.capacidad)
                        BañosMixtos.capacidad = (sexo<0)?Math.abs(BañosMixtos.capacidad):Math.abs(BañosMixtos.capacidad)*-1;
                    // saber si hay baños disponibles para su sexo
                    if ((sexo>0 && casoHombre()) || (sexo<0 && casoMujer())) 
                        espera = false;
                    else BañosMixtos.sem.release();
                }
            }
            catch(Exception e) {
                System.out.println("ERROR: INTERRUMPIDO");
                BañosMixtos.sem.release();
            }
        }
        // esta persona pudo entrar al baño
        BañosMixtos.enuso+=sexo;
        System.out.println(((sexo>0)?"Un hombre":"Una mujer")+" entra al baño, en el baño hay "+Math.abs(BañosMixtos.enuso));
        // esta persona cumplio su mision
        BañosMixtos.sem.release();
        try {
            sleep((int) (Math.random()*10+1)*100);
            // esta persona pudo hacer sus necesidades
            BañosMixtos.sem.acquire();
            BañosMixtos.enuso-=sexo;
            System.out.println(((sexo>0)?"Un hombre":"Una mujer")+" se satisfacio en baño, en el baño hay "+Math.abs(BañosMixtos.enuso));
        } catch (Exception e) {
            System.out.println("ERROR: INTERRUMPIDO");
            BañosMixtos.sem.release();
        }
        // esta persona cumplio su mision
        BañosMixtos.sem.release();
    }
}