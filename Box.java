import java.util.ArrayList;

public class Box implements Comparable<Box>{
	private int number;
	private Integer lock;
	private ArrayList<Integer> storedKeys;
	
	public Box(int number, int lock, ArrayList<Integer> storedKeys) {
		super();
		this.number = number;
		this.lock = lock;
		this.storedKeys = storedKeys;
	}

	public int getNumber() {
		return number;
	}

	public int getLock() {
		return lock;
	}

	public void openBox(ArrayList<Integer> keys){
		if(!keys.contains(lock)) return;
		keys.remove(lock);
		keys.addAll(storedKeys);
	}
	
	
	@Override
	public String toString(){
		return String.format("%2d) lock: %s stores: %s", number, lock, storedKeys);
	}

	@Override
	public int compareTo(Box other) {
		return other.storedKeys.size() - this.storedKeys.size();
	}
	
	
}
