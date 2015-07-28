package org.cjan.test_collector;

public final class Test {

	private final String name;
	private final Status status;
	private final String metadata;

	public Test(String name, Status status, String metadata) {
		this.name = name;
		this.status = status;
		this.metadata = metadata;
	}

	public String getName() {
		return name;
	}

	public Status getStatus() {
		return status;
	}

	public String getMetadata() {
		return metadata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((metadata == null) ? 0 : metadata.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (metadata == null) {
			if (other.metadata != null)
				return false;
		} else if (!metadata.equals(other.metadata))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Test [name=" + name + ", status="
				+ status + ", metadata=" + metadata + "]";
	}

}
