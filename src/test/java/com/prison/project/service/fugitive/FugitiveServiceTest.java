package com.prison.project.service.fugitive;

import com.prison.project.model.Fugitive;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class FugitiveServiceTest {

    @Test
    void shouldConnectSuccessfullyToApi()
            throws IOException {

        HttpUriRequest request = new HttpGet("https://api.fbi.gov/wanted/v1/list");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        MatcherAssert.assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void shouldReturnContentTypeJsonWhenRequestIsExecuted()
            throws IOException {

        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet("https://api.fbi.gov/wanted/v1/list");

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }

    @Test
    void shouldRetrieveCorrectInformation() throws  IOException{

        Fugitive expectedFugitive = new Fugitive("ALEXANDRA (ALEX) ANAYA",
                "The Federal Bureau of Investigation (FBI) is seeking information from the public regarding the unsolved murder of 13-year-old Alexandra (Alex) Anaya.\r\nOn August 13, 2005, Alex was reported missing from her Hammond, Indiana residence in the early morning hours. Three days later, Alex's torso was recovered floating in the Little Calumet River. Alex was wearing a gold chain with a round, religious medallion.",
                null,
                "https://www.fbi.gov/wanted/seeking-info/alexandra-alex-anaya/@@images/image/large",
                "Female",
                null,
                "The FBI is offering a reward of up to $10,000 for information leading to the identification, arrest, and conviction of the person or persons responsible for the disappearance and death of Alexandra (Alex) Anaya.",
                null,
                "https://www.fbi.gov/wanted/seeking-info/alexandra-alex-anaya/download.pdf");


        FugitiveService fs = new FugitiveService("https://api.fbi.gov/wanted/v1/list?title=ANAYA");
        Fugitive actualFugitive = fs.getFugitiveList().get(0);

        Assertions.assertThat(expectedFugitive).isEqualTo(actualFugitive);
        assertEquals(expectedFugitive, actualFugitive);
        assertNotNull(actualFugitive);
        assertEquals(expectedFugitive.getTitle(), actualFugitive.getTitle());
        assertEquals(expectedFugitive.getDetails(),actualFugitive.getDetails());
        assertEquals(expectedFugitive.getCaution(), actualFugitive.getCaution());
        assertEquals(expectedFugitive.getImage(), actualFugitive.getImage());
        assertEquals(expectedFugitive.getSex(), actualFugitive.getSex());
        assertEquals(expectedFugitive.getRemarks(), actualFugitive.getRemarks());
        assertEquals(expectedFugitive.getReward(), actualFugitive.getReward());
        assertEquals(expectedFugitive.getMessage(), actualFugitive.getMessage());
        assertEquals(expectedFugitive.getPdfLink(), actualFugitive.getPdfLink());

    }

    @Test
    void shouldReturn5elementsFromTheList() {
        FugitiveService fs = new FugitiveService("https://api.fbi.gov/wanted/v1/list");
        int expectedSize = 5;
        int actualSize = fs.getFiveFugitiveList(3).size();

        assertNotNull(actualSize);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void shouldReplaceInformation() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = FugitiveService.class
                .getDeclaredMethod("replace", String.class);
        method.setAccessible(true);
        FugitiveService fs = new FugitiveService();

        String expected = "It is a check for additional symbols";
        String toCheck = "It is<p> a check for</p> additional symbols";
        String actual = (String) method.invoke(fs, toCheck);

        assertEquals(expected, actual);
    }


}

