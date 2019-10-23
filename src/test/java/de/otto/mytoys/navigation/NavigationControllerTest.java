package de.otto.mytoys.navigation;

import io.micrometer.core.instrument.util.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class NavigationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void thatOnlyRequestWithCorrectApiKeyIsAccepted() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/navigation"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/navigation")
                .header("x-api-key", ""))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/navigation")
                .header("x-api-key", "hz7JPdKK069Ui1TRxxd1k8BQcocSVDkj219DVzzD"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void thatCorrectNavigationEntriesAreReturned() throws Exception {
        // given
        final String expected = getNavigationEntriesAsJson();

        //when
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/navigation")
                .header("x-api-key", "hz7JPdKK069Ui1TRxxd1k8BQcocSVDkj219DVzzD"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertThat(mvcResult.getResponse().getContentAsString()).isEqualToIgnoringWhitespace(expected);

    }

    private String getNavigationEntriesAsJson() throws FileNotFoundException {
        File inJson = new File("src/test/resources/navigation.json");

        InputStream inputStream = new FileInputStream(inJson);

        return IOUtils.toString(inputStream);
    }
}