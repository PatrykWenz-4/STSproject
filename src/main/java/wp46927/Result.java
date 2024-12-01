package wp46927;

public class Result {
	private int status;
	private String entity;
	private String message;
	
	public Result(int status, String entity, String message) {
		super();
		this.status = status;
		this.entity = entity;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
