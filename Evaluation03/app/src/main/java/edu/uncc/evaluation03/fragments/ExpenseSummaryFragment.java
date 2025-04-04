package edu.uncc.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentAddExpenseBinding;
import edu.uncc.evaluation03.databinding.FragmentExpenseSummaryBinding;
import edu.uncc.evaluation03.models.Expense;


public class ExpenseSummaryFragment extends Fragment {


    private static final String ARG_PARAM_Expense = "Expense";
    private static final String ARG_PARAM_TOTAL_BUDGET = "total_budget";

    Expense expense;
    ExpenseSummaryListener mListener;
    double totalBudget;

    public ExpenseSummaryFragment() {
        // Required empty public constructor
    }
    public static ExpenseSummaryFragment newInstance(Expense expense, double totalBudget) {
        ExpenseSummaryFragment fragment = new ExpenseSummaryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_Expense, expense);
        args.putDouble(ARG_PARAM_TOTAL_BUDGET,totalBudget);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expense = (Expense)getArguments().getSerializable(ARG_PARAM_Expense);
            totalBudget = getArguments().getDouble(ARG_PARAM_TOTAL_BUDGET);
        }
    }

    FragmentExpenseSummaryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentExpenseSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewNameAndAmount.setText(expense.getName()+"($"+expense.getAmount()+")");
        binding.textViewCategoryAndTotal.setText(expense.getCategory()+" (Total $"+totalBudget+")");
        binding.textViewPriorityName.setText(expense.getPriority().getName());
        binding.textViewPriorityDescription.setText(expense.getPriority().getDescription());

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeExpenseSummary();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ExpenseSummaryListener) {
            mListener = (ExpenseSummaryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillSummaryListener");
        }
    }

    public interface ExpenseSummaryListener {
        void closeExpenseSummary();
    }
}