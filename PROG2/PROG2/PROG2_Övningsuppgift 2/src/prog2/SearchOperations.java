package prog2;

import java.util.Collection;
import java.util.Optional;
import java.util.SortedSet;

public interface SearchOperations {
	/**
	 * Kontrollerar om det finns en artist med det s�kta namnet.
	 * <p>
	 * Exempel:
	 * doesArtistExist("Phoebe Bridgers") => false
	 * doesArtistExist("Miles Davis") => true
	 *
	 * @param name Artistens namn
	 * @return true om artisten finns, false om inte
	 */
	boolean doesArtistExist(String name);

	/**
	 * Ger en omodifierbar samling med genrer.
	 * <p>
	 * Exempel:
	 * getGenres() => en samling med 19 genrer.
	 *
	 * @return en omodifierbar samling med genrer
	 */
	Collection<String> getGenres();

	/**
	 * H�mtar inspelning med den s�kta titeln.
	 * <p>
	 * Exempel:
	 * getRecordingByName("Punisher").isPresent() == false
	 * getRecordingByName("Giant Steps").isPresent() == true
	 *
	 * @param title Inspelningens titel
	 * @return ett Optional-objekt som inneh�ller den s�kta inspelningen om den hittades
	 */
	Optional<Recording> getRecordingByName(String title);

	/**
	 * H�mtar en omodifierbar samling med inspelningar fr�n och med det angivna �ret.
	 * <p>
	 * Exempel:
	 * getRecordingsAfter(2010) => samling med 2 objekt
	 *
	 * @param year �ret som s�kningen startar fr�n (och inkluderar)
	 * @return en omodifierbar samling med inspelningar
	 */
	Collection<Recording> getRecordingsAfter(int year);

	/**
	 * H�mtar en omodifierbar samling med inspelningar av artisten
	 * sorterade i stigande ordning p� �r.
	 * <p>
	 * Exempel:
	 * getRecordingsByArtistOrderedByYearAsc("John Coltrane") => samling med 2 objekt
	 *
	 * @param artist �ret som s�kningen l�per till (och exkluderar)
	 * @return ett omodifierbar samling med inspelningar
	 */
	SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist);

	/**
	 * H�mtar en omodifierbar samling med inspelningar i genren.
	 * <p>
	 * Exempel:
	 * getRecordingsByGenre("Jazz") => samling med 19 objekt
	 *
	 * @param genre den s�kta genren
	 * @return ett omodifierbar samling med inspelningar
	 */
	Collection<Recording> getRecordingsByGenre(String genre);

	/**
	 * H�mtar en omodifierbar samling med inspelningar i genren gjorda
	 * mellan de angivna �ren
	 * <p>
	 * Exempel:
	 * getRecordingsByGenreAndYear("Jazz", 1950, 1960) => samling med 7 objekt
	 *
	 * @param genre    den efterfr�gade genren
	 * @param yearFrom f�rsta �ret i intervallet
	 * @param yearTo   sista �ret i intervallet
	 * @return en omodifierbar samling
	 */
	Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo);

	/**
	 * Tar emot en samling och returnerar en ny samling
	 * som bara inneh�ller de som inte redan fanns i databasen.
	 *
	 * @param offered En samling med inspelningar
	 * @return en omodifierbar samling med de inspelningar som inte redan finns
	 */
	Collection<Recording> offerHasNewRecordings(Collection<Recording> offered);

	/**
	 * H�mtar en omodifierbar samling med inspelningar innan det angivna �ret.
	 * <p>
	 * Exempel:
	 * optionalGetRecordingsBefore(1960) => samling med 10 objekt daterade < 1960
	 *
	 * @param year �ret som s�kningen l�per till (och exkluderar)
	 * @return ett omodifierbar samling med inspelningar
	 */
	default Collection<Recording> optionalGetRecordingsBefore(int year) {
		return null;
	}

	/**
	 * H�mtar en omodifierbar samling med inspelningar av den s�kta artisten.
	 * Samlingen ska vara sorterad p� titel i stigande ordning A -> Z
	 * <p>
	 * Exempel:
	 * optionalGetRecordingsByArtistOrderedByTitleAsc(1960) => sorterad samling med 10 objekt
	 *
	 * @param artist artisten s�kningen g�ller
	 * @return ett omodifierbar samling med inspelningar sorterade p� titel
	 */
	default SortedSet<Recording> optionalGetRecordingsByArtistOrderedByTitleAsc(String artist) {
		return null;
	}

	/**
	 * H�mtar en omodifierbar samling med inspelningar fr�n det angivna �ret.
	 * <p>
	 * Exempel:
	 * optionalGetRecordingsFrom(1983) => samling med 6 objekt
	 *
	 * @param year �ret som s�kningen g�ller
	 * @return en omodifierbar samling med inspelningar
	 */
	default Collection<Recording> optionalGetRecordingsFrom(int year) {
		return null;
	}

}
