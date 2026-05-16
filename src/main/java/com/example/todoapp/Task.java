package com.example.todoapp;

/**
 * Task model.
 * @param id            task identifier
 * @param title         task title
 * @param description   task description
 * @param done          task accomplishment status (false by default)
 */
public record Task(Integer id, String title, String description, boolean done) {

}
