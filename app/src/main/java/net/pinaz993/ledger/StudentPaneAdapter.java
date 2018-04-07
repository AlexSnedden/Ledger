package net.pinaz993.ledger;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import org.joda.time.DateTime;

import java.time.LocalDate;

/**StudentPaneView is a view that displays information about and presents interactivity for an
 * instance of the Student class. It contains the name of the student, a switch for present/absent,
 * and a swipe-left function for revealing additional options pertaining to attendance. Later, it
 * will also contain a long-press functionality for additional interactivity with the Student
 * instance.
 *
 * Naturally, when this is constructed, it will need to have access to a single student object.
 * Other than that, it shouldn't need anything other than the context. The color of the main pane
 * will be determined by the conditional formatting from the student object.
 *
 * StudentPaneAdapter is a view that manages multiple student pane views.
 * Created by Patrick Shannon on 2/5/2018.
 */

public class StudentPaneAdapter extends ArrayAdapter {
    private String studentClassID;
    private final LayoutInflater inflater;
    private final ViewBinderHelper binderHelper;

    StudentPaneAdapter(@NonNull Context context, @NonNull Object[] objects, String studentClassID) {
        super(context, R.layout.student_pane_template, objects);
        inflater = LayoutInflater.from(getContext());
        binderHelper = new ViewBinderHelper();
        binderHelper.setOpenOnlyOne(true);
        this.studentClassID = studentClassID;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        final Student student = (Student) getItem(position);

        convertView = inflater.inflate(R.layout.student_pane_template, parent, false);
        holder = new ViewHolder();
        holder.bottomLayout = convertView.findViewById(R.id.bottomLayout);
        holder.excusedBtn = convertView.findViewById(R.id.excusedBtn);
        holder.earlyDepartureBtn = convertView.findViewById(R.id.earlyDepartureBtn);
        holder.lateArrivalBtn = convertView.findViewById(R.id.lateArrivalBtn);

        holder.topLayout = convertView.findViewById(R.id.topLayout);
        holder.studentNameTxt = convertView.findViewById(R.id.studentNameTxt);
        holder.absentPresentSwitch = convertView.findViewById(R.id.absentPresentSwitch);

        DateTime dateTime = new DateTime();
        String currentDate = Integer.toString(dateTime.getYear()) + "-" +
                Integer.toString(dateTime.getMonthOfYear()) + "-" +
                Integer.toString(dateTime.getDayOfMonth());
        Attendance existingRecord = LedgerDatabase.getDb().getAttendanceDao().getRecord(student.getId(),
                                                                                        studentClassID,
                                                                                        currentDate);

        // update button states to match today's records.
        if(existingRecord != null) {
            holder.absentPresentSwitch.setChecked(existingRecord.present);
            if(existingRecord.earlyDeparture) {
                holder.earlyDepartureBtn.setChecked(existingRecord.earlyDeparture);
                holder.earlyDepartureBtn.setTextColor(Color.BLUE);
            }
            if(existingRecord.lateArrival) {
                holder.lateArrivalBtn.setChecked(existingRecord.lateArrival);
                holder.lateArrivalBtn.setTextColor(Color.BLUE);
            }
            if(existingRecord.excused) {
                holder.excusedBtn.setChecked(existingRecord.excused);
                holder.excusedBtn.setTextColor(Color.BLUE);
            }
        } else {
            Attendance attendance = new Attendance();
            attendance.setDate(currentDate);
            attendance.setClassId(studentClassID);
            attendance.setStudentId(student.getId());
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendance);
        }

