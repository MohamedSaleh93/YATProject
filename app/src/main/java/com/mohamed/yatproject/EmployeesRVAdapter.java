package com.mohamed.yatproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmployeesRVAdapter extends RecyclerView.Adapter<EmployeesRVAdapter.EmployeesViewHolder> {

    private ArrayList<String> employeesList;

    public EmployeesRVAdapter(ArrayList<String> employees) {
        this.employeesList = employees;
    }

    @NonNull
    @Override
    public EmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View employeeView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employess_list_item, null, false);
        EmployeesViewHolder empVH = new EmployeesViewHolder(employeeView);
        return empVH;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeesViewHolder holder, int position) {
        holder.employeeName.setText(employeesList.get(position));
    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    class EmployeesViewHolder extends RecyclerView.ViewHolder {

        ImageView employeeAvatar;
        TextView employeeName;

        public EmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAvatar = itemView.findViewById(R.id.employeeAvatar);
            employeeName = itemView.findViewById(R.id.employeeName);
        }
    }
}
