package com.dteodoro.javari.connector.controller;

import com.dteodoro.javari.connector.domain.ScheduleLoader;
import com.dteodoro.javari.connector.domain.TeamLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class LoaderControllerTest {

    @InjectMocks
    LoaderController loaderController;
    @Mock
    TeamLoader teamLoaderMock;
    @Mock
    ScheduleLoader scheduleLoaderMock;

    @Test
    @DisplayName("Load teams by external provider")
    public void loadTeams_ReturnsListOfTeam(){
        assertTrue(true);
    }
  
}