        holder.swipe = convertView.findViewById(R.id.swipe);
        holder.studentNameTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DialogFragment diagFrag = new BehaviorDialog();
                Bundle diagFragArgs = new Bundle();
                diagFragArgs.putString("studentId", student.getId());
                diagFragArgs.putString("classId", studentClassID);
                diagFrag.setArguments(diagFragArgs);
                diagFrag.show(((Activity)getContext()).getFragmentManager(), "Behaviors");
                return false;
            }
        });
        convertView.setTag(holder);

        if (student != null) {
            binderHelper.bind(holder.swipe, student.id);
            //Set click handlers
                    holder.excusedBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(holder.excusedBtn.isChecked()) {
                                compoundButton.setTextColor(Color.BLUE);
                                toggleExcused(true, student);
                            } else {
                                compoundButton.setTextColor(Color.BLACK);
                                toggleExcused(false, student);
                            }
                        }
                    });

            holder.earlyDepartureBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(holder.earlyDepartureBtn.isChecked()) {
                        compoundButton.setTextColor(Color.BLUE);
                        toggleEarlyDeparture(true, student);
                    } else {
                        compoundButton.setTextColor(Color.BLACK);
                        toggleEarlyDeparture(false, student);
                    }
                }
            });

            holder.lateArrivalBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(holder.lateArrivalBtn.isChecked()) {
                        compoundButton.setTextColor(Color.BLUE);
                        toggleLateArrival(true, student);
                    } else {
                        compoundButton.setTextColor(Color.BLACK);
                        toggleLateArrival(false, student);
                    }
                }
            });

            holder.absentPresentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    toggleAbsentPresent(holder.absentPresentSwitch.isChecked(), student);
                }
            });

            holder.studentNameTxt.setText(student.getFullName());
        }
        return convertView;
    }

    private void toggleAbsentPresent(boolean isChecked, Student student) {
        DateTime dateTime = new DateTime();
        String currentDate = Integer.toString(dateTime.getYear()) + "-" +
                             Integer.toString(dateTime.getMonthOfYear()) + "-" +
                             Integer.toString(dateTime.getDayOfMonth());
        Attendance attendanceRecord = new Attendance();
        attendanceRecord.setStudentId(student.getId());
        attendanceRecord.setClassId(studentClassID);
        attendanceRecord.setDate(currentDate);
        Attendance existingRecord = LedgerDatabase.getDb().getAttendanceDao().getRecord(student.getId(),
                                                                        studentClassID, currentDate);
        if(existingRecord != null) {
            attendanceRecord.setLateArrival(existingRecord.lateArrival);
            attendanceRecord.setEarlyDeparture(existingRecord.earlyDeparture);
            attendanceRecord.setExcused(existingRecord.excused);
        }
        if(isChecked) {
            // student is present
            attendanceRecord.setPresent(true);
            if(existingRecord != null) {
                //LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                LedgerDatabase.getDb().getAttendanceDao().deleteAttendance(existingRecord);
                LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);

                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        } else {
            // student is absent
            attendanceRecord.setPresent(false);
            if(existingRecord != null) {
                //LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                LedgerDatabase.getDb().getAttendanceDao().deleteAttendance(existingRecord);
                LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        }
    }

    private void toggleLateArrival(boolean isChecked, Student student) {
        DateTime dateTime = new DateTime();
        String currentDate = Integer.toString(dateTime.getYear()) + "-" +
                Integer.toString(dateTime.getMonthOfYear()) + "-" +
                Integer.toString(dateTime.getDayOfMonth());
        Attendance attendanceRecord = new Attendance();
        attendanceRecord.setStudentId(student.getId());
        attendanceRecord.setClassId(studentClassID);
        attendanceRecord.setDate(currentDate);
        Attendance existingRecord = LedgerDatabase.getDb().getAttendanceDao().getRecord(student.getId(),
                studentClassID, currentDate);
        if(existingRecord != null) {
            attendanceRecord.setPresent(existingRecord.present);
            attendanceRecord.setEarlyDeparture(existingRecord.earlyDeparture);
            attendanceRecord.setExcused(existingRecord.excused);
        }
        if(isChecked) {
            // student arrived late
            attendanceRecord.setLateArrival(true);
            if(existingRecord != null) {
                LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        } else {
            // student arrived on time
            attendanceRecord.setLateArrival(false);
            if(existingRecord != null) {
                LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        }
    }

    private void toggleExcused(boolean isChecked, Student student) {
        DateTime dateTime = new DateTime();
        String currentDate = Integer.toString(dateTime.getYear()) + "-" +
                Integer.toString(dateTime.getMonthOfYear()) + "-" +
                Integer.toString(dateTime.getDayOfMonth());
        Attendance attendanceRecord = new Attendance();
        attendanceRecord.setStudentId(student.getId());
        attendanceRecord.setClassId(studentClassID);
        attendanceRecord.setDate(currentDate);
        Attendance existingRecord = LedgerDatabase.getDb().getAttendanceDao().getRecord(student.getId(),
                studentClassID, currentDate);
        if(existingRecord != null) {
            attendanceRecord.setLateArrival(existingRecord.lateArrival);
            attendanceRecord.setEarlyDeparture(existingRecord.earlyDeparture);
            attendanceRecord.setPresent(existingRecord.present);
        }
        if(isChecked) {
            // student is excused
            attendanceRecord.setExcused(true);
            if(existingRecord != null) {
                LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        } else {
            // student is not excused
            attendanceRecord.setExcused(false);
            if(existingRecord != null) {
                LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        }
    }

    private void toggleEarlyDeparture(boolean isChecked, Student student) {
        DateTime dateTime = new DateTime();
        String currentDate = Integer.toString(dateTime.getYear()) + "-" +
                Integer.toString(dateTime.getMonthOfYear()) + "-" +
                Integer.toString(dateTime.getDayOfMonth());
        Attendance attendanceRecord = new Attendance();
        attendanceRecord.setStudentId(student.getId());
        attendanceRecord.setClassId(studentClassID);
        attendanceRecord.setDate(currentDate);
        Attendance existingRecord = LedgerDatabase.getDb().getAttendanceDao().getRecord(student.getId(),
                studentClassID, currentDate);
        if(existingRecord != null) {
            attendanceRecord.setLateArrival(existingRecord.lateArrival);
            attendanceRecord.setPresent(existingRecord.present);
            attendanceRecord.setExcused(existingRecord.excused);
        }
        if(isChecked) {
            // student departed early
            attendanceRecord.setEarlyDeparture(true);
            if(existingRecord != null) {
                LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        } else {
            // student did not depart early
            attendanceRecord.setEarlyDeparture(false);
            if(existingRecord != null) {
                LedgerDatabase.getDb().getAttendanceDao().updateAttendance(attendanceRecord);
                return;
            }
            LedgerDatabase.getDb().getAttendanceDao().recordAttendance(attendanceRecord);
        }
    }

    private class ViewHolder {
        LinearLayout bottomLayout;
        ToggleButton excusedBtn,
                lateArrivalBtn,
                earlyDepartureBtn;
        LinearLayout topLayout;
        TextView studentNameTxt;
        Switch absentPresentSwitch;

        SwipeRevealLayout swipe;
    }
}
