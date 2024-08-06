import org.example.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ResultAnalyzer.class)
public class MainTest {

    Task task1;
    Task task2;
    Task task3;
    TaskData taskData;
    Set<Task> taskSet1;
    Set<Task> taskSet2;
    Set<Task> taskSet3;

    @BeforeEach
    void setUp() {
        task1 = new Task("Java Collections", "Write List Interface",
                "Ann", Priority.LOW, Status.IN_QUEUE);
        task2 = new Task("Java Collections", "Write Set Interface",
                "Ann", Priority.MED, Status.ASSIGNED);
        task3 = new Task("Java Collections", "Write Map Interface",
                "Bob", Priority.HIGH, Status.IN_QUEUE);

        taskSet1 = new HashSet<>();
        taskSet1.add(task1);
        taskSet1.add(task2);
        taskSet1.add(task3);

        taskSet2 = new HashSet<>();
        taskSet3 = new HashSet<>();

        taskData = new TaskData(taskSet1, taskSet2, taskSet3, new HashSet<>());
    }

    @DisplayName("Task sınıfı doğru access modifiers sahip mi")
    @Test
    public void testTaskAccessModifiers() throws NoSuchFieldException {
        Field assigneeFields = task1.getClass().getDeclaredField("assignee");
        Field descriptionsFields = task1.getClass().getDeclaredField("description");
        Field projectFields = task1.getClass().getDeclaredField("project");
        Field priorityFields = task1.getClass().getDeclaredField("priority");
        Field statusFields = task1.getClass().getDeclaredField("status");

        assertEquals(assigneeFields.getModifiers(), 2);
        assertEquals(descriptionsFields.getModifiers(), 2);
        assertEquals(projectFields.getModifiers(), 2);
        assertEquals(priorityFields.getModifiers(), 2);
        assertEquals(statusFields.getModifiers(), 2);
    }

    @DisplayName("Task sınıfı doğru typelara sahip mi")
    @Test
    public void testTaskTypes() throws NoSuchFieldException {
        assertThat(task1.getAssignee(), instanceOf(String.class));
        assertThat(task1.getDescription(), instanceOf(String.class));
        assertThat(task1.getPriority(), instanceOf(Priority.class));
        assertThat(task1.getProject(), instanceOf(String.class));
        assertThat(task1.getStatus(), instanceOf(Status.class));
    }

    @DisplayName("TaskData sınıfı doğru access modifiers sahip mi")
    @Test
    public void testTaskDataAccessModifiers() throws NoSuchFieldException {
        Field annTasksField = taskData.getClass().getDeclaredField("annsTasks");
        Field bobTasksField = taskData.getClass().getDeclaredField("bobsTasks");
        Field carolTasksField = taskData.getClass().getDeclaredField("carolsTasks");
        Field unassignedTasksField = taskData.getClass().getDeclaredField("unassignedTasks");

        assertEquals(annTasksField.getModifiers(), 2);
        assertEquals(bobTasksField.getModifiers(), 2);
        assertEquals(carolTasksField.getModifiers(), 2);
        assertEquals(unassignedTasksField.getModifiers(), 2);
    }

    @DisplayName("TaskData getTasks method doğru çalışıyor mu ?")
    @Test
    public void testGetTasksMethod() {
        assertEquals(taskData.getTasks("ann"), taskSet1);
        assertEquals(taskData.getTasks("bob"), taskSet2);
        assertEquals(taskData.getTasks("carol"), taskSet3);
    }

    @DisplayName("TaskData getUnion method doğru çalışıyor mu ?")
    @Test
    public void testGetUnionMethod() {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        Set<Task> totals = taskData.getUnion(taskSet, taskSet2);
        assertEquals(totals.size(), 2);
    }

    @DisplayName("TaskData getIntersect() method doğru çalışıyor mu ?")
    @Test
    public void testGetIntersectMethod() throws NoSuchFieldException {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        taskSet.add(task2);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        Set<Task> intersections = taskData.getIntersection(taskSet, taskSet2);

        for(Task task: intersections){
            assertEquals(task, task2);
        }

        assertEquals(intersections.size(), 1);
    }

    @DisplayName("TaskData getDifference() method doğru çalışıyor mu ?")
    @Test
    public void testGetDifferenceMethod() throws NoSuchFieldException {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        taskSet.add(task2);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        Set<Task> differences = taskData.getDifference(taskSet, taskSet2);

        for(Task task: differences){
            assertEquals(task, task1);
        }

        assertEquals(differences.size(), 1);
    }

    @DisplayName("findUniqueWords doğru çalışıyor mu ?")
    @Test
    public void testFindUniqueWordsMethod() {
        String text = "Carroll began writing the manuscript of the story the next day, although that earliest version is lost. " +
                "The girls and Carroll took another boat trip a month later, when he elaborated the plot to the story of Alice, " +
                "and in November he began working on the manuscript in earnest. To add the finishing touches he researched " +
                "natural history in connection with the animals presented in the book and then had the book examined " +
                "by other children—particularly those of George MacDonald. Though Carroll did add his own illustrations " +
                "to the original copy, on publication he was advised to find a professional illustrator so the pictures " +
                "were more appealing to its audiences. He subsequently approached John Tenniel to reinterpret " +
                "Carroll's visions through his own artistic eye, telling him that the story had been well liked by the children.\n" +
                "\n" +
                "Carroll began planning a print edition of the Alice story in 1863. " +
                "He wrote on 9 May 1863 that MacDonald's family had suggested he publish Alice. " +
                "A diary entry for 2 July says that he received a specimen page of the print edition around that date. " +
                "On 26 November 1864, Carroll gave Alice the manuscript of Alice's Adventures Under Ground, with illustrations " +
                "by Carroll, dedicating it as 'A Christmas Gift to a Dear Child in Memory of a Summer's Day'. " +
                "The published version of Alice's Adventures in Wonderland is about twice the length of " +
                "Alice's Adventures Under Ground and includes episodes, such as the Mad Tea-Party, " +
                "that did not appear in the manuscript. The only known manuscript copy of Under Ground " +
                "is held in the British Library. Macmillan published a facsimile of the manuscript in 1886.";

        assertEquals(StringSet.findUniqueWords(text).size(), 143);

        List<String> results = StringSet.findUniqueWords(text).stream().collect(Collectors.toList());
        assertEquals(results.get(0), "a");
        assertEquals(results.get(results.size()-1), "wrote");
    }
}
