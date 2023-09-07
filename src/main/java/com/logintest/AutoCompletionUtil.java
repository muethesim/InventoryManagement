package com.logintest;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.stream.Collectors;

public class AutoCompletionUtil {
    public static void setUpAutoCompletion(TextField textField, List<String> suggestions) {
        ContextMenu contextMenu = new ContextMenu();

        textField.setOnKeyReleased(event -> {
            String text = textField.getText().toLowerCase();
            // || event.getCode() == KeyCode.ENTER  || event.getCode() == KeyCode.TAB
            if (text.length() >= 1) {
                // filter the suggestions
                List<String> filteredSuggestions = suggestions.stream()
                        .filter(s -> s.toLowerCase().contains(text))
                        .collect(Collectors.toList());

                // create a menu item for each suggestion and add it to the context menu
                contextMenu.getItems().clear();
                for (String suggestion : filteredSuggestions) {
                    MenuItem menuItem = new MenuItem(suggestion);
                    contextMenu.getItems().add(menuItem);
                }
                // show the context menu
                contextMenu.show(textField, Side.BOTTOM, 0, 0);
            }
            if (text.length() == 0 || event.getCode() == KeyCode.ENTER) {
                contextMenu.hide();
            }
        });

        // handle selection of a suggestion from the context menu
        contextMenu.setOnAction(event -> {
            String selectedSuggestion = ((MenuItem) event.getTarget()).getText();
            textField.setText(selectedSuggestion);
            contextMenu.hide();
        });
    }
}