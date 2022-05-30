package com.example.present_funding;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> arrayList;
    private Context context;

    public ProductAdapter(ArrayList<Product> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getImg())
                .into(holder.pd_img);
        holder.pd_brand.setText(arrayList.get(position).getBrand());
        holder.pd_name.setText(arrayList.get(position).getName());
        holder.pd_price.setText(String.valueOf(arrayList.get(position).getPrice())+" 원");
    }

    @Override
    public int getItemCount() {
        return (
                arrayList != null ? arrayList.size() : 0); //삼항 연산자
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView pd_img;
        TextView pd_brand, pd_name, pd_price;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.pd_img = itemView.findViewById(R.id.img_product_image);
            this.pd_brand = itemView.findViewById(R.id.txt_product_brand);
            this.pd_name = itemView.findViewById(R.id.txt_product_name);
            this.pd_price = itemView.findViewById(R.id.txt_product_price);

            itemView.setClickable(true);                                // 아이템뷰 클릭시 위치 정보를 가져와서 위치에 맞는 데이터 전송
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();                     // pos = 0, 1, 2...이런 식으로 index값을 가져와서 데이터를 전송함
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, DetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 데이터를 전송할 activity 설정

                        intent.putExtra("brand", arrayList.get(pos).getBrand());      // pos에 맞는 데이터의 브랜드값을 전송
                        intent.putExtra("name", arrayList.get(pos).getName());      // pos에 맞는 데이터의 이름값을 전송
                        intent.putExtra("price", arrayList.get(pos).getPrice());        // pos에 맞는 데이터의 가격값을 전송
                        intent.putExtra("img", arrayList.get(pos).getImg());        // pos에 맞는 데이터의 이미지값을 전송

                        context.startActivity(intent);                                  // 데이터 전송 후 화면 전환
                    }
                }
            });
        }
    }
}
