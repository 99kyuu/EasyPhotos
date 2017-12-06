package com.huantansheng.easyphotos.ui.adapter;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huantansheng.easyphotos.R;
import com.huantansheng.easyphotos.models.puzzle.PuzzleLayout;
import com.huantansheng.easyphotos.models.puzzle.SquarePuzzleView;
import com.huantansheng.easyphotos.models.puzzle.template.slant.NumberSlantLayout;
import com.huantansheng.easyphotos.models.puzzle.template.straight.NumberStraightLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wupanjie
 */
public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.PuzzleViewHolder> {

    private List<PuzzleLayout> layoutData = new ArrayList<>();
    private List<Bitmap> bitmapData = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int selectedNumber = 0;

    @Override
    public PuzzleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_puzzle, parent, false);
        return new PuzzleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PuzzleViewHolder holder, int position) {
        final PuzzleLayout puzzleLayout = layoutData.get(position);
        final int p = position;
        if (selectedNumber == position) {
            holder.mFrame.setVisibility(View.VISIBLE);
        } else {
            holder.mFrame.setVisibility(View.GONE);
        }
        holder.puzzleView.setNeedDrawLine(true);
        holder.puzzleView.setNeedDrawOuterLine(true);
        holder.puzzleView.setTouchEnable(false);

        holder.puzzleView.setPuzzleLayout(puzzleLayout);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    int themeType = 0;
                    int themeId = 0;
                    if (puzzleLayout instanceof NumberSlantLayout) {
                        themeType = 0;
                        themeId = ((NumberSlantLayout) puzzleLayout).getTheme();
                    } else if (puzzleLayout instanceof NumberStraightLayout) {
                        themeType = 1;
                        themeId = ((NumberStraightLayout) puzzleLayout).getTheme();
                    }
                    selectedNumber = p;
                    onItemClickListener.onItemClick(themeType, themeId);
                    notifyDataSetChanged();
                }
            }
        });

        if (bitmapData == null) return;

        final int bitmapSize = bitmapData.size();

        if (puzzleLayout.getAreaCount() > bitmapSize) {
            for (int i = 0; i < puzzleLayout.getAreaCount(); i++) {
                holder.puzzleView.addPiece(ContextCompat.getDrawable(holder.puzzleView.getContext(), R.drawable.bg_dialog_album_items_background_easy_photos));
            }
        } else {
            holder.puzzleView.addPiece(ContextCompat.getDrawable(holder.puzzleView.getContext(), R.drawable.bg_dialog_album_items_background_easy_photos));

        }
    }

    @Override
    public int getItemCount() {
        return layoutData == null ? 0 : layoutData.size();
    }

    public void refreshData(List<PuzzleLayout> layoutData, List<Bitmap> bitmapData) {
        this.layoutData = layoutData;
        this.bitmapData = bitmapData;

        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class PuzzleViewHolder extends RecyclerView.ViewHolder {

        SquarePuzzleView puzzleView;
        View mFrame;

        public PuzzleViewHolder(View itemView) {
            super(itemView);
            puzzleView = (SquarePuzzleView) itemView.findViewById(R.id.puzzle);
            mFrame = itemView.findViewById(R.id.m_selector);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int themeType, int themeId);
    }
}
