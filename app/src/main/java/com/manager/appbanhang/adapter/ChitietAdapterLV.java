package com.manager.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manager.appbanhang.R;
import com.manager.appbanhang.model.Item;

import java.util.List;

public class ChitietAdapterLV extends BaseAdapter {
    Context context;
    List<Item> itemList;

    public ChitietAdapterLV(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        ImageView item_img_CT;
        TextView item_txttensp_CT,item_txtSoluong_CT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_chitiet,null);

            viewHolder.item_img_CT = convertView.findViewById(R.id.item_imgchitiet);
            viewHolder.item_txttensp_CT = convertView.findViewById(R.id.item_txttensp_CT);
            viewHolder.item_txtSoluong_CT = convertView.findViewById(R.id.item_txtSoluong_CT);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
       viewHolder.item_txttensp_CT.setText(itemList.get(position).getTensp()+"");
       viewHolder.item_txtSoluong_CT.setText("Số lượng: " + itemList.get(position).getSoluong());
        Glide.with(context).load(itemList.get(position).getHinhanh()).into(viewHolder.item_img_CT);
        return convertView;
    }
}
