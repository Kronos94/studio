package es.iessaladillo.pedrojoya.pr211.ui.main;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import es.iessaladillo.pedrojoya.pr211.R;
import es.iessaladillo.pedrojoya.pr211.data.model.Student;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, Student student, int position);
    }

    private List<Student> data;
    private OnItemClickListener onItemClickListener;
    private final SparseBooleanArray selectedItems = new SparseBooleanArray();
    private final TextDrawable.IBuilder mDrawableBuilder;
    private View mEmptyView;
    private final RecyclerView.AdapterDataObserver mObserver = new RecyclerView
            .AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkEmptyViewVisibility();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkEmptyViewVisibility();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkEmptyViewVisibility();
        }
    };

    public MainFragmentAdapter() {
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .toUpperCase()
                .endConfig()
                .round();
    }

    public void setData(List<Student> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getItemAtPosition(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainFragmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0: data.size();
    }

    public Student getItemAtPosition(int position) {
        if (data != null) {
            return data.get(position);
        } else {
            return null;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        if (mEmptyView != null) {
            unregisterAdapterDataObserver(mObserver);
        }
        mEmptyView = emptyView;
        registerAdapterDataObserver(mObserver);
    }

    private void checkEmptyViewVisibility() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void onDestroy() {
        if (mEmptyView != null) {
            unregisterAdapterDataObserver(mObserver);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblName;
        private final TextView lblAddress;
        private final ImageView imgAvatar;

        private final TextView lblGrade;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            lblName = itemView.findViewById(R.id.lblName);
            lblGrade = itemView.findViewById(R.id.lblGrade);
            lblAddress = itemView.findViewById(R.id.lblAddress);
        }
        public void bind(Student student, int position) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAddress.setText(student.getAddress());
            itemView.setActivated(selectedItems.get(position, false));
            imgAvatar.setImageDrawable(mDrawableBuilder.build(
                    itemView.isActivated() ? "\u2713" : student.getName().substring(0, 1),
                    itemView.isActivated() ? Color.GRAY : ColorGenerator.MATERIAL.getColor(
                            student.getName())));
        }

    }

}
