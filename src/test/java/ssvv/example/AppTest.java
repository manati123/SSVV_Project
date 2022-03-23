package ssvv.example;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void AddStudentInvalidGroupNumber(){
        /*Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);*/

        assert(true);
    }

    @Test
    public void addStudent_studentSaved_TestWorks(){
        StudentValidator validator = new StudentValidator();
        StudentXMLRepository studentRepo = new StudentXMLRepository(validator,"src/main/resources/studenti.xml");
        Service studentService = new Service(studentRepo,null,null);
        Student s = new Student("0","Gigi Becali",936);
        studentRepo.save(s);
        Student found = studentRepo.findOne("0");
        assert(found.getNume().equals(s.getNume()));
    }

    @Test
    public void removeStudent_studentRemoved_TestWorks(){
        StudentValidator validator = new StudentValidator();
        StudentXMLRepository studentRepo = new StudentXMLRepository(validator,"src/main/resources/studenti.xml");
        studentRepo.delete("0");
        assertNull(studentRepo.findOne("0"));
    }


}
