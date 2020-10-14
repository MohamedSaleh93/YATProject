package com.mohamed.yatproject;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mohamed.yatproject.database.employees.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesRVAdapter extends RecyclerView.Adapter<EmployeesRVAdapter.EmployeesViewHolder> {

    private List<Employee> employeesList;

    public EmployeesRVAdapter(List<Employee> employees) {
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
    public void onBindViewHolder(@NonNull EmployeesViewHolder holder, final int position) {
        holder.employeeName.setText(employeesList.get(position).name);
        holder.employeePosition.setText(employeesList.get(position).position);
        Uri imageUri = Uri.parse(Uri.decode(employeesList.get(position).imagePath));
        holder.employeeAvatar.setImageURI(imageUri);

        holder.employeesItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EmployeeDetailsActivity.class);
                i.putExtra("employee", employeesList.get(position));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    class EmployeesViewHolder extends RecyclerView.ViewHolder {

        ImageView employeeAvatar;
        TextView employeeName;
        TextView employeePosition;
        ConstraintLayout employeesItemContainer;

        public EmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeAvatar = itemView.findViewById(R.id.employeeAvatar);
            employeeName = itemView.findViewById(R.id.employeeName);
            employeePosition = itemView.findViewById(R.id.employeePosition);
            employeesItemContainer = itemView.findViewById(R.id.employeesItemContainer);
        }
    }
}
