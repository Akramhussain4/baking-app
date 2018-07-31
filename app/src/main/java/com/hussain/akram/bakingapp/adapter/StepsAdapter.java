package com.hussain.akram.bakingapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hussain.akram.bakingapp.BR;
import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.databinding.LayoutStepItemBinding;
import com.hussain.akram.bakingapp.model.Steps;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    final private List<Steps> stepsList;
    final private StepsClickListener stepsClickListener;
    final private Context context;

    public interface StepsClickListener{
        void stepClickListener(int index);
    }

    public StepsAdapter(List<Steps> stepsList, Context context,StepsClickListener clickListener) {
        this.stepsList = stepsList;
        this.context = context;
        this.stepsClickListener = clickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_step_item, viewGroup, false);
        return new StepsAdapter.StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder stepsViewHolder, int i) {
        int serial = i+1;
        stepsViewHolder.itemBinding.setVariable(BR.steps, stepsList.get(i));
        stepsViewHolder.itemBinding.executePendingBindings();
        stepsViewHolder.tvSerial.setText(String.valueOf(serial));
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final LayoutStepItemBinding itemBinding;
        @BindView(R.id.tvSerial)
        TextView tvSerial;

        private StepsViewHolder(final View itemView) {
            super(itemView.getRootView());
            itemBinding = DataBindingUtil.bind(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            stepsClickListener.stepClickListener(getAdapterPosition());
        }
    }

}
