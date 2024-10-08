package com.giochi;

public class Videogioco extends Gioco {
    private String piattaforma;
    private int durataGioco;
    private Genere genere;

    public Videogioco(String idGioco, String titolo, int annoPubblicazione, double prezzo, String piattaforma, int durataGioco, Genere genere) {
        super(idGioco, titolo, annoPubblicazione, prezzo);
        this.piattaforma = piattaforma;
        this.durataGioco = durataGioco;
        this.genere = genere;
    }

    public String getPiattaforma() {
        return piattaforma;
    }

    public int getDurataGioco() {
        return durataGioco;
    }

    public Genere getGenere() {
        return genere;
    }

    @Override
    public String toString() {
        return String.format("Videogioco [ID: %s, Titolo: %s, Anno: %d, Prezzo: %.2f, Piattaforma: %s, Durata: %d ore, Genere: %s]",
                getIdGioco(), getTitolo(), getAnnoPubblicazione(), getPrezzo(), piattaforma, durataGioco, genere);
    }
}
