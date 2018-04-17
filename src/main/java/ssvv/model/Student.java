package ssvv.model;


public class Student {
    private int regNumber;
    private String name;
    private int group;

    public Student(int regNumber, String name, int group) {
        this.regNumber = regNumber;
        this.name = name;
        this.group = group;
    }

    public int getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(int regNumber) {
        this.regNumber = regNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return regNumber + "," + name + "," + group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return regNumber == student.regNumber &&
                group == student.group &&
                name.equals(student.name);
    }

}