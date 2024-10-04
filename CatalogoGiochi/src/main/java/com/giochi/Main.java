package com.giochi;

import java.util.Scanner;

@FunctionalInterface
interface Operazione {
    void esegui(String argomento);
}

public class Main {
    public static void main(String[] args) {
        Collezione collezione = new Collezione();
        Scanner scanner = new Scanner(System.in);
        int scelta;

        do {
            mostraMenu();
            scelta = leggiScelta(scanner);
            gestisciScelta(collezione, scanner, scelta);
        } while (scelta != 0);

        scanner.close();
    }

    private static void mostraMenu() {
        System.out.println("\nMenu:\n" +
                "1. Cerca gioco per ID\n" +
                "2. Cerca giochi per prezzo\n" +
                "3. Cerca giochi da tavolo per numero di giocatori\n" +
                "4. Rimuovi gioco per ID\n" +
                "5. Aggiorna gioco per ID\n" +
                "6. Statistiche collezione\n" +
                "7. Aggiungi un nuovo gioco\n" +
                "8. Mostra lista giochi\n" +
                "0. Esci\n" +
                "Scegli un'opzione: ");
    }

    private static int leggiScelta(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Input non valido. Per favore, inserisci un numero.");
                scanner.next();
            }
        }
    }

    private static void gestisciScelta(Collezione collezione, Scanner scanner, int scelta) {
        switch (scelta) {
            case 1: cercaGiocoPerID(collezione, scanner); break;
            case 2: cercaGiochiPerPrezzo(collezione, scanner); break;
            case 3: cercaGiochiDaTavoloPerGiocatori(collezione, scanner); break;
            case 4: modificaGioco(collezione, scanner, "rimuovi"); break;
            case 5: modificaGioco(collezione, scanner, "aggiorna"); break;
            case 6: collezione.statistiche(); break;
            case 7: aggiungiGioco(collezione, scanner); break;
            case 8: mostraListaGiochi(collezione); break;
            case 0: System.out.println("Uscita dal programma."); break;
            default: System.out.println("Scelta non valida. Riprova.");
        }
    }

    private static void cercaGiocoPerID(Collezione collezione, Scanner scanner) {
        eseguiOperazione(scanner, "Inserisci ID gioco: ", idCerca -> {
            collezione.cercaPerID(idCerca).ifPresentOrElse(
                    gioco -> System.out.println("Gioco trovato: " + gioco.getTitolo()),
                    () -> System.out.println("Nessun gioco trovato con ID: " + idCerca)
            );
        });
    }

    private static void cercaGiochiPerPrezzo(Collezione collezione, Scanner scanner) {
        eseguiOperazione(scanner, "Inserisci prezzo: ", prezzoStr -> {
            double prezzo = Double.parseDouble(prezzoStr);
            System.out.println("Giochi con prezzo inferiore a " + prezzo + ":");
            collezione.cercaPerPrezzo(prezzo).forEach(gioco ->
                    System.out.println("- " + gioco.getTitolo() + " - Prezzo: " + gioco.getPrezzo())
            );
        });
    }

    private static void cercaGiochiDaTavoloPerGiocatori(Collezione collezione, Scanner scanner) {
        eseguiOperazione(scanner, "Inserisci numero di giocatori: ", numGiocatoriStr -> {
            int numGiocatori = Integer.parseInt(numGiocatoriStr);
            System.out.println("Giochi da tavolo per " + numGiocatori + " giocatori:");
            collezione.cercaPerNumeroGiocatori(numGiocatori).forEach(gioco ->
                    System.out.println("- " + gioco.getTitolo())
            );
        });
    }

    private static void modificaGioco(Collezione collezione, Scanner scanner, String azione) {
        eseguiOperazione(scanner, "Inserisci ID gioco da aggiornare: ", idGioco -> {
            if (azione.equals("rimuovi")) {
                collezione.rimuoviGioco(idGioco);
                System.out.println("Gioco rimosso con successo.");
            } else {
                aggiornaGioco(collezione, scanner, idGioco);
            }
        });
    }

    private static void eseguiOperazione(Scanner scanner, String messaggio, Operazione operazione) {
        try {
            System.out.print(messaggio);
            operazione.esegui(scanner.next());
        } catch (Exception e) {
            System.out.println("Si è verificato un errore: " + e.getMessage());
        }
    }

    private static void aggiornaGioco(Collezione collezione, Scanner scanner, String idAggiorna) {
        if (collezione.cercaPerID(idAggiorna).isEmpty()) {
            System.out.println("Nessun gioco trovato con ID: " + idAggiorna);
            return;
        }

        String titoloNuovo = leggiDati(scanner, "Inserisci nuovo titolo: ");
        int annoNuovo = leggiDatiInt(scanner, "Inserisci nuovo anno di pubblicazione: ");
        double prezzoNuovo = leggiDatiDouble(scanner, "Inserisci nuovo prezzo: ");
        Gioco giocoEsistente = collezione.cercaPerID(idAggiorna).get();

        if (giocoEsistente instanceof GiocoDaTavolo) {
            int numeroGiocatoriNuovo = leggiDatiInt(scanner, "Inserisci numero di giocatori: ");
            int durataMediaNuova = leggiDatiInt(scanner, "Inserisci durata media di una partita: ");
            GiocoDaTavolo nuovoGioco = new GiocoDaTavolo(idAggiorna, titoloNuovo, annoNuovo, prezzoNuovo, numeroGiocatoriNuovo, durataMediaNuova);
            collezione.aggiornaGioco(idAggiorna, nuovoGioco);
        } else {
            String piattaforma = leggiDati(scanner, "Inserisci piattaforma: ");
            int durataGioco = leggiDatiInt(scanner, "Inserisci durata di gioco: ");
            int genereIndex = leggiDatiInt(scanner, "Inserisci genere (0-4): ");
            Genere genere = Genere.values()[genereIndex];
            Videogioco nuovoVideogioco = new Videogioco(idAggiorna, titoloNuovo, annoNuovo, prezzoNuovo, piattaforma, durataGioco, genere);
            collezione.aggiornaGioco(idAggiorna, nuovoVideogioco);
        }

        System.out.println("Gioco aggiornato con successo.");
    }

    private static void aggiungiGioco(Collezione collezione, Scanner scanner) {
        boolean inputValido = false;
        char tipoGioco = ' ';

        while (!inputValido) {
            System.out.print("Vuoi aggiungere un videogioco (v) o un gioco da tavolo (t)? ");
            tipoGioco = scanner.next().toLowerCase().charAt(0);
            scanner.nextLine();

            if (tipoGioco == 'v' || tipoGioco == 't') {
                inputValido = true;
            } else {
                System.out.println("Input non valido. Devi digitare 'v' per videogioco o 't' per gioco da tavolo.");
            }
        }

        String idGioco = leggiDati(scanner, "Inserisci ID gioco: ");
        String titoloGioco = leggiDati(scanner, "Inserisci titolo: ");
        int annoGioco = leggiDatiInt(scanner, "Inserisci anno di pubblicazione: ");
        double prezzoGioco = leggiDatiDouble(scanner, "Inserisci prezzo: ");

        if (tipoGioco == 'v') {
            aggiungiVideogioco(collezione, scanner, idGioco, titoloGioco, annoGioco, prezzoGioco);
        } else if (tipoGioco == 't') {
            int numeroGiocatori = leggiDatiInt(scanner, "Inserisci numero di giocatori (da 2 a 10): ");
            int durataPartita = leggiDatiInt(scanner, "Inserisci durata media di una partita (in minuti): ");
            GiocoDaTavolo nuovoGiocoDaTavolo = new GiocoDaTavolo(idGioco, titoloGioco, annoGioco, prezzoGioco, numeroGiocatori, durataPartita);
            collezione.aggiungiGioco(nuovoGiocoDaTavolo);
            System.out.println("Gioco da tavolo aggiunto con successo.");
        }
    }

    private static void aggiungiVideogioco(Collezione collezione, Scanner scanner, String idGioco, String titoloGioco, int annoGioco, double prezzoGioco) {
        String piattaforma = leggiDati(scanner, "Inserisci piattaforma: ");
        int durataGioco = leggiDatiInt(scanner, "Inserisci durata di gioco (in ore): ");
        int genereIndex = leggiDatiInt(scanner, "Inserisci genere (0-4): ");
        Genere genere = Genere.values()[genereIndex];
        Videogioco nuovoVideogioco = new Videogioco(idGioco, titoloGioco, annoGioco, prezzoGioco, piattaforma, durataGioco, genere);
        collezione.aggiungiGioco(nuovoVideogioco);
        System.out.println("Videogioco aggiunto con successo.");
    }

    private static String leggiDati(Scanner scanner, String messaggio) {
        System.out.print(messaggio);
        return scanner.nextLine();
    }

    private static int leggiDatiInt(Scanner scanner, String messaggio) {
        System.out.print(messaggio);
        return leggiScelta(scanner);
    }

    private static double leggiDatiDouble(Scanner scanner, String messaggio) {
        System.out.print(messaggio);
        return scanner.nextDouble();
    }

    private static void mostraListaGiochi(Collezione collezione) {
        System.out.println("Lista dei giochi nella collezione:");
        collezione.mostraGiochi();
    }
}
