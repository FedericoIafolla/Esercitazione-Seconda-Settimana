package com.giochi;

public class GiocoDaTavolo extends Gioco {
    private int numeroGiocatori;
    private int durataMedia;

    public GiocoDaTavolo(String idGioco, String titolo, int annoPubblicazione, double prezzo, int numeroGiocatori, int durataMedia) {
        super(idGioco, titolo, annoPubblicazione, prezzo);
        this.numeroGiocatori = numeroGiocatori;
        this.durataMedia = durataMedia;
    }

    public int getNumeroGiocatori() {
        return numeroGiocatori;
    }

    public int getDurataMedia() {
        return durataMedia;
    }
}
