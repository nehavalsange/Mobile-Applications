package edu.uncc.evaluation03.fragments;

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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentBudgetListBinding;
import edu.uncc.evaluation03.models.Expense;

public class BudgetListFragment extends Fragment {
    public BudgetListFragment() {
        // Required empty public constructor
    }

    FragmentBudgetListBinding binding;

    ArrayList<Expense> mExpense = new ArrayList<>();
    BudgetRecylerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBudgetListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExpense = mListener.getAllExpenses();
        binding.textViewBudgetTotal.setText("Total Budget : "+String.valueOf(getTotalBudget(mExpense)));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        adapter = new BudgetRecylerViewAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddExpense();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpense.remove(mExpense);
                adapter.notifyDataSetChanged();
                binding.textViewBudgetTotal.setText("Total Budget : $0.00");
                mListener.clearAllExpenses();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpense.sort((o1, o2) -> Double.compare(o1.getAmount(), o2.getAmount()));
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpense.sort((o1, o2) -> Double.compare(o2.getAmount(), o1.getAmount()));
                adapter.notifyDataSetChanged();
            }
        });
    }


    BudgetListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BudgetListListener) {
            mListener = (BudgetListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BudgetListListener");
        }
    }

    public interface BudgetListListener {
        void gotoAddExpense();
        void deleteExpense(Expense expense);
        void clearAllExpenses();
        ArrayList<Expense> getAllExpenses();
        void gotoExpenseSummary(Expense expense);
    }

    public double getTotalBudget(ArrayList<Expense> expenses){
        ArrayList<Expense> expenses1 = expenses;
        double totalBud = 0;
        for(int i=0; i<expenses1.size();i++) {
            totalBud += expenses1.get(i).getAmount();
        }
        return totalBud;
    }

    class BudgetRecylerViewAdapter extends RecyclerView.Adapter<BudgetRecylerViewAdapter.BudgetViewHolder>{


        @NonNull
        @Override
        public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.list_item_expense,parent,false);
            return new BudgetViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
            Expense expense = mExpense.get(position);
            holder.setupUI(expense);
        }

        @Override
        public int getItemCount() {
            return mExpense.size();
        }

        class BudgetViewHolder extends RecyclerView.ViewHolder{
            TextView name,category, amount,priority;
            Expense expenseAdapter;

            public BudgetViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.textViewName);
                category = itemView.findViewById(R.id.textViewCategory);
                amount = itemView.findViewById(R.id.textViewAmount);
                priority = itemView.findViewById(R.id.textViewPriority);
                //  rootview = itemView;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoExpenseSummary(expenseAdapter);
                    }
                });

                itemView.findViewById(R.id.imageViewDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mExpense.remove(expenseAdapter);
                        notifyDataSetChanged();
                        binding.textViewBudgetTotal.setText("Total Budget : "+String.valueOf(getTotalBudget(mExpense)));
                        mListener.deleteExpense(expenseAdapter);

                    }
                });



            }

            public void setupUI(Expense expense){
                expenseAdapter = expense;
                name.setText(expense.getName());
                category.setText(expense.getCategory());
                amount.setText(String.valueOf(expense.getAmount()));
                priority.setText(expense.getPriority().getName());

            }

        }
    }

}