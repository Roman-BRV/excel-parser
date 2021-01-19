package ua.pp.helperzit.excelparser.ui.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import ua.pp.helperzit.excelparser.ExcelParser;
import ua.pp.helperzit.excelparser.service.FileFinder;
import ua.pp.helperzit.excelparser.ui.UIException;

public class ChatBot {
    
    private static final String FIND_LOW_GROUPS_COMMAND = "1";
    private static final String FIND_STUDENTS_IN_COURSE_COMMAND = "2";
    private static final String ADD_NEW_STUDENT_COMMAND = "3";
    private static final String DELETE_STUDENT_BY_ID_COMMAND = "4";
    private static final String ADD_STUDENT_TO_COURSE_COMMAND = "5";
    private static final String REMOVE_STUDENT_FROM_COURSE_COMMAND = "6";
    private static final String EXIT_COMMAND = "1111";
    
    private static final int EXIT_COMMAND_NUMBER = 1111;
    
    private ExcelParser excelParser = new ExcelParser();
    private FileFinder fileFinder = new FileFinder();
    
    public void fileConversation() throws UIException {
        
        String answer;
        try (Scanner input = new Scanner(System.in);) {
            do {
                answer = input.nextLine();
                if (fileFinder.checkDirPath(answer)) {
                    System.out.println("DIR");
                    List<String> fileNames = fileFinder.getFileNames(answer);
                    for (String fileName : fileNames) {
                        System.out.println(fileName);
                    }
                    //printLowOccupancyGroups(input);

                } else if (fileFinder.checkFilePath(answer)) {
                    System.out.println("FILE");
                    //printStudentsByCourseName(input);

                } else if(answer.equals(EXIT_COMMAND)) {
                    System.out.println("Goodbay! Have a nice day!");
                    
                } else {
                    System.out.println("Please enter correct path.");
                    
                }
            } while (!answer.equals(EXIT_COMMAND));
        } catch (Exception e) {
            throw new UIException("Problems in UI layer");
        }
        
    }
    
