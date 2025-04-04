package edu.uncc.evaluation03.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.evaluation03.R;
import edu.uncc.evaluation03.databinding.FragmentSelectPriorityBinding;
import edu.uncc.evaluation03.models.Data;
import edu.uncc.evaluation03.models.Priority;


public class SelectPriorityFragment extends Fragment {
    public SelectPriorityFragment() {
        // Required empty public constructor
    }
    ArrayList<Priority> mPriorities = Data.getPriorities();
    PriorityAdapter adapter;
    FragmentSelectPriorityBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectPriorityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });

        //mPriorities has the list of priorities
        adapter = new PriorityAdapter(getActivity(),mPriorities);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onPrioritySelected(mPriorities.get(i));
            }
        });

    }

    SelectPriorityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectPriorityListener) {
            mListener = (SelectPriorityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectPriorityListener");
        }
    }

    public interface SelectPriorityListener {
        void onPrioritySelected(Priority priority);
        void onCancelSelection();
    }


    class PriorityAdapter extends ArrayAdapter {

        public PriorityAdapter(@NonNull Context context, @NonNull List objects) {
            super(context, R.layout.list_item_priority, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item_priority,parent,false);
            }
            TextView name = convertView.findViewById(R.id.textViewPriorityName);
            TextView description = convertView.findViewById(R.id.textViewPriorityDescription);

            Priority priority = (Priority) getItem(position);


            name.setText(priority.getName());
            description.setText(priority.getDescription());
            return convertView;
        }
    }

}