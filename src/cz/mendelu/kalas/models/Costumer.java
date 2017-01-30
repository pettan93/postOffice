package cz.mendelu.kalas.models;

/**
 * Customer model
 */
public class Costumer {

    private int number;

    public Costumer(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Costumer costumer = (Costumer) o;

        return number == costumer.number;
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "number=" + number +
                '}';
    }
}
