package models;

import java.io.Serializable;

public class Question implements Serializable {

    private int id;
    private String texte = "";
    private String value = "";
    private int idTest;
    private int nombreChoix;

    public Question(int id, String texte, String value, int idTest) {
        this.id = id;
        this.texte = texte;
        this.value = value;
        this.idTest = idTest;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIdTest() {
        return idTest;
    }

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public int getNombreChoix() {
        return nombreChoix;
    }

    public void setNombreChoix(int nombreChoix) {
        this.nombreChoix = nombreChoix;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", texte='" + texte + '\'' +
                ", value='" + value + '\'' +
                ", idTest=" + idTest +
                ", nombreChoix=" + nombreChoix +
                '}';
    }
}
