package com.example.ecommerce.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.R;
import com.example.ecommerce.activities.ShowAllActivity;
import com.example.ecommerce.adapter.CategoryAdapter;
import com.example.ecommerce.adapter.NewProductsAdapter;
import com.example.ecommerce.adapter.PopularProductsAdapter;
import com.example.ecommerce.models.CategoryModel;
import com.example.ecommerce.models.NewProductModel;
import com.example.ecommerce.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView popularShowAll, newProductShowAll;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView categoryRecyclerView,newProductRecyclerView,popularRecyclerView;

    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    NewProductsAdapter newProductsAdapter;
    List<NewProductModel> newProductModelList;

    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;
    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getActivity());
        categoryRecyclerView = root.findViewById(R.id.rec_category);
        newProductRecyclerView = root.findViewById(R.id.new_product_rec);
        popularRecyclerView = root.findViewById(R.id.popular_rec);

        newProductShowAll = root.findViewById(R.id.newProducts_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);


        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        db = FirebaseFirestore.getInstance();

        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModelList = new ArrayList<>();

        slideModelList.add(new SlideModel(R.drawable.banner1,"", ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner2,"", ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner3,"", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModelList);
        progressDialog.setTitle("Chào Mừng Bạn Đã Tham Gia");
        progressDialog.setMessage("Lam on ......");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        CategoryModel categoryModel =documentSnapshot.toObject(CategoryModel.class);
                        categoryModelList.add(categoryModel);
                        categoryAdapter.notifyDataSetChanged();
                        linearLayout.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                }else {
                    Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });

        newProductRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductModelList);
        newProductRecyclerView.setAdapter(newProductsAdapter);
        db.collection("NewProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        NewProductModel newProductModel =documentSnapshot.toObject(NewProductModel.class);
                        newProductModelList.add(newProductModel);
                        newProductsAdapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });

        popularRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularRecyclerView.setAdapter(popularProductsAdapter);
        db.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        PopularProductsModel popularProductsModel =documentSnapshot.toObject(PopularProductsModel.class);
                        popularProductsModelList.add(popularProductsModel);
                        popularProductsAdapter.notifyDataSetChanged();
                    }
                }else {
                    Toast.makeText(getActivity(),""+task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }
}