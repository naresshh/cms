package com._x.controller;

import com._x.dto.CandidateDTO;
import com._x.security.JwtUtils;
import com._x.service.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(value=CandidateController.class)
class CandidateControllerTest
{
    @MockBean
    private CandidateService candidateService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtUtils jwtUtils;

    @Test
    void testSaveCandidate() throws Exception {
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateName("John");
        candidateDTO.setExperience("5 years");
        candidateDTO.setNoticePeriod("1 month");
        candidateDTO.setEducation("B.Sc IT");
        candidateDTO.setSkills("Java, Spring Boot");
        candidateDTO.setPhone("1234567890");
        candidateDTO.setEmail("johndoe@email.com");
        candidateDTO.setSource("LinkedIn");
        candidateDTO.setHistory("Has worked at X and Y");
        candidateDTO.setOpenToWork(true);
        candidateDTO.setContactStatus("Open");

        when(candidateService.saveCandidate(ArgumentMatchers.any())).thenReturn(candidateDTO);
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.post("/api/v1/candidates")
                .content(asJsonString(candidateDTO)).contentType(MediaType.APPLICATION_JSON);
        ResultActions perform = mockMvc.perform(reqBuilder);
        MvcResult result=  perform.andReturn();
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        assertEquals(200,status);

    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUpdateCandidate() throws Exception {
        // create a request
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setCandidateName("John");
        candidateDTO.setExperience("1 year");
        candidateDTO.setNoticePeriod("1 month");
        candidateDTO.setEducation("B.Sc IT");
        candidateDTO.setSkills("Java, Spring Boot");
        candidateDTO.setPhone("1234567890");
        candidateDTO.setEmail("johndoe@email.com");
        candidateDTO.setSource("LinkedIn");
        candidateDTO.setHistory("Has worked at X and Y");
        candidateDTO.setOpenToWork(true);
        candidateDTO.setContactStatus("Open");

        when(candidateService.updateCandidate(1L,candidateDTO)).thenReturn(candidateDTO);
        ObjectMapper mapper = new ObjectMapper();
        String candidateJson = mapper.writeValueAsString(candidateDTO);
        MockHttpServletRequestBuilder reqBuilder = MockMvcRequestBuilders.put("/api/v1/candidates/1")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(candidateJson);
        ResultActions perform = mockMvc.perform(reqBuilder);
        MvcResult result=  perform.andReturn();
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        assertEquals(200,status);

    }
}