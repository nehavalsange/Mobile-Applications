package edu.uncc.evaluation05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.evaluation05.databinding.FragmentCreatePurchaseListBinding;
import edu.uncc.evaluation05.databinding.ListItemProductAddBinding;
import edu.uncc.evaluation05.models.AuthResponse;
import edu.uncc.evaluation05.models.Product;
import edu.uncc.evaluation05.models.ProductResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreatePurchaseListFragment extends Fragment {
    public CreatePurchaseListFragment() {
        // Required empty public constructor
    }

    FragmentCreatePurchaseListBinding binding;
    ArrayList<Product> mProducts = new ArrayList<>();
    ProductsAdapter adapter;
    AuthResponse mAuthResponse;
    int itemQty = 0;
    double overAllCost = 0.0;
    ArrayList<String> selectedItemsToken =  new ArrayList<>();
    private final OkHttpClient client = new OkHttpClient();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePurchaseListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Purchase List");
        mAuthResponse = mListener.getAuthResponse();
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.doneCreatingPurchaseList();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextPurchaseListName.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter purchase list name!", Toast.LENGTH_SHORT).show();
                } else {
                    //need to check if the user selected any products
                    if(overAllCost<=0){
                        Toast.makeText(getActivity(), "Please select items", Toast.LENGTH_SHORT).show();
                    }else{
                        addNewPurchaseList(name);
                    }
                }
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProductsAdapter();
        binding.recyclerView.setAdapter(adapter);
        getAllProducts();
    }

    private void addNewPurchaseList(String name) {
        StringBuilder selectedValue = new StringBuilder();
        String prefix = "";
        for(String val:selectedItemsToken){
            selectedValue.append(prefix);
            prefix = ",";
            selectedValue.append(val);
        }
        Log.d("Demo","Selected Values: "+String.valueOf(selectedValue));

        RequestBody requestBody = new FormBody.Builder()
                .add("name",name)
                .add("productIds", String.valueOf(selectedValue))
                .build();

        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/purchases/new")
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
                    mListener.doneCreatingPurchaseList();

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

    private void getAllProducts() {
        // Get all products from the server
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/api/purchases/products")
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

                    ProductResponse productResponse = gson.fromJson(body,ProductResponse.class);
                    mProducts.addAll(productResponse.getProducts());

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

    class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{
        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ListItemProductAddBinding itemBinding = ListItemProductAddBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ProductViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            holder.bind(mProducts.get(position));
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder {
            ListItemProductAddBinding itemBinding;
            Product mProduct;
            public ProductViewHolder(ListItemProductAddBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Product product) {
                mProduct = product;
                itemBinding.textViewName.setText(product.getName());
                itemBinding.textViewCostPerItem.setText(String.valueOf(product.getPrice_per_item()));

                Picasso.get().load(product.getImg_url()) // Pass the URL
                        .into(itemBinding.imageViewIcon);
                itemBinding.imageViewMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Decrement quantity
                        int iQty = Integer.parseInt((String) itemBinding.textViewItemQuantity.getText());

                        if(iQty-1 >= 0){
                            itemBinding.textViewItemQuantity.setText(String.valueOf(iQty-1));
                            overAllCost = overAllCost - mProduct.getPrice_per_item();
                            if(overAllCost <= 0){
                                binding.textViewOverallCost.setText("Overall Cost : $0.0");
                            }else {
                                binding.textViewOverallCost.setText("Overall Cost : $"+String.format("%.2f",overAllCost));
                            }
                            selectedItemsToken.remove(mProduct.getPid());
                            Log.d("Demo", " Eval5 :"+String.valueOf(selectedItemsToken));

                        }

                    }
                });

                itemBinding.imageViewPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Increment quantity
                        int iQty = Integer.parseInt((String) itemBinding.textViewItemQuantity.getText());
                        itemBinding.textViewItemQuantity.setText(String.valueOf(iQty+1));
                        overAllCost = overAllCost + mProduct.getPrice_per_item();
                        binding.textViewOverallCost.setText("Overall Cost : $"+String.format("%.2f",overAllCost));
                        selectedItemsToken.add(mProduct.getPid());

                       // Log.d("Demo", " Eval5 :"+String.valueOf(selectedValue));
                    }
                });

            }
        }
    }

    CreatePurchaseListListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CreatePurchaseListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CreatePurchaseListListener");
        }
    }

    public interface CreatePurchaseListListener {
        void doneCreatingPurchaseList();
        AuthResponse getAuthResponse();
    }

}