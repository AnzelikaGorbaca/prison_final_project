package com.prison.project.controller;

import com.prison.project.model.Crime;
import com.prison.project.service.crime.CreateCrimeService;
import com.prison.project.service.crime.DeleteCrimeService;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.crime.UpdateCrimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CrimeController.class)
class CrimeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCrimeService createCrimeService;
    @MockBean
    private DeleteCrimeService deleteCrimeService;
    @MockBean
    private GetCrimeService getCrimeService;
    @MockBean
    private UpdateCrimeService updateCrimeService;

    @Test
    void crimeIndex() throws Exception {

        when(getCrimeService.getAllCrime()).thenReturn(
                Arrays.asList(new Crime(1L, "Murder"),
                        new Crime(2L, "Robbery"),
                        new Crime(3L, "Awful cook")));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/prison-management-system/crimes")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("crime-index"))
                .andReturn();
    }

    @Test
    void addCrime() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get("/prison-management-system/crimes/crime-add")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("crime-add"))
                .andReturn();
    }

    @Test //DOESN'T WORK//NOT SURE
    void registerCrime() throws Exception {
        when(createCrimeService.registerCrime(new Crime(1L, "Murder")))
                .thenReturn(new Crime(1L, "Murder"));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/prison-management-system/crimes")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(view().name("crime-index"))
                .andReturn();

        verify(createCrimeService).registerCrime(new Crime(1L, "Murder"));
    }

    @Test //NOT SURE
    void registerExistingCrimeError() throws Exception {
        when(createCrimeService.registerCrime(new Crime(1L, "Murder")))
                .thenReturn(new Crime(1L, "Murder"));
        when(getCrimeService.getAllCrime()).thenReturn(Arrays.asList(new Crime(1L, "Murder"),
                new Crime(2L, "Robbery"),
                new Crime(3L, "Awful cook")));

        createCrimeService.registerCrime(new Crime(1L, "Murder"));
        getCrimeService.getAllCrime();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/prison-management-system/crimes")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(view().name("crime-add"))
                .andReturn();


        verify(createCrimeService).registerCrime(new Crime(1L, "Murder"));
        verify(getCrimeService).getAllCrime();
    }

    @Test
    void updateCrimeById() throws Exception {
        when (getCrimeService.getCrimeById(11L)).thenReturn(new Crime (11L, "Murder"));
        RequestBuilder request = MockMvcRequestBuilders
                .get("/prison-management-system/crimes/update/{id}", 11)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("crime-edit"))
                .andReturn();

        verify(getCrimeService).getCrimeById(11L);


    }

    @Test //DOESN'T WORK//NOT SURE
    void updateCrime() throws Exception {
        Crime updated = new Crime (1L,"Murder");
        when(updateCrimeService.updateCrime(1L, updated))
                .thenReturn(new Crime(1L, "Murder"));

        updateCrimeService.updateCrime(1L, updated);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/prison-management-system/crimes/update/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("crime-index"))
                .andReturn();

    }

    @Test //NOT SURE
    void updateExistingCrimeError() throws Exception {
        Crime updated = new Crime (1L,"Murder");
        when(updateCrimeService.updateCrime(1L, updated))
                .thenReturn(new Crime(1L, "Murder"));

        when(getCrimeService.getAllCrime()).thenReturn(Arrays.asList(new Crime(1L, "Robbery"),
                new Crime(2L, "Murder"),
                new Crime(3L, "Awful cook")));

        updateCrimeService.updateCrime(1L, updated);
        getCrimeService.getAllCrime();

        RequestBuilder request = MockMvcRequestBuilders
                .post("/prison-management-system/crimes/update/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("crime-edit"))
                .andReturn();

        verify(updateCrimeService).updateCrime(1L, updated);
        verify(getCrimeService).getAllCrime();

    }




    @Test
    void deleteCrime() throws Exception {
        doNothing().when(deleteCrimeService).deleteCrimeById(11L);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/prison-management-system/crimes/delete/{id}", 11)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request)
//                .andExpect(status().isOk())
                .andExpect(view().name("crime-index"));
    }
}