package com.aps.user.apssolutions;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/3/2017.
 */

public class albumAdapter extends RecyclerView.Adapter<albumAdapter.MyViewHolder> {

    private List<OrderDetail> orderList;
    Context ctx;




    public albumAdapter(List<OrderDetail> albumList,Context ctx) {

        this.orderList = albumList;
        this.ctx=ctx;

    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlist, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(itemView,ctx,orderList);
        return myViewHolder ;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderDetail order = orderList.get(position);

        holder.client_name .setText(order.getClientName());
        holder.company_name.setText(order.getCompanyName());
        holder.order_id.setText(order.getOrderNo());
        holder.order_date.setText(order.getOrderDate());


      /* holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                *//*Intent intent=new Intent(v.getContext(),ShowOrders.class);
                User u=(User)v.getContext().getApplicationContext();
                intent.putExtra("user_id",u.name);
                intent.putExtra("order_id",a);
                v.getContext().startActivity(intent);
*//*
                Toast.makeText(v.getContext().getApplicationContext(),""+a,
                        Toast.LENGTH_LONG).show();

            }
        });*/
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView order_id, order_date, client_name, company_name;
        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        Context ctx;


        public MyViewHolder(View view, Context ctx, List<OrderDetail> orderDetails) {

            super(view);
            this.orderDetails = orderDetails;
            this.ctx = ctx;


            order_date = (TextView) view.findViewById(R.id.order_date);
            order_id = (TextView) view.findViewById(R.id.order_id);
            client_name = (TextView) view.findViewById(R.id.client_name);
            company_name = (TextView) view.findViewById(R.id.company_name);

        }
//
//        @Override
//        public void onClick(View view) {
//            OrderDetail item = orderDetails.get(getLayoutPosition());
//            Intent intent = new Intent(ctx, ShowOrders.class);
//            intent.putExtra("userid",item.getId() );
//            ctx.startActivity(intent);



        /*int position=getAdapterPosition();
            OrderDetail orderDetail=this.orderDetails.get(position);
            Intent intent=new Intent(this.ctx,ShowOrders.class);
            intent.putExtra("orderid",orderDetail.getOrderNo());
            this.ctx.startActivity(intent);*/

        }

    }



