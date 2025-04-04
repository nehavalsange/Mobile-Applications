package edu.uncc.evaluation04;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.ArrayList;

import edu.uncc.evaluation04.fragments.AddGradeFragment;
import edu.uncc.evaluation04.fragments.MyGradesFragment;
import edu.uncc.evaluation04.fragments.PinCodeCheckFragment;
import edu.uncc.evaluation04.fragments.PinCodeSetupFragment;
import edu.uncc.evaluation04.fragments.PinCodeUpdateFragment;
import edu.uncc.evaluation04.fragments.SelectCourseFragment;
import edu.uncc.evaluation04.fragments.SelectLetterGradeFragment;
import edu.uncc.evaluation04.fragments.SelectSemesterFragment;
import edu.uncc.evaluation04.models.AppDatabase;
import edu.uncc.evaluation04.models.Course;
import edu.uncc.evaluation04.models.Grade;
import edu.uncc.evaluation04.models.LetterGrade;
import edu.uncc.evaluation04.models.Semester;

public class MainActivity extends AppCompatActivity implements SelectSemesterFragment.SelectSemesterListener, SelectLetterGradeFragment.SelectLetterGradeListener,
        SelectCourseFragment.SelectCourseListener, AddGradeFragment.AddGradeListener, MyGradesFragment.MyGradesListener,
        PinCodeCheckFragment.PinCodeCheckListener, PinCodeUpdateFragment.PinCodeUpdateListener, PinCodeSetupFragment.PinCodeSetupListener {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //need to do the check related to the pin code

        db = Room.databaseBuilder(this, AppDatabase.class, "Grade-db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String pinCode = sharedPref.getString(getString(R.string.saved_pin_key),"Null");
        if(pinCode.equals("Null")){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new PinCodeSetupFragment())
                    .commit();
        } else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new PinCodeCheckFragment())
                    .commit();
        }


    }

    @Override
    public boolean checkPinCode(String pinCode) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String pinCodeCheck = sharedPref.getString(getString(R.string.saved_pin_key),"Null");
        if(pinCodeCheck.equals(pinCode)){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void onPinCodeCheckSuccessful() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new MyGradesFragment())
                .commit();
    }

    @Override
    public void onPinCodeSetup(String pinCode) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_pin_key), pinCode);
        editor.apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PinCodeCheckFragment())
                .commit();


    }

    @Override
    public void onPinCodeUpdate(String pinCode) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_pin_key), pinCode);
        editor.apply();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PinCodeCheckFragment())
                .commit();
    }

    @Override
    public void onPinCodeUpdateCancel() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new MyGradesFragment())
                .commit();
    }

    @Override
    public void onLetterGradeSelected(LetterGrade letterGrade) {
        AddGradeFragment addGradeFragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if (addGradeFragment != null) {
            addGradeFragment.setSelectedLetterGrade(letterGrade);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSemesterSelected(Semester semester) {
        AddGradeFragment addGradeFragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if (addGradeFragment != null) {
            addGradeFragment.setSelectedSemester(semester);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCourseSelected(Course course) {
        AddGradeFragment addGradeFragment = (AddGradeFragment) getSupportFragmentManager().findFragmentByTag("add-grade-fragment");
        if (addGradeFragment != null) {
            addGradeFragment.setSelectedCourse(course);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onAddGrade(Grade grade) {
        //store to database
        //pop the back stack.
        db.gradeDao().insertAll(grade);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelAddGrade() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectLetterGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectLetterGradeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectCourse() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectCourseFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSemester() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectSemesterFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoAddGrade() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new AddGradeFragment(), "add-grade-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoUpdatePin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new PinCodeUpdateFragment())
                .commit();
    }

    @Override
    public ArrayList<Grade> getAllGrades() {
        return new ArrayList<>(db.gradeDao().getAll());
    }

    @Override
    public void deleteGrade(Grade grade) {
        db.gradeDao().delete(grade);
    }

}