    public void startConversation() throws UIException {

        System.out.println("Hello I`m chatbot Marichka. Welcome at office JDBC school!");
        printProposedCommands();
        
        String answer;
        try (Scanner input = new Scanner(System.in);) {
            do {
                answer = input.nextLine();
                if (answer.equals(FIND_LOW_GROUPS_COMMAND)) {
                    //printLowOccupancyGroups(input);

                } else if (answer.equals(FIND_STUDENTS_IN_COURSE_COMMAND)) {
                    //printStudentsByCourseName(input);

                } else if (answer.equals(ADD_NEW_STUDENT_COMMAND)) {
                    //addNewStudent(input);

                } else if (answer.equals(DELETE_STUDENT_BY_ID_COMMAND)) {
                    //deleteStudentById(input);

                } else if (answer.equals(ADD_STUDENT_TO_COURSE_COMMAND)) {
                    //addStudentToCourse(input);

                } else if (answer.equals(REMOVE_STUDENT_FROM_COURSE_COMMAND)) {
                    //removeStudentFromCourse(input);

                } else if(answer.equals(EXIT_COMMAND)) {
                    System.out.println("Goodbay! Have a nice day!");
                    
                } else {
                    System.out.println("Please enter only proposed answer.");
                    
                }
            } while (!answer.equals(EXIT_COMMAND));
        } catch (Exception e) {
            throw new UIException("Problems in UI layer");
        }
    }
    
//    private void printLowOccupancyGroups(Scanner input) {
//        
//        System.out.println("Enter number of groups low occupancy lewel:");
//
//        int lowOccupancyLevel = askForNumber(input);
//        Collection<Group> lowGroups = schoolManager.getLowOccupancyGroups(lowOccupancyLevel);
//        if(lowOccupancyLevel != EXIT_COMMAND_NUMBER) {
//            System.out.println("1. Find all groups with less or equals " + lowOccupancyLevel + " studens.");
//            
//            if(lowGroups.isEmpty()) {
//                System.out.println("There are no any group with less or equals " + lowOccupancyLevel + " studens.");
//                
//            } else {
//                int numeration = 1;
//                for (Group group : lowGroups) {
//                    System.out.print(numeration + ". ");
//                    System.out.println(group.getName());
//                    numeration++;
//                }
//            }   
//        }
//        printProposedCommands(); 
//    }
//    
//    private void printStudentsByCourseName(Scanner input) throws DataProcessingException {
//
//        System.out.println("2. Find all students related to course.");
//        System.out.println("We have such courses:");
//
//        Collection<Course> courses = schoolManager.getAllCourses();
//        Collection<String> permittedAnswers = new ArrayList<>();
//        if(courses.isEmpty()) {
//            System.out.println("There are no any course.");
//        } else {
//            int numeration = 1;
//            for (Course course : courses) {
//                permittedAnswers.add(course.getName());
//                System.out.print(numeration + ". ");
//                System.out.println(course.getName());
//                numeration++;
//            }
//        }
//        System.out.println("Choose course and enter its name:");
//
//        String courseName = askForPermittedAnswer (input, permittedAnswers);
//        if(!courseName.equals(EXIT_COMMAND)) {
//            System.out.println("This students related to course - " + courseName);
//            Collection<Student> courseStudents = schoolManager.getStudentsByCourseName(courseName);
//            if(courseStudents.isEmpty()) {
//                System.out.println("There are no any student related to course " + courseName + ".");
//            } else {
//                int numeration = 1;
//                for (Student student : courseStudents) {
//                    System.out.print(numeration + ". ");
//                    System.out.println(student.getFirstName() + " " + student.getLastName());
//                    numeration++;
//                }
//            }
//        }
//        printProposedCommands();
//    }
//    
//    private void addNewStudent(Scanner input) throws DataProcessingException {
//
//        System.out.println("3. Add new student.");
//        System.out.println("Enter first name of new student:");
//        String firstName = input.nextLine();
//        System.out.println("Enter last name of new student:");
//        String lastName = input.nextLine();
//        System.out.println("You entered name of new student - " + firstName + " " + lastName);
//        System.out.println("If it's rigth enter any numer, if not - enter 0.");
//        int notZeroAccept = askForNumber(input);
//
//        if(notZeroAccept != EXIT_COMMAND_NUMBER) {
//            schoolManager.addNewStudent(firstName, lastName);
//            System.out.println("New student " + firstName + " " + lastName + " successfully added.");
//        }
//        printProposedCommands();
//    }
//    
//    private void deleteStudentById(Scanner input) throws DataProcessingException {
//        
//        System.out.println("4. Delete student from the list.");
//        
//        Collection<Student> students = schoolManager.getAllStudents();
//        Collection<String> permittedAnswers = new ArrayList<>();
//        if(students.isEmpty()) {
//            System.out.println("There are no any student.");
//        } else {
//            for (Student student : students) {
//                permittedAnswers.add(String.valueOf(student.getId()));
//                System.out.print("ID - " + student.getId() + ". Name - ");
//                System.out.println(student.getFirstName() + " " + student.getLastName());
//            }
//        }
//        System.out.println("Enter the student ID:");
//
//        String studentID = askForPermittedAnswer (input, permittedAnswers);
//        if(!studentID.equals(EXIT_COMMAND)) {
//            int studentId = Integer.parseInt(studentID);
//            schoolManager.deleteStudentById(studentId);
//            System.out.println("Student with STUDENT_ID = " + studentID + " successfully deleted.");
//        }
//        printProposedCommands();
//    }
//    
//    private void addStudentToCourse(Scanner input) throws DataProcessingException {
//
//        System.out.println("5. Add a student to the course (from a list)");
//        
//        Student student = askForStudentFromList(input, schoolManager);
//        Collection<Course> allcourses = schoolManager.getAllCourses();
//        Collection<Integer> assignedCourseIds = student.getCourseIds();
//        
//        if(assignedCourseIds.isEmpty()) {
//            System.out.println("This student hasn't assigned to any course.");
//        } else {
//            System.out.println("This student has been already assigned to this courses with ID:");
//            for (Integer courseId : assignedCourseIds) {
//                String courseName = "";
//                for (Course course : allcourses) {
//                    if(courseId == course.getId()) {
//                        courseName = course.getName();
//                    }
//                }
//                System.out.print("ID - " + courseId + ". Name - ");
//                System.out.println(courseName);
//            }
//        }
//        System.out.println();
//        System.out.println("We have such courses:");
//        
//        Collection<String> permittedAnswersCourseID = new ArrayList<>();
//        if(allcourses.isEmpty()) {
//            System.out.println("There are no any course.");
//        } else {
//            for (Course course : allcourses) {
//                permittedAnswersCourseID.add(String.valueOf(course.getId()));
//                System.out.print("ID - " + course.getId() + ". Name - ");
//                System.out.println(course.getName());
//            }
//            if (!assignedCourseIds.isEmpty()) {
//                for (Integer id : assignedCourseIds) {
//                    if(permittedAnswersCourseID.contains(String.valueOf(id))) {
//                        permittedAnswersCourseID.remove(String.valueOf(id));
//                    }
//                }
//            }
//        }
//        System.out.println("Choose course ID to relate this student:");
//
//        String courseID = askForPermittedAnswer (input, permittedAnswersCourseID);
//        int studentId = student.getId();
//        
//        if(!courseID.equals(EXIT_COMMAND)) {
//            schoolManager.assignStudentToCourse(studentId, Integer.parseInt(courseID));
//            System.out.println("Student with ID " + student.getId() + " successfully related to course with ID - " + courseID);
//        }
//        printProposedCommands();
//    }
//    
//    private void removeStudentFromCourse(Scanner input) throws DataProcessingException {
//
//        System.out.println("6. Remove the student from one of his or her courses");
//
//        Student student = askForStudentFromList(input, schoolManager);
//        Collection<Course> allcourses = schoolManager.getAllCourses();
//        Collection<Integer> assignedCourseIds = student.getCourseIds();
//        
//        if(assignedCourseIds.isEmpty()) {
//            System.out.println("This student hasn't assigned to any course.");
//        } else {
//            System.out.println("This student has been assigned to this courses with ID:");
//            for (Integer courseId : assignedCourseIds) {
//                String courseName = "";
//                for (Course course : allcourses) {
//                    if(courseId == course.getId()) {
//                        courseName = course.getName();
//                    }
//                }
//                System.out.print("ID - " + courseId + ". Name - ");
//                System.out.println(courseName);
//            }
//        }
//
//        Collection<String> permittedAnswersCourseID = new ArrayList<>();
//        if (!assignedCourseIds.isEmpty()) {
//            for (Integer id : assignedCourseIds) {
//                permittedAnswersCourseID.add(String.valueOf(id));
//            }
//        }
//
//        System.out.println("Choose course ID to remove this student from:");
//
//        String courseID = askForPermittedAnswer (input, permittedAnswersCourseID);
//        int studentId = student.getId();
//
//        if(!courseID.equals(EXIT_COMMAND)) {
//            schoolManager.removeStudentFromCourse(studentId, Integer.parseInt(courseID));
//            System.out.println("Student with ID " + student.getId() + " successfully removed from course with ID - " + courseID);
//        }
//        printProposedCommands();
//    }
//    
//    private Student askForStudentFromList(Scanner input, SchoolManager schoolManager) throws DataProcessingException {
//        
//        Collection<Student> students = schoolManager.getAllStudents();
//        Collection<String> permittedAnswersStudID = new ArrayList<>();
//        if(students.isEmpty()) {
//            System.out.println("There are no any student.");
//        } else {
//            for (Student student : students) {
//                permittedAnswersStudID.add(String.valueOf(student.getId()));
//                System.out.print("ID - " + student.getId() + ". Name - ");
//                System.out.println(student.getFirstName() + " " + student.getLastName());
//            }
//        }
//        System.out.println("Enter the student ID:");
//
//        String studentID = null;
//        do {
//            studentID = askForPermittedAnswer (input, permittedAnswersStudID);
//            if (studentID.contentEquals(EXIT_COMMAND)) {
//                System.out.println("Sorry, you don't exit to uper level of conversation.");
//                System.out.println("There are no student with id = 0, choose another student.");
//            }
//        } while (studentID.contentEquals(EXIT_COMMAND));
//        Student student = schoolManager.getStudentWithCoursesByStudentId(Integer.parseInt(studentID));
//        System.out.println("You choose student - " + student.getFirstName() + " " + student.getLastName());
//
//        return student;
//    }
    
