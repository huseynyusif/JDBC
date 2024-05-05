package org.example;

import java.sql.Date;
import java.sql.Timestamp;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String description;
    private Date dateOfBirthday;//LocalDate

    private Timestamp createdAt;//LocalDateTime
    private int teacherId;
    private String teacherName;
    private int idUpdate;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Student(String name, String surname, String description, Date dateOfBirthday, int teacherId) {
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.dateOfBirthday = dateOfBirthday;
        this.teacherId = teacherId;
    }

    public Student(int id, String name, String surname, String description, Date dateOfBirthday, int teacherId, int idUpdate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.dateOfBirthday = dateOfBirthday;
        this.teacherId = teacherId;
        this.idUpdate = idUpdate;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", description='" + description + '\'' +
                ", dateOfBirthday=" + dateOfBirthday +
                ", createdAt=" + createdAt +

                ", teacherName='" + teacherName + '\'' +

                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public int getIdUpdate() {
        return idUpdate;
    }

    public void setIdUpdate(int idUpdate) {
        this.idUpdate = idUpdate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfBirthday() {
        return dateOfBirthday;
    }

    public void setDateOfBirthday(Date dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
