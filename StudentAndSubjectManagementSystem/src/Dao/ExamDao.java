package Dao;

import Model.Exam;

public interface ExamDao {

	void addExam(Exam exam);
	Exam getExamById(int id);
	Exam getExamByName(String name);
	Exam[] getAllExams();
	void updateExam(Exam Exam);
	void deleteExam(int id);

}
