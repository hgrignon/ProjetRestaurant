package com.example.restaurant.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.R;
import com.example.restaurant.enums.ToolType;

import java.util.ArrayList;
import java.util.List;

public class EditingToolsAdapter extends RecyclerView.Adapter<EditingToolsAdapter.ViewHolder> {
    private final OnItemSelected mOnItemSelected;
    private final List<ToolModel> mToolList;

    public interface OnItemSelected {
        void onToolSelected(ToolType toolType);
    }

    public EditingToolsAdapter(OnItemSelected onItemSelected) {
        mOnItemSelected = onItemSelected;
        mToolList = new ArrayList<>();

        mToolList.add(new ToolModel("Shape", R.drawable.ic_oval, ToolType.SHAPE));
        mToolList.add(new ToolModel("Text", R.drawable.ic_text, ToolType.TEXT));
        mToolList.add(new ToolModel("Eraser", R.drawable.ic_eraser, ToolType.ERASER));
        mToolList.add(new ToolModel("Filter", R.drawable.ic_photo_filter, ToolType.FILTER));
        mToolList.add(new ToolModel("Emoji", R.drawable.ic_insert_emoticon, ToolType.EMOJI));
        mToolList.add(new ToolModel("Sticker", R.drawable.ic_sticker, ToolType.STICKER));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_editing_tools, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToolModel item = mToolList.get(position);
        holder.txtTool.setText(item.getToolName());
        holder.imgToolIcon.setImageResource(item.getToolIcon());
    }

    @Override
    public int getItemCount() {
        return mToolList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgToolIcon;
        private final TextView txtTool;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgToolIcon = itemView.findViewById(R.id.imgToolIcon);
            txtTool = itemView.findViewById(R.id.txtTool);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemSelected.onToolSelected(mToolList.get(getLayoutPosition()).getToolType());
                }
            });
        }
    }

    public static class ToolModel {
        private final String mToolName;
        private final int mToolIcon;
        private final ToolType mToolType;

        public ToolModel(String toolName, int toolIcon, ToolType toolType) {
            mToolName = toolName;
            mToolIcon = toolIcon;
            mToolType = toolType;
        }

        public String getToolName() {
            return mToolName;
        }

        public int getToolIcon() {
            return mToolIcon;
        }

        public ToolType getToolType() {
            return mToolType;
        }
    }
}

