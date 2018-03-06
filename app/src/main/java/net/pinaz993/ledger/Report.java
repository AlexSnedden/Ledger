package net.pinaz993.ledger;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Patrick Shannon on 3/6/2018.
 * Used to create reports on the
 */

public class Report {
    public class StudentReport {
        public final String id;
//        public final int totalAbsent, totalPresent, totalLate, totalEarly,
//                totalUnexcusedAbsent, totalUnexcusedLate, totalUnexcusedEarly;

        public StudentReport(String studentId) {
            //TODO: Implement StudentReport constuctor such that it uses AttendanceDao to fill in the above final values
            this.id = studentId;

        }

        public String toCsv(){
            //TODO: Implement toCsv such that it exports the above values in a single-line CSV format for placing into a CSV file
            return null;
        }
    }

    public class ClassReport {
        public final String id;
        public ArrayList<StudentReport> students;

        public ClassReport(String classId) {
            //TODO: Implement ClassReport constructor such that it fills in the above ArrayList with the reports of all students in that class
            this.id = classId;
            students = new ArrayList<StudentReport>();
        }

        public String toCsv(){
            //TODO Implement toCsv such that it exports the results of toCsv for all its students into a single string with carriage returns between entries
            return null;
        }

        public File toCsvFile() {
            //TODO: Implement toCsvFile such that it packages the results of toCSV in a .csv file to be emailed.
            return null;
        }
    }
}
