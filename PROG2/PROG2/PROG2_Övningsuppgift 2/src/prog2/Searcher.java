package prog2;

import java.util.*;


public class Searcher implements Stats,SearchOperations{
	private Collection<Recording> recordings;
	private Map<String,Recording> byTitle= new HashMap<>();
	private Map<String,Set<Recording>> byGenera= new HashMap<>();
	private Map<String,Set<Recording>> byArtist= new HashMap<>();
	public Searcher(Collection<Recording> data) {
		
		recordings = data;
		for(Recording r: recordings) {
			byTitle.put(r.getTitle(),r);

			for(String s: r.getGenre())
			{
				Set<Recording>sameGenera=byGenera.getOrDefault(s, new HashSet<>());
				byGenera.put(s,sameGenera);
				sameGenera.add(r);


				Set<Recording>sameArtist=byArtist.getOrDefault(s, new HashSet<>());
				byArtist.put(s,sameArtist);
				sameArtist.add(r);
			}
		}
	}



	@Override
	public long numberOfArtists() {

		Set<String> set = new HashSet<>();
		for (Recording recording : recordings) {
			set.add(recording.getArtist());
		}

		return set.size();
	}

	@Override
	public long numberOfGenres() {

		Set<String> set = new HashSet<>();
		for(Recording r: recordings)
		{
			for(String s: r.getGenre())
			{
				set.add(s);
			}
		}
		return set.size();
	}

	@Override
	public long numberOfTitles() {

		Set<String> set = new HashSet<>();
		for (Recording recording : recordings) {
			set.add(recording.getTitle());
		}

		return set.size();
	}


	@Override
	public boolean doesArtistExist(String name) {
		Set<String> set = new HashSet<>();
		for (Recording recording : recordings) {
			set.add(recording.getArtist());
		}
		return set.contains(name);

	}

	@Override
	public Collection<String> getGenres() {
		Set<String> set = new HashSet<>();
		for(Recording r: recordings)
		{
			for(String s: r.getGenre())
			{
				set.add(s);
			}
		}
		return Collections.unmodifiableCollection(set);
	}

	@Override
	public Optional<Recording> getRecordingByName(String title) {
		Map<String,Recording>map = new HashMap<>();

		for (Recording recording : recordings) {
			map.put(recording.getTitle(),recording);
		}
		return Optional.ofNullable(map.get(title));
	}

	@Override
	public Collection<Recording> getRecordingsAfter(int year) {

		Map<String,Recording>map = new HashMap<>();

		for (Recording recording : recordings) {
			if(recording.getYear()>=year)
			{

				map.putIfAbsent(recording.getTitle(),recording);

			}



		}
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {

		TreeSet<Recording>set = new TreeSet<>(new YearComparator());

		for (Recording recording : recordings) {
			if(recording.getArtist().compareToIgnoreCase(artist)==0)
			{
				set.add(recording);
			}
		}

		return Collections.unmodifiableSortedSet(set) ;
	}

	@Override
	public Collection<Recording> getRecordingsByGenre(String genre) {

		Map<String,Recording>map = new HashMap<>();

		for (Recording recording : recordings) {

				if(recording.getGenre().contains(genre))
				{
					map.putIfAbsent(recording.getTitle(),recording);
				}


		}
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
		Map<String,Recording>map = new HashMap<>();

		for (Recording recording : recordings) {

			if(recording.getGenre().contains(genre) && recording.getYear()<=yearTo && recording.getYear()>=yearFrom)
			{
				map.putIfAbsent(recording.getTitle(),recording);
			}


		}
		return Collections.unmodifiableCollection(map.values());
	}

	@Override
	public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
        Map<String,Recording>map = new HashMap();
//        Set<Recording>set1=new HashSet<>();
//
//        for (Recording recording : recordings) {
//            set.add(recording);
//        }
//        ArrayList<Recording>arrayList=new ArrayList<>(set);
//
//        for(Recording recording: offered)
//        {
//            if(!isExist(recording,0,arrayList.size(),arrayList))
//            {
//                set1.add(recording);
//            }
//
//        }

        //LinearSearch
        for(Recording recording:recordings)
        {
            map.put(recording.getTitle(),recording);
        }
        Set<Recording>set1 = new HashSet<>();

        for(Recording recording: offered)
        {
            if(!map.containsKey(recording.getTitle()))
            set1.add(recording);
        }
        //return set;
        return Collections.unmodifiableCollection(set1);
	}



	public class YearComparator implements Comparator<Recording>{

		@Override
		public int compare(Recording o1, Recording o2) {

			return o1.getYear()- o2.getYear();
		}
	}


}



/*import java.util.*;


/*public class Searcher implements Stats,SearchOperations{
	private Map<String,Recording> byTitle= new HashMap<>();
	private Map<String,Set<Recording>> byGenera= new HashMap<>(); 
	private Map<String,Set<Recording>> byArtist= new HashMap<>(); 
	
	//private Collection<Recording> recordings;
	//private Collection<Recording> recordings1;
	
	public Searcher(Collection<Recording> data) {
		Collection<Recording> recordings=data;
		//recordings = data;
		//recordings1 = data;

      for(Recording r: recordings) {
    	  byTitle.put(r.getTitle(),r);
    	  
  			for(String s: r.getGenre())
  			{
  				Set<Recording>sameGenera=byGenera.getOrDefault(s, new HashSet<>());
  				byGenera.put(s,sameGenera);
  				sameGenera.add(r);
  			
  			
  				Set<Recording>sameArtist=byArtist.getOrDefault(s, new HashSet<>());
  				byArtist.put(s,sameArtist);
  				sameArtist.add(r);
  			}
      }
  		}*/
      
	