package shivani.com.grabtest.adapter;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import shivani.com.grabtest.R;
import shivani.com.grabtest.vo.Article;

/**
 * Created by Shivani on 07/03/19.
 */
public class NewsListAdapter extends PagedListAdapter<Article, NewsListAdapter.ViewHolder> {

    private OnItemClickListener listener;

    private static final DiffUtil.ItemCallback<Article> diffCallback = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(Article oldItem, Article newItem) {
            return oldItem.getUrl().equals(newItem.getUrl());
        }

        @Override
        public boolean areContentsTheSame(Article oldItem, Article newItem) {
            return oldItem.getUrl().equals(newItem.getUrl());
        }
    };

    public NewsListAdapter() {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mName.setText(getItem(i).getTitle());
        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(viewHolder.mImage.getContext())
                .setDefaultRequestOptions(defaultOptions)
                .load(getCurrentList().get(i).getUrlToImage())
                .into(viewHolder.mImage);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.name)
        TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(getCurrentList().get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Article article);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

