package br.edu.utfpr.dv.siacoes.model;

public class JuryStudent {
	
	private int idJuryStudent;
	private Jury jury;
	private User student;
	
	public JuryStudent(){
		this.setJury(new Jury());
		this.setStudent(new User());
	}
	
	public int getIdJuryStudent(){
		return idJuryStudent;
	}
	
	public void setIdJuryStudent(int idJuryStudent){
		this.idJuryStudent = idJuryStudent;
	}
	
	public Jury getJury() {
		return jury;
	}
	public void setJury(Jury jury) {
		this.jury = jury;
	}
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}

}
