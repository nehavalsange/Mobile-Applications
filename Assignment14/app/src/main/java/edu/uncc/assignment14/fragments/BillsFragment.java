package edu.uncc.assignment14.fragments;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.assignment14.models.Bill;
import edu.uncc.assignment14.databinding.BillRowItemBinding;
import edu.uncc.assignment14.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    public BillsFragment() {
        // Required empty public constructor
    }

    FragmentBillsBinding binding;

    private ArrayList<Bill> mBills = new ArrayList<>();
    BillsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBills.clear();
        mBills.addAll(mListener.getAllBills());
        adapter = new BillsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllBills();
                mBills.clear();
                mBills.addAll(mListener.getAllBills());
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateBill();
            }
        });
    }

    class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsViewHolder> {


        @NonNull
        @Override
        public BillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BillRowItemBinding itemBinding = BillRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new BillsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull BillsViewHolder holder, int position) {
            holder.setupUI(mBills.get(position));
        }

        @Override
        public int getItemCount() {
            return mBills.size();
        }

        class BillsViewHolder extends RecyclerView.ViewHolder {
            BillRowItemBinding itemBinding;
            Bill mBill;
            public BillsViewHolder(BillRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(Bill bill){
                mBill = bill;
                itemBinding.textViewBillName.setText(bill.getName());
                itemBinding.textViewBillAmount.setText("Bill Amount: " + String.format("$%.2f", bill.getAmount()));
                double discountAmount = bill.getAmount() * bill.getDiscount() / 100;
                itemBinding.textViewBillDiscount.setText("Bill Discount" + String.format("%.2f", bill.getDiscount()) + " (" + String.format("$%.2f", discountAmount) + ")");
                itemBinding.textViewTotalBill.setText("Total Bill: " + String.format("$%.2f", bill.getAmount() - discountAmount));
                itemBinding.textViewBillCategory.setText(bill.getCategory());
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                itemBinding.textViewBillDate.setText(sdf.format(bill.getBillDate()));
                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.goToBillSummary(mBill);
                    }
                });

                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.deleteBillFromBills(mBill);
                        mBills.clear();
                        mBills.addAll(mListener.getAllBills());
                        notifyDataSetChanged();
                    }
                });

                itemBinding.imageViewEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.goToEditBill(mBill);
                    }
                });
            }
        }
    }

    BillsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillsListener) {
            mListener = (BillsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillsListener");
        }
    }

    public interface BillsListener {
        void goToBillSummary(Bill bill);
        void goToEditBill(Bill bill);
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void clearAllBills();
        void deleteBillFromBills(Bill bill);
    }
}