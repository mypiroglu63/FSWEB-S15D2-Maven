package org.example.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskData {
    private Set<Task> annsTasks;
    private Set<Task> bobsTasks;
    private Set<Task> carolsTasks;
    private Set<Task> unassignedTasks;

    public TaskData(Set<Task> annsTasks, Set<Task> bobsTasks, Set<Task> carolsTasks, Set<Task> unassignedTasks) {
        this.annsTasks = annsTasks;
        this.bobsTasks = bobsTasks;
        this.carolsTasks = carolsTasks;
        this.unassignedTasks = unassignedTasks;
    }

    public Set<Task> getTasks(String assignee) {
        switch (assignee.toLowerCase()) {
            case "ann":
                return new HashSet<>(annsTasks);
            case "bob":
                return new HashSet<>(bobsTasks);
            case "carol":
                return new HashSet<>(carolsTasks);
            case "all":
                return Stream.of(annsTasks, bobsTasks, carolsTasks, unassignedTasks)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet());
            default:
                throw new IllegalArgumentException("Invalid assignee: " + assignee);
        }
    }

    public Set<Task> getUnion(Set<Task>... sets) {
        return Stream.of(sets)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<Task> getIntersection(Set<Task> set1, Set<Task> set2) { // isim düzeltildi
        return set1.stream()
                .filter(set2::contains)
                .collect(Collectors.toSet());
    }

    public Set<Task> getDifference(Set<Task> set1, Set<Task> set2) {
        return set1.stream()
                .filter(task -> !set2.contains(task))
                .collect(Collectors.toSet());
    }
}
