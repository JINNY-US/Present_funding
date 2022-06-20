package com.example.present_funding;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.TempViewHolder> {  //<FundingAdapter.ProductViewHolder>


    private ArrayList<Temp> arrayList;
    private Context context;

    public TempAdapter(ArrayList<Temp> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.support_list, parent, false);
        TempViewHolder holder = new TempViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull TempViewHolder holder, int position) {
        holder.sid.setText(arrayList.get(position).getSupport_uid()); //호스트 이름 (펀딩을 오픈한 사람을 구별하기 위함)
        holder.s_name.setText(arrayList.get(position).getSupport_name());
        holder.s_collection.setText(String.valueOf(arrayList.get(position).getTemp()));
    }

    @Override
    public int getItemCount() {
        return (
                arrayList != null ? arrayList.size() : 0); //삼항 연산자
    }

    public class TempViewHolder extends RecyclerView.ViewHolder {

        TextView sid, s_name, s_collection;

        public TempViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sid = itemView.findViewById(R.id.txt_support_id);
            this.s_name = itemView.findViewById(R.id.txt_support_name);
            this.s_collection = itemView.findViewById(R.id.txt_support_collection);

            itemView.setClickable(true);                                // 아이템뷰 클릭시 위치 정보를 가져와서 위치에 맞는 데이터 전송
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();                     // pos = 0, 1, 2...이런 식으로 index값을 가져와서 데이터를 전송함
                    if(pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, AskListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 데이터를 전송할 activity 설정

                        intent.putExtra("sid", arrayList.get(pos).getSupport_uid());
                        intent.putExtra("s_name", arrayList.get(pos).getSupport_name());
                        intent.putExtra("s_collection", arrayList.get(pos).getTemp());
                        intent.putExtra("val", arrayList.get(pos).getVal());

                        context.startActivity(intent);                                  // 데이터 전송 후 화면 전환
                    }
                }
            });
        }
    }
}