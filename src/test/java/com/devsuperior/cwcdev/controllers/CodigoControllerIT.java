package com.devsuperior.cwcdev.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.cwcdev.dto.CodigoDTO;
import com.devsuperior.cwcdev.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CodigoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    private String clientUsername;
    private String clientPassword;
    private String adminUsername;
    private String adminPassword;

    @BeforeEach
    void setUp() throws Exception {
        clientUsername = "livinha.resende@hotmail.com";
        clientPassword = "123456";
        adminUsername = "calebewerneck@hotmail.com";
        adminPassword = "123456";
    }

    @Test
    public void insertShouldReturn401WhenNoUserLogged() throws Exception {
        CodigoDTO dto = new CodigoDTO(null, "Java", "java teste", "javateste", "image.jpg");
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/codigos")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnauthorized());
    }

    @Test
    public void insertShouldReturn403WhenClientLogged() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, clientUsername, clientPassword);

        CodigoDTO dto = new CodigoDTO(null, "Java", "java teste", "javateste", "image.jpg");
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/codigos")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldInsertResourceWhenAdminLoggedAndCorrectData() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        CodigoDTO dto = new CodigoDTO(null, "Java", "java teste", "javateste", "image.jpg");
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/codigos")
                .header("Authorization", "Bearer " + accessToken)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.linguagem").value("Java"));
    }

    

    @Test
    public void findByIdShouldReturnResourceWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/codigos/1")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void findByIdShouldReturn404WhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/codigos/999")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenAdminLoggedAndIdExists() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result = mockMvc.perform(delete("/codigos/1")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturn404WhenIdDoesNotExist() throws Exception {
        String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

        ResultActions result = mockMvc.perform(delete("/codigos/999")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}