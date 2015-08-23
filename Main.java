import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	String temp=" ";
	public static void main(String[] args) {
		String filename = "chestsAndTreasure.txt";
		for (int i = 0; i < 9; i++) {
			processTask(filename, i);
			System.out.println();
		}
	}

	public static void processTask(String filename, int tasknum) {
		ArrayList<Integer> keys = new ArrayList<>();
		ArrayList<Box> boxes = new ArrayList<>();
		boolean readBegin = (tasknum == 0) ? true : false;
		int sepnum = 0;
		int keysQty = 0;
		int boxesQty = 0;


		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			int i = 1;
			String str = "";

			while ((str = br.readLine()) != null) {
				if (readBegin && !str.matches("^\\s*$")) {
					switch (i) {
					case 1:
						keysQty = Integer.parseInt(str.split("\\s+")[0]);
						boxesQty = Integer.parseInt(str.split("\\s+")[1]);
						break;
					case 2:
						for (int j = 0; j < keysQty; j++) {
							keys.add(Integer.parseInt(str.split("\\s+")[j]));
						}
						break;
					default:
						int k = 1;
						int lock = 0;
						ArrayList<Integer> storedKeys = new ArrayList<>();
						for (String v : str.split("\\s+")) {
							switch (k) {
							case 1:
								lock = Integer.parseInt(v);
								break;
							case 2:
								break;
							default:
								storedKeys.add(Integer.parseInt(v));
							}
							k++;
						}
						boxes.add(new Box(i - 2, lock, storedKeys));
					}

					if (++i >= boxesQty + 3)
						break;

				} else if (str.matches("^\\s*$")) {
					if (tasknum == ++sepnum)
						readBegin = true;
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading file!");
		}

		if (keys.isEmpty() || boxes.isEmpty()) {
			System.out
					.println("Error intializing task from file! Check task number!");
		} else {
			System.out.println("Task N" + (tasknum + 1) + " initial data:");
			System.out.println("Keys: " + keys);
			System.out.println("Boxes:");

			for (Box bx : boxes) {
				System.out.println(bx);
			}

			System.out.println("The answer is:");
			System.out.println((new Main()).solution(keys, boxes, " "));
		}
	}
	
	public String solution (ArrayList<Integer> keys, ArrayList<Box> boxes, String answer) {
		ArrayList<Box> tempBoxes=boxes;
		ArrayList<Integer> tempKeys=keys;
		ArrayList<Box> openable = openablePairs(tempBoxes, tempKeys);
		for (int i=0; !openable.isEmpty() && !tempBoxes.isEmpty()  && i<openable.size(); i++){
			tempBoxes.remove(openable.get(i));
			temp+="->" + openable.get(i).getNumber();
			openable.get(i).openBox(tempKeys);
			solution (tempKeys, tempBoxes, temp);
		}
		if ( tempBoxes.isEmpty() ) return temp;
		else return "Task unsolvable, all variants failed";
	}
	
	public static ArrayList<Box> openablePairs (ArrayList<Box> boxes, ArrayList<Integer> keys) {
		ArrayList<Box> openables = new ArrayList<Box>();
		for (Box bx : boxes) {
			if ( keys.contains(bx.getLock()) )   
				openables.add(bx);
		}
		return openables;
	}
}
