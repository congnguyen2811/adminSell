package com.manager.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.manager.appbanhang.Interface.ItemClickListener;
import com.manager.appbanhang.R;
import com.manager.appbanhang.model.DonHang;
import com.manager.appbanhang.model.EventBus.DonHangEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> donHangList;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_donhang,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang  = donHangList.get(position);
        holder.item_txtDonhang.setText("Đơn hàng : " + donHang.getId());
        holder.item_txtTrangthaidon.setText(trangThaiDon(donHang.getTrangthai())+"");
        holder.item_txtdiachidon.setText("Địa chỉ giao hàng: " + donHang.getDiachi());
        holder.item_txtUsername.setText("Người đặt: " + donHang.getUsername());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                holder.item_rcv_CT.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        linearLayoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        // adapter chi tiet
        ChiTietAdapter adapter = new ChiTietAdapter(context,donHang.getItem());
        holder.item_rcv_CT.setLayoutManager(linearLayoutManager);
        holder.item_rcv_CT.setAdapter(adapter);
        holder.item_rcv_CT.setRecycledViewPool(viewPool);

        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(isLongClick){
                    EventBus.getDefault().postSticky(new DonHangEvent(donHang));
                }
            }
        });
    }
    private String trangThaiDon(int status){
        String result = " ";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lý";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Đơn hàng đã giao thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView item_txtDonhang,item_txtTrangthaidon,item_txtdiachidon,item_txtUsername;
        RecyclerView item_rcv_CT;
        ItemClickListener listener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_rcv_CT  = itemView.findViewById(R.id.item_rcvchitietdonhang);
            item_txtDonhang = itemView.findViewById(R.id.item_txtDonhang);
            item_txtTrangthaidon = itemView.findViewById(R.id.item_txtTinhTrangDon);
            item_txtdiachidon = itemView.findViewById(R.id.item_txtDiachidon);
            item_txtUsername = itemView.findViewById(R.id.item_txtUserName);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onClick(v,getAdapterPosition(),true);
            return false;
        }
    }
}
