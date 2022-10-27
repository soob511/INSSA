package com.inssa.backend.bus.controller;

import com.inssa.backend.ApiDocument;
import com.inssa.backend.bus.controller.dto.BusResponse;
import com.inssa.backend.bus.controller.dto.BusStopResponse;
import com.inssa.backend.bus.service.BusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusController.class)
public class BusControllerTest extends ApiDocument {

    private static final int NUMBER = 1;
    private static final String NUMBER_PARAMETER_NAME = "number";
    private static final String NAME = "name";
    private static final double LATITUDE = 0D;
    private static final double LONGITUDE = 0D;
    private static final int LAST_BUS_NUMBER = 2;
    private static final int LAST_VISITED_ROUTE = 1;

    @MockBean
    private BusService busService;

    private BusResponse busResponse;

    @BeforeEach
    void setUp() {
        List<BusStopResponse> busStopResponses = IntStream.range(0, 2)
                .mapToObj(n -> BusStopResponse.builder()
                        .name(NAME)
                        .latitude(LATITUDE)
                        .longitude(LONGITUDE)
                        .build())
                .collect(Collectors.toList());
        busResponse = BusResponse.builder()
                .lastBusNumber(LAST_BUS_NUMBER)
                .lastVisitedRoute(LAST_VISITED_ROUTE)
                .busStopResponses(busStopResponses)
                .build();
    }

    @DisplayName("버스 조회 성공")
    @Test
    void get_bus_success() throws Exception {
        // given
        willReturn(busResponse).given(busService).getBus(anyInt());
        // when
        ResultActions resultActions = 버스_조회_요청(NUMBER);
        // then
        버스_조회_성공(resultActions);
    }

    private ResultActions 버스_조회_요청(int number) throws Exception {
        return mockMvc.perform(get("/api/v1/buses")
                .contextPath("/api/v1")
                .param(NUMBER_PARAMETER_NAME, String.valueOf(number)));
    }

    private void 버스_조회_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(busResponse)))
                .andDo(print())
                .andDo(toDocument("get-bus-success"));
    }
}