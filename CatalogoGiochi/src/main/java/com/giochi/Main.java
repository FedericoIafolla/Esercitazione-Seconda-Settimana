package com.giochi;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Collezione collezione = new Collezione();
        Scanner scanner = new Scanner(System.in);
        int scelta = -1;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Cerca gioco per ID");
            System.out.println("2. Cerca giochi per prezzo");
            System.out.println("3. Cerca giochi da tavolo per numero di giocatori");
            System.out.println("4. Rimuovi gioco per ID");
            System.out.println("5. Aggiorna gioco per ID");
            System.out.println("6. Statistiche collezione");
            System.out.println("7. Aggiungi un nuovo gioco");
            System.out.println("8. Mostra lista giochi");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");

            try {
                scelta = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Input non valido. Per favore, inserisci un numero.");
                scanner.next();
                continue;
            }

            switch (scelta) {
                case 1:
                    cercaGiocoPerID(collezione, scanner);
                    break;

                case 2:
                    cercaGiochiPerPrezzo(collezione, scanner);
                    break;

                case 3:
                    cercaGiochiDaTavoloPerGiocatori(collezione, scanner);
                    break;

                case 4:
                    rimuoviGioco(collezione, scanner);
                    break;

                case 5:
                    aggiornaGioco(collezione, scanner);
                    break;

                case 6:
                    collezione.statistiche();
                    break;

                case 7:
                    aggiungiGioco(collezione, scanner);
                    break;

                case 8:
                    mostraListaGiochi(collezione);
                    break;

                case 0:
                    System.out.println("Uscita dal programma.");
                    break;

                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        } while (scelta != 0);

        scanner.close();
    }

    private static void cercaGiocoPerID(Collezione collezione, Scanner scanner) {
        try {
            System.out.print("Inserisci ID gioco: ");
            String idCerca = scanner.next();
            collezione.cercaPerID(idCerca).ifPresentOrElse(
                    gioco -> System.out.println("Gioco trovato: " + gioco.getTitolo()),
                    () -> System.out.println("Nessun gioco trovato con ID: " + idCerca)
            );
        } catch (Exception e) {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }

    private static void cercaGiochiPerPrezzo(Collezione collezione, Scanner scanner) {
        try {
            System.out.print("Inserisci prezzo: ");
            double prezzo = scanner.nextDouble();
            System.out.println("Giochi con prezzo inferiore a " + prezzo + ":");
            collezione.cercaPerPrezzo(prezzo).forEach(gioco ->
                    System.out.println("- " + gioco.getTitolo() + " - Prezzo: " + gioco.getPrezzo())
            );
        } catch (Exception e) {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }

    private static void cercaGiochiDaTavoloPerGiocatori(Collezione collezione, Scanner scanner) {
        try {
            System.out.print("Inserisci numero di giocatori: ");
            int numGiocatori = scanner.nextInt();
            System.out.println("Giochi da tavolo per " + numGiocatori + " giocatori:");
            collezione.cercaPerNumeroGiocatori(numGiocatori).forEach(gioco ->
                    System.out.println("- " + gioco.getTitolo())
            );
        } catch (Exception e) {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }

    private static void rimuoviGioco(Collezione collezione, Scanner scanner) {
        try {
            System.out.print("Inserisci ID gioco da rimuovere: ");
            String idRimuovi = scanner.next();
            collezione.rimuoviGioco(idRimuovi);
            System.out.println("Gioco rimosso con successo.");
        } catch (Exception e) {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }

    private static void aggiornaGioco(Collezione collezione, Scanner scanner) {
        try {
            System.out.print("Inserisci ID gioco da aggiornare: ");
            String idAggiorna = scanner.next();

            if (collezione.cercaPerID(idAggiorna).isEmpty()) {
                System.out.println("Nessun gioco trovato con ID: " + idAggiorna);
                return;
            }

            System.out.print("Inserisci nuovo titolo: ");
            String titoloNuovo = scanner.next();
            System.out.print("Inserisci nuovo anno di pubblicazione: ");
            int annoNuovo = scanner.nextInt();
            System.out.print("Inserisci nuovo prezzo: ");
            double prezzoNuovo = scanner.nextDouble();

            Gioco giocoEsistente = collezione.cercaPerID(idAggiorna).get();

            if (giocoEsistente instanceof GiocoDaTavolo) {
                System.out.print("Inserisci numero di giocatori (se gioco da tavolo): ");
                int numeroGiocatoriNuovo = scanner.nextInt();
                System.out.print("Inserisci durata media di una partita (se gioco da tavolo): ");
                int durataMediaNuova = scanner.nextInt();

                GiocoDaTavolo nuovoGioco = new GiocoDaTavolo(idAggiorna, titoloNuovo, annoNuovo, prezzoNuovo, numeroGiocatoriNuovo, durataMediaNuova);
                collezione.aggiornaGioco(idAggiorna, nuovoGioco);
            } else {
                System.out.print("Inserisci piattaforma (es. PC, PS5, Xbox): ");
                String piattaforma = scanner.next();
                System.out.print("Inserisci durata di gioco (in ore): ");
                int durataGioco = scanner.nextInt();
                System.out.print("Inserisci genere (0: AZIONE, 1: HORROR, 2: AVVENTURA, 3: FPS, 4: TPS): ");
                int genereIndex = scanner.nextInt();
                Genere genere = Genere.values()[genereIndex];

                Videogioco nuovoVideogioco = new Videogioco(idAggiorna, titoloNuovo, annoNuovo, prezzoNuovo, piattaforma, durataGioco, genere);
                collezione.aggiornaGioco(idAggiorna, nuovoVideogioco);
            }

            System.out.println("Gioco aggiornato con successo.");
        } catch (Exception e) {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }


    private static void aggiungiGioco(Collezione collezione, Scanner scanner) {
        try {
            System.out.print("Vuoi aggiungere un videogioco (v) o un gioco da tavolo (t)? ");
            char tipoGioco = scanner.next().charAt(0);
            scanner.nextLine();

            System.out.print("Inserisci ID gioco: ");
            String idGioco = scanner.nextLine();
            System.out.print("Inserisci titolo: ");
            String titoloGioco = scanner.nextLine();
            System.out.print("Inserisci anno di pubblicazione: ");
            int annoGioco = scanner.nextInt();
            System.out.print("Inserisci prezzo: ");
            double prezzoGioco = scanner.nextDouble();

            if (tipoGioco == 'v') {
                System.out.print("Inserisci piattaforma (es. PC, PS5, Xbox): ");
                String piattaforma = scanner.next();
                System.out.print("Inserisci durata di gioco (in ore): ");
                int durataGioco = scanner.nextInt();
                System.out.print("Inserisci genere (0: AZIONE, 1: HORROR, 2: AVVENTURA, 3: FPS, 4: TPS): ");
                int genereIndex = scanner.nextInt();
                Genere genere = Genere.values()[genereIndex];
                Videogioco nuovoVideogioco = new Videogioco(idGioco, titoloGioco, annoGioco, prezzoGioco, piattaforma, durataGioco, genere);
                collezione.aggiungiGioco(nuovoVideogioco);
                System.out.println("Videogioco aggiunto con successo.");
            } else if (tipoGioco == 't') {
                System.out.print("Inserisci numero di giocatori (da 2 a 10): ");
                int numeroGiocatori = scanner.nextInt();
                System.out.print("Inserisci durata media di una partita (in minuti): ");
                int durataPartita = scanner.nextInt();
                GiocoDaTavolo nuovoGiocoDaTavolo = new GiocoDaTavolo(idGioco, titoloGioco, annoGioco, prezzoGioco, numeroGiocatori, durataPartita);
                collezione.aggiungiGioco(nuovoGiocoDaTavolo);
                System.out.println("Gioco da tavolo aggiunto con successo.");
            } else {
                System.out.println("Tipo di gioco non valido.");
            }
        } catch (Exception e) {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }

    private static void mostraListaGiochi(Collezione collezione) {
        try {
            System.out.println("Lista dei giochi nella collezione:");

            System.out.println("Videogiochi:");
            collezione.getGiochi().stream()
                    .filter(g -> g instanceof Videogioco)
                    .forEach(gioco -> {
                        Videogioco v = (Videogioco) gioco;
                        System.out.println("ID: " + v.getIdGioco() +
                                ", Titolo: " + v.getTitolo() +
                                ", Anno: " + v.getAnnoPubblicazione() +
                                ", Prezzo: " + v.getPrezzo() +
                                ", Piattaforma: " + v.getPiattaforma() +
                                ", Durata: " + v.getDurataGioco() + " ore" +
                                ", Genere: " + v.getGenere());
                    });

            System.out.println("Giochi da tavolo:");
            collezione.getGiochi().stream()
                    .filter(g -> g instanceof GiocoDaTavolo)
                    .forEach(gioco -> {
                        GiocoDaTavolo t = (GiocoDaTavolo) gioco;
                        System.out.println("ID: " + t.getIdGioco() +
                                ", Titolo: " + t.getTitolo() +
                                ", Anno: " + t.getAnnoPubblicazione() +
                                ", Prezzo: " + t.getPrezzo() +
                                ", Numero Giocatori: " + t.getNumeroGiocatori() +
                                ", Durata: " + t.getDurataMedia() + " minuti");
                    });

        } catch (Exception e) {
            System.out.println("Si è verificato un errore durante la visualizzazione dei giochi: " + e.getMessage());
        }
    }
}
