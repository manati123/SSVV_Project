package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.rules.TemporaryFolder;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    Service service;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @TempDir
    public Path tempDir;

    @Before
    public void setUpTest(){
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        Path path1, path2, path3;
        File a = new File("testfile1.xml");
        File b = new File("testfile2.xml");
        File c = new File("testfile3.xml");
        String empty = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <Entitati></Entitati>";
        try {
            a.createNewFile();
            b.createNewFile();
            c.createNewFile();
            FileWriter fwa = new FileWriter(a);
            fwa.write(empty);
            FileWriter fwb = new FileWriter(b);
            fwb.write(empty);
            FileWriter fwc = new FileWriter(c);
            fwc.write(empty);
            fwa.close();fwb.close();fwc.close();
        } catch (IOException ioe){
            System.out.println("exception");
            return;
        }
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "testfile1.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "testfile2.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "testfile3.xml");

        this.service = new Service(fileRepository1, fileRepository2, fileRepository3);
        System.out.println("ok");
    }

    @After
    public void tearDown(){
        File a = new File("testfile1.xml");
        File b = new File("testfile2.xml");
        File c = new File("testfile3.xml");
        a.delete();b.delete();c.delete();
    }

    private long getStudentCount(){
        Iterable<Student> students = service.findAllStudents();
        return StreamSupport.stream(students.spliterator(), false).count();
    }

    @Test
    public void addStudent_invalidGroupNumber(){
        assertEquals(1, service.saveStudent("1", "aa", 938));
        assertEquals(1, service.saveStudent("1", "aa", 110));
        long studentCount = getStudentCount();
        assertEquals(0, studentCount);
    }

    @Test
    public void addStudent_invalidName(){
        assertEquals(1, service.saveStudent("1", null, 937));
        assertEquals(1, service.saveStudent("1", "", 118));
    }

    @Test
    public void addStudent_invalidId(){
        assertEquals(1, service.saveStudent("", "john", 937));
        assertEquals(1, service.saveStudent(null, "john", 118));
    }

    @Test
    public void addInvalidFields(){
        assertEquals(1, service.saveStudent("", "john", 940));
        assertEquals(1, service.saveStudent("okid", "", 940));
        assertEquals(1, service.saveStudent("", null, 936));
    }

    @Test
    public void addStudentValid(){
        assertEquals(0, service.saveStudent("1", "a", 937));
        assertEquals(0, service.saveStudent("12345", "john", 111));
        assertEquals(2, getStudentCount());
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
