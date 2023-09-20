package com.metaphorse.diagnostic.diagnostic_taskmanagement;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.metaphorse.diagnostic.diagnostic_taskmanagement.Controller.TaskController;
import com.metaphorse.diagnostic.diagnostic_taskmanagement.Model.Task;
import com.metaphorse.diagnostic.diagnostic_taskmanagement.Repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository; // Reemplace con el nombre de su servicio

    @BeforeEach
    public void setUp() {
        // Configura el comportamiento esperado del servicio simulado (Mock)
        when(taskRepository.findAll()).thenReturn(Arrays.asList(new Task(1L, "Tarea 1", "pendiente"), new Task(1L, "Tarea 2", "en progreso")));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2)); // Comprueba que se devuelvan 2 tareas
    }
}
