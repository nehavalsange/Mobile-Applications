package edu.uncc.evaluation05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.evaluation05.databinding.FragmentPurchaseListsBinding;
import edu.uncc.evaluation05.databinding.ListItemProductlistBinding;
import edu.uncc.evaluation05.models.AuthResponse;
import edu.uncc.evaluation05.models.Product;
import edu.uncc.evaluation05.models.ProductResponse;
import edu.uncc.evaluation05.models.PurchaseList;
import edu.uncc.evaluation05.models.PurchaseListResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PurchaseListsFragment extends Fragment {

    public PurchaseListsFragment() {
        // Required empty public constructor
    }

    FragmentPurchaseListsBinding binding;
    AuthResponse mAuthResponse;
    ArrayList<PurchaseList> mPurchaseLists = new ArrayList<>();
    PurchaseListsAdapter adapter;
    private final OkHttpClient client = new OkHttpClient();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPurchaseListsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Purchase Lists");
        mAuthResponse = mListener.getAuthResponse();
        MenuHost menuHost = (MenuHost) getActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.action_logout){
                    mListener.logout();
                    return true;
                } else if(menuItem.getItemId() == R.id.action_add){
                    mListener.gotoCreatePurchaseList();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PurchaseListsAdapter();
        binding.recyclerView.setAdapter(adapter);
        getMyPurchaseLists();

    }

    private void getMyPurchaseLists(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/purchases/lists")
                .addHeader("Authorization","BEARER "+mAuthResponse.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();

                    Gson gson = new Gson();

                    PurchaseListResponse purchaseListResponse = gson.fromJson(body,PurchaseListResponse.class);
                    mPurchaseLists.clear();
                    mPurchaseLists.addAll(purchaseListResponse.getPurchase_lists());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            // binding.textViewPaging.setText("Showing page "+currentPage+" out of "+maxPage);
                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Unable to load posts",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }

    private void deletePurchaseList(PurchaseList purchaseList){
        RequestBody requestBody = new FormBody.Builder()
                .add("plid",purchaseList.getPlid())
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/purchases/delete")
                .addHeader("Authorization","BEARER " + mAuthResponse.getToken())
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String body = response.body().string();
                    getMyPurchaseLists();
                }else{
                    Log.d("demo","Response: "+response);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Unable to submit purchase list",Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }


    class PurchaseListsAdapter extends RecyclerView.Adapter<PurchaseListsAdapter.PurchaseListViewHolder>{

        @NonNull
        @Override
        public PurchaseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemProductlistBinding rowItemBinding = ListItemProductlistBinding.inflate(getLayoutInflater(), parent, false);
            return new PurchaseListViewHolder(rowItemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull PurchaseListViewHolder holder, int position) {
            holder.bind(mPurchaseLists.get(position));
        }

        @Override
        public int getItemCount() {
            return mPurchaseLists.size();
        }

        class PurchaseListViewHolder extends RecyclerView.ViewHolder{
            ListItemProductlistBinding rowItemBinding;
            PurchaseList purchaseList;
            public PurchaseListViewHolder(ListItemProductlistBinding rowItemBinding) {
                super(rowItemBinding.getRoot());
                this.rowItemBinding = rowItemBinding;

            }

            public void bind(PurchaseList purchaseList){
                this.purchaseList = purchaseList;
                rowItemBinding.textViewName.setText(purchaseList.getName());
                int totalQty =0;
                double totalCost = 0.0;
                for(Product product: purchaseList.getItems()){
                    totalQty = totalQty + product.getQuantity();
                    totalCost = totalCost + (product.getQuantity() * product.getPrice_per_item());
                }
                rowItemBinding.textViewTotalItems.setText("Total Items : "+String.valueOf(totalQty));
                rowItemBinding.textViewTotalCost.setText("Total Cost : "+String.format("%.2f",totalCost));


                rowItemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletePurchaseList(purchaseList);
                    }
                });

                rowItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.gotoPurchaseListDetails(purchaseList);
                    }
                });

            }
        }
    }

    PurchaseListsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PurchaseListsListener) {
            mListener = (PurchaseListsListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PurchaseListsListener");
        }
    }

    public interface PurchaseListsListener {
        void logout();
        AuthResponse getAuthResponse();
        void gotoCreatePurchaseList();
        void gotoPurchaseListDetails(PurchaseList purchaseList);
    }
}