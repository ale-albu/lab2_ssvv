package ssvv;

import ssvv.ui.LaboratoriesUI;

import java.io.IOException;;

public class App {

    public static void main(String[] args) {
    	LaboratoriesUI view  = new LaboratoriesUI();

        try {
            view.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}