package pos.main.model;

public class Item_Size {

	int id;
	String title;

	// constructors
	public Item_Size() {
	}
	
	public Item_Size(String title) {
		this.title = title;
	}

	public Item_Size(int id, String title) {
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}