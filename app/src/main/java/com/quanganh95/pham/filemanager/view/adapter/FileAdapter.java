package com.quanganh95.pham.filemanager.view.adapter;

import android.content.Context;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quanganh95.pham.filemanager.R;
import com.quanganh95.pham.filemanager.entity.DetailFile;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<DetailFile> files;
    private OnItemClickListener onItemClickListener;


    public FileAdapter(Context context, List<DetailFile> files) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.files = files;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (files.size() != 0) {
            itemView = inflater.inflate(R.layout.item_file, parent, false);
        } else {
            itemView = inflater.inflate(R.layout.item_not_file, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (files.size() > 0) {
            final DetailFile file = files.get(position);
            holder.imageView.setImageResource(file.getImageFormatFile());
            holder.txtTitle.setText(file.getNameFile());
            holder.txtDetail.setText(file.getDetailFile());
            holder.txtDateFile.setText(file.getDateFile());
            holder.itemFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(file);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(DetailFile detailFile);
    }

    @Override
    public int getItemCount() {
        int i = files.size();
        if (i == 0) {
            return 1;
        } else {
            return i;
        }
    }

    public void resetListFile() {
        for (int i = 0; i < getCountFiles(); i++) {
            Log.d("CountFile", i + "");
            files.remove(i);
        }
    }

    public void setFiles(List<DetailFile> files) {
        this.files = files;
    }

    public int getCountFiles() {
        return files.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout itemFile;
        private ImageView imageView;
        private TextView txtTitle;
        private TextView txtDetail;
        private TextView txtDateFile;

        public ViewHolder(View itemView) {
            super(itemView);
            itemFile = itemView.findViewById(R.id.linearItemFile);
            imageView = itemView.findViewById(R.id.imgIcon);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDetail = itemView.findViewById(R.id.txtDetail);
            txtDateFile = itemView.findViewById(R.id.txtDate);
        }
    }
}
