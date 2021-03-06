package com.example.resources;

import com.example.core.Course;
import com.example.core.Student;
import com.example.dao.CourseDAO;
import com.example.dao.StudentDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;

/**
 * Created by sarthak on 6/6/17.
 */

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResources {




        private final CourseDAO courseDAO;
        private final StudentDAO studentDAO;


    public CourseResources(CourseDAO courseDAO,StudentDAO studentDAO) {
            this.courseDAO = courseDAO;
            this.studentDAO = studentDAO;
        }


        @POST
        @UnitOfWork
        public int createCourse(Course course) {
            return courseDAO.create(course);
        }

    @Path("/{id}")
    @GET
    @UnitOfWork
    public Course getCourseById(@PathParam("id") int id) {

        Optional<Course> courseOptional= courseDAO.findById(id);
        if(courseOptional.isPresent())
        {
            Course course = courseOptional.get();
            return course;
        }

        throw new WebApplicationException("Course not found", Response.Status.NOT_FOUND);



    }

    @Path("/addstudent")
    @POST
    @UnitOfWork
    public int addCourseToStudent(Requests requests)
    {
        int studentId = requests.getStudentId();
        int courseId = requests.getCourseId();
//        System.out.println(studentId);
//        System.out.println(courseId);
        Optional<Student> studentOptional = studentDAO.findById(studentId);
//        System.out.println(studentOptional);
        Optional<Course> courseOptional = courseDAO.findById((courseId));
        if(studentOptional.isPresent() && courseOptional.isPresent())
        {
            Course course = courseOptional.get();
            Student student = studentOptional.get();
            return courseDAO.addStudent(student,course);
        }

        throw new WebApplicationException("Course/Student not found", Response.Status.NOT_FOUND);
    }

    @Path("/students/{id}")
    @GET
    @UnitOfWork
    public Set<Student> showStudents(@PathParam("id") int id)
    {
        Optional<Course> courseOptional= courseDAO.findById(id);
//        System.out.println(courseOptional);
        if(courseOptional.isPresent())
        {
            Course course = courseOptional.get();
            return course.getStudents();
        }

        throw new WebApplicationException("Course not found", Response.Status.NOT_FOUND);

    }

//    @Path("/updatecourse")
//    @PUT
//    @UnitOfWork
//    public int removeStudentFromCourse(Requests requests)
//    {
//        int courseId = requests.getCourseId();
//        int studentId = requests.getStudentId();
//        Optional<Course> courseOptional= courseDAO.findById(courseId);
//        Optional<Student> studentOptional = studentDAO.findById(studentId);
//        if(studentOptional.isPresent() && courseOptional.isPresent())
//        {
//            Course course = courseOptional.get();
//            Student student = studentOptional.get();
//            return courseDAO.removeStudent(student,course);
//        }
//
//        throw new WebApplicationException("Course/Student not found", Response.Status.NOT_FOUND);
//
//
//    }



//    @Path("/changecoursename")
    @PUT
    @UnitOfWork
    public int changeNameOfCourse(ChangeName changeName)
    {
        int courseId = changeName.getCourseId();
        String newName = changeName.getCourseName();
//        System.out.println(courseId);
        System.out.println(newName);
        Optional<Course> courseOptional = courseDAO.findById(courseId);
        if(courseOptional.isPresent())
        {
            Course course = courseOptional.get();
            return courseDAO.changeCourseName(course,newName);
        }

        throw new WebApplicationException("Course not found", Response.Status.NOT_FOUND);

    }


    @DELETE
    @UnitOfWork
    public void deleteCourseFromCatalog(Requests request)
    {

        int courseId = request.getCourseId();
//        System.out.println(courseId);
        Optional<Course> courseOptional = courseDAO.findById(courseId);
//        System.out.println(courseOptional);
        if(courseOptional.isPresent())
        {

            Course course = courseOptional.get();
            courseDAO.deleteCourse(course);
            return;
        }

        throw new WebApplicationException("Course not found", Response.Status.NOT_FOUND);


    }



    }


