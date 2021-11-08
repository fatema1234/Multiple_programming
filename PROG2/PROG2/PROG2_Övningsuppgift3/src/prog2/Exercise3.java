package prog2;

import java.io.*;
import java.util.*;

public class Exercise3 {

	private final List<Recording> recordings = new ArrayList<>();

	public void exportRecordings(String fileName) {

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {

		}
		PrintWriter printWriter = new PrintWriter(fileOutputStream);
		for (Recording recording:recordings)
		{
			printWriter.println("<recording>");
			printWriter.println("  <artist>"+recording.getArtist()+"</artist>");
			printWriter.println("  <title>"+recording.getTitle()+"</title>");
			printWriter.println("  <year>"+recording.getYear()+"</year>");
			printWriter.println("  <genres>");
			for (String genre: recording.getGenre())
			{
				printWriter.println("    <genre>"+genre+"</genre>");
			}
			printWriter.println("  </genres>");
			printWriter.println("</recording>");
		}
		printWriter.flush();


	}

	public void importRecordings(String fileName) throws FileNotFoundException {

		DataInputStream dis = new DataInputStream(new FileInputStream(fileName));


		try {
		int times =Integer.parseInt(dis.readLine().split(":")[0])	;
			for(int i=0;i<times;i++)
			{
				String[] informations = dis.readLine().split(";");
				int totalGenre =Integer.parseInt(dis.readLine());
				Set<String>genres=new HashSet<>();
				for (int j=0;j<totalGenre;j++)
				{
					genres.add(dis.readLine());
				}
				Recording recording = new Recording(informations[1],informations[0],Integer.parseInt(informations[2]),genres);
				recordings.add(recording);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}





	}

	public Map<Integer, Double> importSales(String fileName) throws FileNotFoundException {
		//DataInputStream dis = new DataInputStream(new FileInputStream(fileName));
		Map<Integer, Double> data = new HashMap<>();
		final int factor = 100;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(fileName));
//			while (dis.available()>0)
//				System.out.println(dis.readInt());
			int times = dis.readInt();

			for (int i = 0; i<times; i++)
			{
				int year = dis.readInt();
				
				int month = dis.readInt();
				int day = dis.readInt();

				double sell = dis.readDouble();

//				System.out.println(year);
//				System.out.println(day);
//				System.out.println(month);
//				System.out.println(sell);
				//dis.readInt();
				int key = year*factor +month;
				
				double oldValue = data.getOrDefault(key, 0.0);
				 

			//if(day<10)
			//{
				//key+="0"+String.valueOf(day);
			//}
			//else
			//{
			//	key+=String.valueOf(day);
			//}
				data.put(key,oldValue+sell);


			}
		 
		}catch(FileNotFoundException e){
		    throw new FileNotFoundException(); 
		    
		}catch (IOException e) {
			e.printStackTrace();
		}	
		return data;
	}

	public List<Recording> getRecordings() {
		return Collections.unmodifiableList(recordings);
	}

	public void setRecordings(List<Recording> recordings) {
		this.recordings.clear();
		this.recordings.addAll(recordings);
	}
	/*public static void main(String...arg){
		Exercise3 exercise3 = new Exercise3();
		String path = "C:\\Users\\Fatema Ferdousy\\eclipse-workspace\\PROG2_�vningsuppgift3\\src\\prog2\\";
		try{
			exercise3.importRecordings(path+"recording_input.txt");
			exercise3.exportRecordings("recording_output1.txt");
			exercise3.importSales(path+"sales_input.bin");
			exercise3.getRecordings();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
		}
	}*/
}