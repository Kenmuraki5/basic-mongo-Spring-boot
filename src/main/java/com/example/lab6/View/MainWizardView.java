package com.example.lab6.View;

import com.example.lab6.pojo.Wizard;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Route(value = "/mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField t1, t2;
    private ComboBox<String> c1, c2, c3;
    private RadioButtonGroup r1;
    private HorizontalLayout but;
    private Button doubleLeftArrowButton, createButton, updateButton, deleteButton, doubleRightArrowButton;
    private int currentIndex = 0;
    private List<Wizard> wizards;
    public MainWizardView(){
        t1 = new TextField("Fullname", "fullname");
        but = new HorizontalLayout();
        t2 = new TextField("Dollars");
        t2.setPrefixComponent(new Span("$"));
        c1 = new ComboBox<>("position");c2 = new ComboBox<>("School");c3 = new ComboBox<>("house");
        c1.setPlaceholder("Position");
        c2.setPlaceholder("School");
        c3.setPlaceholder("house");
        c1.setItems("Student", "Teacher");
        c2.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        c3.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        r1 = new RadioButtonGroup();
        r1.setLabel("Gender");
        r1.setItems("m", "f");
        doubleLeftArrowButton = new Button("<<");
        createButton = new Button("Create");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");
        doubleRightArrowButton = new Button(">>");
        but.add(doubleLeftArrowButton, createButton, updateButton, deleteButton, doubleRightArrowButton);
        add(t1, r1, c1, t2, c2, c3, but);
        doubleLeftArrowButton.addClickListener(e -> {
            currentIndex--;
            displayCurrentWizard();

        });

        createButton.addClickListener(e -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("name", t1.getValue());
            formData.add("sex", r1.getValue().toString());
            formData.add("position", c1.getValue());
            formData.add("money", t2.getValue().equals("") ? "0" : t2.getValue());
            formData.add("school", c2.getValue());
            formData.add("house", c3.getValue());
            Wizard res = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .contentType(MediaType.ALL.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();

            if (res != null) {
                Notification notification = Notification.show("Add Wizard Success");
            }
            currentIndex = wizards.size();
            loadWizards();
        });


        updateButton.addClickListener(e -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("id", wizards.get(currentIndex).get_id());
            formData.add("nameNew", t1.getValue());
            formData.add("sex", r1.getValue().toString());
            formData.add("position", c1.getValue());
            formData.add("money", t2.getValue().equals("") ? "0" : t2.getValue());
            formData.add("school", c2.getValue());
            formData.add("house", c3.getValue());
            boolean res = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .contentType(MediaType.ALL.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve().bodyToMono(boolean.class)
                    .block();
            if (res) {
                Notification notification = Notification
                        .show("Update Wizard Success");
            }
            loadWizards();
        });

        deleteButton.addClickListener(e -> {
            boolean res = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/" + "/deleteWizard/" + wizards.get(currentIndex).get_id())
                    .retrieve().bodyToMono(boolean.class)
                    .block();
            if(res){
                Notification notification = Notification
                        .show("Wizard has been removed");
            }
            loadWizards();
        });

        doubleRightArrowButton.addClickListener(e -> {
            currentIndex++;
            this.displayCurrentWizard();
        });
        loadWizards();
    }
    private void loadWizards() {
        wizards = WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToFlux(Wizard.class)
                .collectList()
                .block();

        displayCurrentWizard();
    }
    private void displayCurrentWizard() {
        if (wizards != null && !wizards.isEmpty()) {
            currentIndex = Math.min(currentIndex, wizards.size() - 1);
            currentIndex = Math.max(currentIndex, 0);
            Wizard currentWizard = wizards.get(currentIndex);

            t1.setValue(currentWizard.getName());
            r1.setValue(currentWizard.getSex());
            c1.setValue(currentWizard.getPosition());
            t2.setValue(String.valueOf(currentWizard.getMoney()));
            c2.setValue(currentWizard.getSchool());
            c3.setValue(currentWizard.getHouse());
        }
        else {
            t1.setValue("");
            r1.setValue("");
            c1.setValue("");
            t2.setValue("");
            c2.setValue("");
            c3.setValue("");
        }
    }
}
