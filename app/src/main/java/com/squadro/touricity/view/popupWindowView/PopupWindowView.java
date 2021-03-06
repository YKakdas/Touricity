package com.squadro.touricity.view.popupWindowView;

import android.widget.Button;
import android.widget.LinearLayout;

import com.squadro.touricity.R;

import java.util.ArrayList;
import java.util.List;

public class PopupWindowView {

    private final int HEIGHT = 120;
    private final int WIDTH = 250;
    private LinearLayout linearLayout;
    private int numberOfButtons;
    private List<String> nameOfButtons;
    private PopupWindowParameters popupWindowParameters;
    private List<Button> buttons;

    LinearLayout.LayoutParams layoutParams;

    private int totalHeight;

    public PopupWindowView(PopupWindowParameters popupWindowParameters, LinearLayout linearLayout) {
        this.popupWindowParameters = popupWindowParameters;
        this.linearLayout = linearLayout;
        numberOfButtons = popupWindowParameters.getNumberOfButtons();
        nameOfButtons = popupWindowParameters.getButtonNames();
        totalHeight = HEIGHT * numberOfButtons;
        layoutParams = new LinearLayout.LayoutParams(WIDTH, HEIGHT);
        layoutParams.setMargins(0, 5, 0, 0);
        buttons = new ArrayList<>();
        addButtonsToView();
    }

    private void addButtonsToView() {
        for (int i = 0; i < numberOfButtons; i++) {
            Button button = new Button(linearLayout.getContext());
            button.setText(nameOfButtons.get(i));
            button.setLayoutParams(layoutParams);
            button.setBackgroundResource(R.drawable.rounded_button);
            buttons.add(button);
            linearLayout.addView(button);
        }
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public List<Button> getButtons(){
        return buttons;
    }
}
