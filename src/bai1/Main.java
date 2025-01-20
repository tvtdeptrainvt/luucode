package bai1;

import java.util.HashSet;
import java.util.Set;

class Employee{
    private int id;
    private String name;
    private int age;

    public Employee(int id, String name , int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return id == employee.id;
    }
    @Override
    public int hashCode(){
        return Integer.hashCode(id);
    }

    @Override
    public String toString(){
        return "Employee{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
    /* dung TreeSet
    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id); // So sánh theo id
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
    */
}
public class Main {
    public static void main(String[] args) {
        Set<Employee> employees = new HashSet<>();

        employees.add(new Employee(1, "thang", 21));
        employees.add(new Employee(2, "chien", 31));
        employees.add(new Employee(3, "quang", 30));
        employees.add(new Employee(1, "thu", 28));

        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }
}
