package com.giochi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Collezione {
    private List<Gioco> giochi;

    public Collezione() {
        this.giochi = new ArrayList<>();
        aggiungiGioco(new GiocoDaTavolo("T001", "Monopoly", 1935, 29.99, 2, 180));
        aggiungiGioco(new GiocoDaTavolo("T002", "Pictionary", 1985, 25.99, 4, 60));
        aggiungiGioco(new GiocoDaTavolo("T003", "Taboo", 1989, 24.99, 4, 60));
        aggiungiGioco(new GiocoDaTavolo("T004", "Exploding Kittens", 2015, 19.99, 2, 20));
        aggiungiGioco(new GiocoDaTavolo("T005", "Cluedo", 1949, 29.99, 2, 60));
        aggiungiGioco(new Videogioco("V001", "Sekiro: Shadows Die Twice", 2019, 59.99, "PS4", 30, Genere.AZIONE));
        aggiungiGioco(new Videogioco("V002", "Alan Wake 2", 2023, 59.99, "PS5", 15, Genere.HORROR));
        aggiungiGioco(new Videogioco("V003", "Wuthering Waves", 2024, 59.99, "PC", 20, Genere.AVVENTURA));
        aggiungiGioco(new Videogioco("V004", "Halo: The Master Chief Collection", 2014, 39.99, "Xbox One", 60, Genere.FPS));
        aggiungiGioco(new Videogioco("V005", "Gears of War", 2006, 29.99, "Xbox 360", 10, Genere.TPS));
    }

    public void aggiungiGioco(Gioco gioco) {
        if (giochi.stream().anyMatch(g -> g.getIdGioco().equals(gioco.getIdGioco()))) {
            throw new IllegalArgumentException("Gioco con ID " + gioco.getIdGioco() + " già esistente.");
        }
        giochi.add(gioco);
    }

    public Optional<Gioco> cercaPerID(String id) {
        return giochi.stream().filter(g -> g.getIdGioco().equals(id)).findFirst();
    }

    public List<Gioco> cercaPerPrezzo(double prezzo) {
        return giochi.stream().filter(g -> g.getPrezzo() < prezzo).collect(Collectors.toList());
    }

    public List<Gioco> cercaPerNumeroGiocatori(int numGiocatori) {
        return giochi.stream()
                .filter(g -> g instanceof GiocoDaTavolo)
                .filter(g -> ((GiocoDaTavolo) g).getNumeroGiocatori() == numGiocatori)
                .collect(Collectors.toList());
    }

    public void rimuoviGioco(String id) {
        giochi.removeIf(g -> g.getIdGioco().equals(id));
    }

    public void aggiornaGioco(String id, Gioco nuovoGioco) {
        for (int i = 0; i < giochi.size(); i++) {
            if (giochi.get(i).getIdGioco().equals(id)) {
                giochi.set(i, nuovoGioco);
                return;
            }
        }
        throw new IllegalArgumentException("Gioco con ID " + id + " non trovato.");
    }

    public void statistiche() {
        double sommaPrezziVideogiochi = 0;
        double sommaPrezziGiochiDaTavolo = 0;
        int countVideogiochi = 0;
        int countGiochiDaTavolo = 0;

        for (Gioco gioco : giochi) {
            if (gioco instanceof Videogioco) {
                sommaPrezziVideogiochi += gioco.getPrezzo();
                countVideogiochi++;
            } else if (gioco instanceof GiocoDaTavolo) {
                sommaPrezziGiochiDaTavolo += gioco.getPrezzo();
                countGiochiDaTavolo++;
            }
        }

        double mediaVideogiochi = countVideogiochi > 0 ? sommaPrezziVideogiochi / countVideogiochi : 0;
        double mediaGiochiDaTavolo = countGiochiDaTavolo > 0 ? sommaPrezziGiochiDaTavolo / countGiochiDaTavolo : 0;

        System.out.println("Media dei prezzi dei videogiochi: " + mediaVideogiochi);
        System.out.println("Media dei prezzi dei giochi da tavolo: " + mediaGiochiDaTavolo);
    }

    public List<Gioco> getGiochi() {
        return giochi;
    }
}
