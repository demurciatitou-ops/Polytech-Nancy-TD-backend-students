package com.example.todoapp;

import java.util.*;

/**
 * Data Access Object for {@link Task} model.
 */
public class TaskDao {

    private final Map<Integer, Task> storage = new HashMap<>();

    {
        save(new Task(1, "Réviser DS de maths", "Séries numériques et probabilités.", false));
        save(new Task(2, "Valider mon PIVE", "PIVE Club Poker.", true));
        save(new Task(3, "Choisir mon parcours de 4A", "SIR ou SIA ?", false));
    }

    /**
     * Persist {@link Task} model.
     * @param task task to save.
     * @return task model.
     */
    public Task save(Task task) {
        storage.put(task.id(), task);
        return task;
    }

    public Optional<Task> delete(int id){
        storage.remove(storage.get(id));
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Retrieve {@link Task} model by id.
     * @param id identifier of the {@link Task}.
     * @return {@link Task} model wrapped by Optional.
     */
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<Task> getAll(boolean todo){
        List<Task> result = new ArrayList<>();
        if (todo == true) {
            for (Task task : this.storage.values()) {
                if (task.done() == false) { // la tâche n'a pas encore était
                    result.add(task);
                }
            }
        }
        else {
            for (Task task : this.storage.values()) {
                result.add(task);
            }
        }
        return result;
    }

    public Optional<Task> modify(Task newTask, int id){
        if (this.storage.get(id) == null){
            return Optional.empty();
        }
        else {
            this.storage.put(id, newTask);
            return Optional.ofNullable(newTask);
        }
    }
}
