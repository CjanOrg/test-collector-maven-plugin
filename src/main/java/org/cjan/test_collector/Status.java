package org.cjan.test_collector;

/**
 * Valid statuses for CJAN.org upload results.
 * 
 * @author Bruno P. Kinoshita
 * @since 0.1
 */
public enum Status {

	SUCCESS(1),
	FAILURE(2),
	SKIP(3),
	UNKNOWN(4);
	
	int id;
	
	Status(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static Status fromId(int id) {
		switch (id) {
		case 1:
			return SUCCESS;
		case 2:
			return FAILURE;
		case 3:
			return SKIP;
		case 5:
			return UNKNOWN;
		default:
			return null;
		}
	}
	
}