    private int askForNumber(Scanner input) {

        boolean isEndOfQuery = false;
        String answer;
        int numberAnswer = 0;

        do {
            answer = input.nextLine();
            if (answer.chars().allMatch( Character::isDigit ) && !answer.equals(EXIT_COMMAND)) {
                numberAnswer = Integer.parseInt(answer);
                isEndOfQuery = true;

            } else if(answer.equals(EXIT_COMMAND)) {
                System.out.println("You exit to uper level of conversation.");
                isEndOfQuery = true;

            } else {
                System.out.println("You entered unexprcted answer.");
                System.out.println("Try once more.");

            }
        } while (!isEndOfQuery);
        
        return numberAnswer;

    }
    
    private String askForPermittedAnswer (Scanner input, Collection<String> permittedAnswers) {

        boolean isEndOfQuery = false;
        String answer = "";

        do {
            answer = input.nextLine();
            if (permittedAnswers.contains(answer) && !answer.equals(EXIT_COMMAND)) {
                isEndOfQuery = true;

            } else if(answer.equals(EXIT_COMMAND)) {
                System.out.println("You exit to uper level of conversation.");
                isEndOfQuery = true;

            } else {
                System.out.println("You entered unexprcted answer.");
                System.out.println("Try once more.");

            }
        } while (!isEndOfQuery);
        
        return answer;

    }
    
    private void printProposedCommands() {
        
        System.out.println();
        System.out.println("Please enter the number of activity, which do you want to do:");
        System.out.println("1. Find all groups with less or equals student count");
        System.out.println("2. Find all students related to course with given name");
        System.out.println("3. Add new student");
        System.out.println("4. Delete student by STUDENT_ID");
        System.out.println("5. Add a student to the course");
        System.out.println("6. Remove the student from one of his or her courses");
        System.out.println();
        System.out.println("0. Exit the application.");
    }

}
