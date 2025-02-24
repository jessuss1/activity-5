package com.example.quinta2;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


//Esta Clase va a dividir la pantalla en 3 partes para dar espacio para controles de la app (boton)
public class LayoutUtils {
    public static LinearLayout createThreePartLayout(Context context, RecyclerView recyclerView, TextView selectionIndicator, Button actionButton) {
        // Root LinearLayout para dividir la pantalla en 3 partes
        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        rootLayout.setWeightSum(3f);

        // Los primeros 2/3 de pantalla para el RecyclerView
        LinearLayout.LayoutParams recyclerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );
        // Aqui le decimos cuanto usara el recyler (usara 2/3)
        recyclerParams.weight = 2f;
        recyclerView.setLayoutParams(recyclerParams);

        // El 1/3 inferiror de pantalla para el checkBox tracker y el boton
        LinearLayout bottomLayout = new LinearLayout(context);
        LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0
        );
        // Aqui le decimos cuanto usara el
        bottomParams.weight = 1f;
        bottomLayout.setLayoutParams(bottomParams);
        bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
        bottomLayout.setGravity(Gravity.CENTER);

        // TextView que ,muestra cuantos checkBoxes hay seleccionados
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.weight = 1f;
        selectionIndicator.setLayoutParams(textParams);
        selectionIndicator.setGravity(Gravity.CENTER);
        selectionIndicator.setTextSize(18f);


        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.weight = 1f;
        actionButton.setLayoutParams(buttonParams);
        actionButton.setGravity(Gravity.CENTER);
        styleButtonAsDefault(actionButton, context);

        // Agrega los elementos al layout
        bottomLayout.addView(selectionIndicator);
        bottomLayout.addView(actionButton);

        // Ensambla el layout con el recyclerView en los primeros 2/3 de pantalla
        // y el label y el boton en el tercio de pantalla inferior
        rootLayout.addView(recyclerView);
        rootLayout.addView(bottomLayout);

        return rootLayout;
    }

    private static void styleButtonAsDefault(Button button, Context context) {
        // Estilo para el boton
        button.setBackgroundTintList(context.getResources().getColorStateList(android.R.color.holo_blue_dark)); // Default blue
        button.setTextColor(context.getResources().getColor(android.R.color.white));
        button.setPadding(16, 8, 16, 8); // Padding similar to MaterialButton
        button.setTextSize(14f); // Default text size
        button.setAllCaps(true); // Default button text is uppercase
        button.setElevation(2f); // Slight elevation for Material feel
    }
}