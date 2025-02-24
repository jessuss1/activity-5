package com.example.quinta2;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.Set;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final int[] images;
    private final String[] names;

    //Hash que guarda las posiciones de los checkboxes activos (isChecked = TRUE)
    private Set<Integer> selectedPositions;

    //TextView para mostrar los checkboxes activos
    private TextView selectionIndicator;
    private Button actionButton;

    //Constante para definir el numero requerido de checkBoxes activos
    private static final int REQUIRED_SELECTIONS = 4;

    //Le agregamos 2 parametros mas para que contenga el TextView el cual da feedback de cuantos checkBoxes hay activos
    // Y el boton el cual cambiara su estado dependiendo si hay 4 checknboxes checked o no
    public ImageAdapter(int[] images, String[] names, TextView selectionIndicator, Button actionButton) {
        this.images = images;
        this.names = names;
        this.selectionIndicator = selectionIndicator;
        this.actionButton = actionButton;
        this.selectedPositions = new HashSet<>();
        updateUI();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(20, 20, 20, 20);
        layout.setBackgroundColor(Color.LTGRAY);

        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        TextView textView = new TextView(parent.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(11);
        textView.setTextColor(Color.BLACK);

        CheckBox checkBox = new CheckBox(parent.getContext());
        checkBox.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        checkBox.setTextSize(11);
        checkBox.setTextAlignment(TEXT_ALIGNMENT_CENTER);

        layout.addView(imageView);
        layout.addView(textView);
        layout.addView(checkBox);

        return new ViewHolder(layout, imageView, textView, checkBox);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.textView.setText(names[position]);
        //holder.checkBox.setText(names[position]);

        holder.checkBox.setChecked(selectedPositions.contains(position));

        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                if (selectedPositions.size() < REQUIRED_SELECTIONS) {
                    selectedPositions.add(position);
                } else {
                    holder.checkBox.setChecked(false); // Cap at 4
                }
            } else {
                selectedPositions.remove(position);
            }
            updateUI();
        });
    }

    @Override
    public int getItemCount() {
        return images.length;
    }


/*
   Actualiza el TextView cuando se selecciona o deselecciona un checkbox
   Y cambia el estado del boton (ENABLED/DISABLED) y el alpha (opacidad)
   Para darle un effecto visual de desactivado .5 = DESACIVADO, 1 = ACTIVO
*/
    private void updateUI() {
        selectionIndicator.setText("Selections: " + selectedPositions.size() + "/" + REQUIRED_SELECTIONS);
        boolean isComplete = selectedPositions.size() == REQUIRED_SELECTIONS;
        actionButton.setEnabled(isComplete);
        actionButton.setAlpha(isComplete ? 1.0f : 0.5f);
    }

    public boolean isSelectionComplete() {
        return selectedPositions.size() == REQUIRED_SELECTIONS;
    }

    public Set<Integer> getSelectedPositions() {
        return selectedPositions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        public ViewHolder(@NonNull LinearLayout itemView, ImageView imageView, TextView textView, CheckBox checkBox) {
            super(itemView);
            this.imageView = imageView;
            this.textView = textView;
            this.checkBox = checkBox;
        }
    }
}