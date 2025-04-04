package edu.uncc.evaluation04.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.evaluation04.R;
import edu.uncc.evaluation04.databinding.FragmentMyGradesBinding;
import edu.uncc.evaluation04.databinding.GradeRowItemBinding;
import edu.uncc.evaluation04.models.Course;
import edu.uncc.evaluation04.models.DataSource;
import edu.uncc.evaluation04.models.Grade;
import edu.uncc.evaluation04.models.LetterGrade;
import edu.uncc.evaluation04.models.Semester;

public class MyGradesFragment extends Fragment {
    public MyGradesFragment() {
        // Required empty public constructor
    }

    FragmentMyGradesBinding binding;
    ArrayList<Grade> mGrades = new ArrayList<>();
    ArrayList<Course> course = DataSource.getCourses();
    ArrayList<Semester> semester = DataSource.getSemesters();
    ArrayList<LetterGrade> letterGrade = DataSource.getLetterGrades();
    GradeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyGradesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Grades");

        mGrades = mListener.getAllGrades();
        adapter = new GradeAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddGrade();
            }
        });

        binding.buttonUpdatePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoUpdatePin();
            }
        });

    }

    private void calculateAndDisplayGPA(){

    }

    MyGradesListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MyGradesListener) {
            mListener = (MyGradesListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MyGradesListener");
        }
    }

    public interface MyGradesListener {
        void gotoAddGrade();
        void gotoUpdatePin();
        ArrayList<Grade> getAllGrades();
        void deleteGrade(Grade grade);
    }

    class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

        @NonNull
        @Override
        public GradeAdapter.GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GradeRowItemBinding itemBinding = GradeRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new GradeViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull GradeAdapter.GradeViewHolder holder, int position) {
            holder.setupUI(mGrades.get(position));
        }

        @Override
        public int getItemCount() {
            return mGrades.size();
        }

        public class GradeViewHolder extends RecyclerView.ViewHolder {
            GradeRowItemBinding itemBinding1;
            Grade grade;

            public GradeViewHolder(GradeRowItemBinding itemView) {
                super(itemView.getRoot());
                this.itemBinding1 = itemView;

                itemView.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.deleteGrade(grade);
                        mGrades.clear();
                        mGrades.addAll(mListener.getAllGrades());
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            public void setupUI(Grade grade) {
                this.grade = (Grade)grade;
                itemBinding1.textViewLetterGrade.setText(String.valueOf(grade.getLetterGrade()));
                itemBinding1.textViewCourseName.setText(grade.getCourse());
                itemBinding1.textViewCourseNumber.setText(grade.getCourse());
                itemBinding1.textViewCreditHours.setText(String.valueOf(grade.getHours()));
                itemBinding1.textViewSemester.setText(grade.getSemester());


            }
        }

//        public Course getCCourse() {
//            Course course1 = null;
//            return Course;
//        }

    }}
