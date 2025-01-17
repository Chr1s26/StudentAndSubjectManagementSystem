package Service;

import java.io.IOException;
import Dao.ExamDao;
import Dao.ExamDaoImp;
import Dao.StudentDao;
import Dao.StudentDaoImp;
import Dao.SubjectDao;
import Dao.SubjectDaoImp;
import Model.Exam;
import Model.Student;
import Model.Subject;

public class ExamService extends BaseService {
	private Exam exam;
	private SubjectService subjectService;
	private StudentDao studentDao;
	private SubjectDao subjectDao;
	private ExamDao examDao;
	
	public ExamService() {
		subjectService = new SubjectService();
		studentDao = new StudentDaoImp();
		subjectDao = new SubjectDaoImp();
		examDao  = new ExamDaoImp();
	}
	
	public void create() throws IOException {
		System.out.println("***Exam Create Form***");
		System.out.println("Enter exam title : ");
		String title = bufferedReader.readLine();
		exam = new Exam(title);
		this.findStudent();
		this.addSubjectAndMarks();
		examDao.addExam(exam);
	}
	
	
	public void addSubjectAndMarks() throws IOException {
		Subject subject =selectSubject();
		int marks = getMarkForSubject(subject);
		this.exam.addSubjectMarks(subject, marks);
		System.out.println("Do you want to add subject more (yes/no) ?");
		String flag = bufferedReader.readLine();
		if(flag.equalsIgnoreCase("yes")) {
			addSubjectAndMarks();
		}
	}
	
	public void findStudent() throws IOException {
		System.out.println("Enter Student name : ");
		String name = bufferedReader.readLine();
		Student student = studentDao.getStudentByName(name);
		if(student == null) {
			System.out.println("Student not found !!!");
			findStudent();
		}
		exam.setStudent(student);
		
	}
	
	public Subject selectSubject() throws IOException {
		System.out.println("***Please Select Subject***");
		subjectService.displaySubject();
		System.out.println("Please enter the subject name : ");
		String name = bufferedReader.readLine();
		Subject subject = subjectDao.getSubjectByName(name);
		if(subject != null) {
			return subject;
		}
		return null;
	}
	
	public int getMarkForSubject(Subject subject) throws NumberFormatException, IOException {
		System.out.println("Enter marks for ("+subject.getName()+") : ");
		int marks = Integer.parseInt(bufferedReader.readLine());
		return marks;
	}
	
	public void DisplayExam() {
		System.out.println("The Exams are : ");
		for(int i = 0; i < Exam.getExamCount(); i++) {
			Exam exam = examDao.getExamById(i+1);
			if(exam != null) {
				System.out.println(exam);
			}
		}
		System.out.println("Average marks is " +exam.calculateAverage());
	}
	
	public void highestMarks() {
		int hightest = -1;
		String name = "";
		for(int i = 0; i < Exam.getExamCount();i++) {
			Exam exam = examDao.getExamById(i+1);
			if(exam.getTotalMarks()> hightest) {
				hightest = exam.getTotalMarks();
				 name = exam.getStudent().getName();
			}
		}
		System.out.println("Student "+name+" get the highest marks "+hightest);
	}
	
	

}
